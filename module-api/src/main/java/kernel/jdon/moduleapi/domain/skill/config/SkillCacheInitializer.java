package kernel.jdon.moduleapi.domain.skill.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import kernel.jdon.moduleapi.domain.skill.infrastructure.SkillRepository;
import kernel.jdon.moduledomain.skill.domain.Skill;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillCacheInitializer {

    private static final String SKILL_KEYWORDS = "SkillKeywords";
    private final SkillRepository skillRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Set<String>> hashOperations;

    @PostConstruct
    public void initializeCache() {
        hashOperations = redisTemplate.opsForHash();
        loadSkillsIntoCache();
    }

    private void loadSkillsIntoCache() {
        List<Skill> skillList = skillRepository.findAll();
        skillList.forEach(skill -> {
            String keyword = skill.getKeyword().toLowerCase();
            loadKeywordIntoCache(keyword);
        });
    }

    private void loadKeywordIntoCache(String keyword) {
        hashOperations.putIfAbsent(SKILL_KEYWORDS, keyword, new HashSet<>(Set.of(keyword)));
    }
}
