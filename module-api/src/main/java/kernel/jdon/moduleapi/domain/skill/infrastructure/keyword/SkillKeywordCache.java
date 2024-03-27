package kernel.jdon.moduleapi.domain.skill.infrastructure.keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillKeywordCache {

    private static final String SKILL_KEYWORDS = "SkillKeywords";
    private final SkillKeywordRepository skillKeywordRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Set<String>> hashOperations;

    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    /** 캐시에서 데이터 제공하는 부분 **/
    public List<String> findOriginKeywordListByRelatedKeyword(String relatedKeyword) {
        Set<String> relatedKeywordSet = Optional.ofNullable(
                hashOperations.get(SKILL_KEYWORDS, relatedKeyword.toLowerCase()))
            .orElseGet(() -> findAndCacheOriginKeywordList(relatedKeyword));

        return new ArrayList<>(relatedKeywordSet);
    }

    private Set<String> findAndCacheOriginKeywordList(String relatedKeyword) {
        Set<String> originKeywordSet = skillKeywordRepository.findAllByRelatedKeywordIgnoreCase(relatedKeyword)
            .stream()
            .map(skillKeyword -> skillKeyword.getSkill().getKeyword().toLowerCase())
            .collect(Collectors.toSet());

        if (!originKeywordSet.isEmpty()) {
            hashOperations.put(SKILL_KEYWORDS, relatedKeyword.toLowerCase(), originKeywordSet);
        }

        return originKeywordSet;
    }
}
