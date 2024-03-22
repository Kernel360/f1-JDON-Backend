package kernel.jdon.moduleapi.domain.skill.infrastructure.keyword;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import kernel.jdon.moduleapi.domain.skill.infrastructure.SkillRepository;
import kernel.jdon.moduledomain.skill.domain.Skill;
import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillKeywordCache {

    private static final String SKILL_KEYWORDS = "SkillKeywords";
    private final SkillRepository skillRepository;
    private final SkillKeywordRepository skillKeywordRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Set<String>> hashOperations;

    @PostConstruct
    public void initializeCache() {
        this.hashOperations = redisTemplate.opsForHash();

        List<Skill> skillList = skillRepository.findAll();
        skillList.forEach(skill -> {
            Set<String> keywordSet = new HashSet<>();
            String keyword = skill.getKeyword().toLowerCase();
            keywordSet.add(keyword);
            hashOperations.put(SKILL_KEYWORDS, skill.getKeyword().toLowerCase(), keywordSet);

            List<SkillKeyword> skillKeywordList = skill.getSkillKeywordList();
            skillKeywordList.forEach(skillKeyword -> {
                String relatedKeyword = skillKeyword.getRelatedKeyword().toLowerCase();
                Set<String> existingKeywordSet = hashOperations.get(SKILL_KEYWORDS, relatedKeyword);
                if (existingKeywordSet != null) {
                    hashOperations.put(SKILL_KEYWORDS, relatedKeyword, existingKeywordSet);
                }
            });
        });
    }

    public List<String> findAssociatedKeywords(String relatedKeyword) {
        Set<String> associatedKeywords = hashOperations.get(SKILL_KEYWORDS, relatedKeyword);

        if (associatedKeywords == null) {
            List<SkillKeyword> skillKeywords = skillKeywordRepository.findAllByRelatedKeywordIgnoreCase(relatedKeyword);

            if (!skillKeywordRepository.findAllByRelatedKeywordIgnoreCase(relatedKeyword).isEmpty()) {
                associatedKeywords = new HashSet<>();
                Set<String> finalAssociatedKeywords = associatedKeywords;
                skillKeywords.forEach(skillKeyword -> {
                    String keyword = skillKeyword.getSkill().getKeyword().toLowerCase();
                    finalAssociatedKeywords.add(keyword);
                    hashOperations.put(SKILL_KEYWORDS, relatedKeyword.toLowerCase(), finalAssociatedKeywords);
                });
            }
        }

        return new ArrayList<>(associatedKeywords);
    }
}
