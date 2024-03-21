package kernel.jdon.moduleapi.domain.skill.infrastructure.keyword;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import kernel.jdon.moduleapi.domain.skill.core.SkillReader;
import kernel.jdon.moduleapi.domain.skill.infrastructure.SkillRepository;
import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillKeywordCache {

    private static final String SKILL_KEYWORDS = "SkillKeywords";
    private final SkillRepository skillRepository;
    private final SkillReader skillReader;
    private final SkillKeywordRepository skillKeywordRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Set<String>> hashOperations;

    @PostConstruct
    public void initializeCache() {
        this.hashOperations = redisTemplate.opsForHash();

        List<SkillKeyword> skillKeywordList = skillKeywordRepository.findAll();

        skillKeywordList.forEach(skillKeyword -> {
            String relatedKeyword = skillKeyword.getRelatedKeyword().toLowerCase();
            String keyword = skillKeyword.getSkill().getKeyword().toLowerCase();
            Set<String> keywords = hashOperations.get(SKILL_KEYWORDS, relatedKeyword);

            if (keywords == null) {
                keywords = new HashSet<>(Collections.singletonList(keyword));
            }
            hashOperations.put(SKILL_KEYWORDS, relatedKeyword, keywords);
            hashOperations.put(SKILL_KEYWORDS, keyword, new HashSet<>(Collections.singleton(keyword)));
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

        return associatedKeywords.stream()
            .toList();
    }

}
