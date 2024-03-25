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
        loadSkillsIntoCache();
    }

    private void loadSkillsIntoCache() {
        List<Skill> skillList = skillRepository.findAll();
        skillList.forEach(skill -> {
            String keyword = skill.getKeyword().toLowerCase();
            cacheKeyword(keyword);
        });
    }

    private void cacheKeyword(String keyword) {
        hashOperations.putIfAbsent(SKILL_KEYWORDS, keyword, new HashSet<>(Set.of(keyword)));
    }

    /** 캐시에서 데이터 제공하는 부분 **/
    public List<String> findRelatedKeywordList(String relatedKeyword) {
        Set<String> relatedKeywordSet = Optional.ofNullable(
                hashOperations.get(SKILL_KEYWORDS, relatedKeyword.toLowerCase()))
            .orElseGet(() -> findAndCacheRelatedKeywordList(relatedKeyword));

        return new ArrayList<>(relatedKeywordSet);
    }

    private Set<String> findAndCacheRelatedKeywordList(String relatedKeyword) {
        Set<String> relatedKeywordSet = skillKeywordRepository.findAllByRelatedKeywordIgnoreCase(relatedKeyword)
            .stream()
            .map(skillKeyword -> skillKeyword.getSkill().getKeyword().toLowerCase())
            .collect(Collectors.toSet());

        if (!relatedKeywordSet.isEmpty()) {
            hashOperations.put(SKILL_KEYWORDS, relatedKeyword.toLowerCase(), relatedKeywordSet);
        }

        return relatedKeywordSet;
    }
}
