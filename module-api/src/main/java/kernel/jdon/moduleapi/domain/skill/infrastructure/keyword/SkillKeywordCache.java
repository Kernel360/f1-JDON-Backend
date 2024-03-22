package kernel.jdon.moduleapi.domain.skill.infrastructure.keyword;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import kernel.jdon.moduleapi.domain.skill.infrastructure.SkillRepository;
import kernel.jdon.moduledomain.skill.domain.Skill;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillKeywordCache {

    private static final String SKILL_KEYWORDS = "SkillKeywords";
    private final SkillRepository skillRepository;
    private final SkillKeywordRepository skillKeywordRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Set<String>> hashOperations;

    /** 캐시 초기화 부분 **/
    @PostConstruct
    public void initializeCache() {
        hashOperations = redisTemplate.opsForHash();
        loadSkillsAndSkillKeywordsIntoCache();
    }

    private void loadSkillsAndSkillKeywordsIntoCache() {
        List<Skill> skillList = skillRepository.findAll();
        skillList.forEach(skill -> {
            String keyword = skill.getKeyword().toLowerCase();
            cacheKeyword(keyword);

            skill.getSkillKeywordList().forEach(skillKeyword -> {
                String relatedKeyword = skillKeyword.getRelatedKeyword().toLowerCase();
                cacheRelatedKeyword(relatedKeyword, keyword);
            });
        });
    }

    private void cacheKeyword(String keyword) {
        hashOperations.putIfAbsent(SKILL_KEYWORDS, keyword, new HashSet<>(Set.of(keyword)));
    }

    private void cacheRelatedKeyword(String relatedKeyword, String keyword) {
        Set<String> keywordSet = Optional.ofNullable(hashOperations.get(SKILL_KEYWORDS, relatedKeyword))
            .orElseGet(HashSet::new);
        keywordSet.add(keyword);
        hashOperations.put(SKILL_KEYWORDS, relatedKeyword, keywordSet);
    }

    /** 캐시에서 데이터 제공하는 부분 **/
    public List<String> findAssociatedKeywords(String relatedKeyword) {
        Set<String> associatedKeywordSet = Optional.ofNullable(
                hashOperations.get(SKILL_KEYWORDS, relatedKeyword.toLowerCase()))
            .orElseGet(() -> findAndCacheAssociatedKeywords(relatedKeyword));

        return new ArrayList<>(associatedKeywordSet);
    }

    private Set<String> findAndCacheAssociatedKeywords(String relatedKeyword) {
        Set<String> associatedKeywordSet = skillKeywordRepository.findAllByRelatedKeywordIgnoreCase(relatedKeyword)
            .stream()
            .map(skillKeyword -> skillKeyword.getSkill().getKeyword().toLowerCase())
            .collect(Collectors.toSet());

        if (!associatedKeywordSet.isEmpty()) {
            hashOperations.put(SKILL_KEYWORDS, relatedKeyword.toLowerCase(), associatedKeywordSet);
        }
        return associatedKeywordSet;
    }
}
