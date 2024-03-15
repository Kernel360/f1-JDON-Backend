package kernel.jdon.moduleapi.domain.skill.infrastructure.keyword;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.skill.core.keyword.SkillKeywordReader;
import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillKeywordReaderImpl implements SkillKeywordReader {

    private final SkillKeywordRepository skillKeywordRepository;

    @Override
    public List<SkillKeyword> findAllByRelatedKeywordIgnoreCase(final String relatedKeyword) {
        return skillKeywordRepository.findAllByRelatedKeywordIgnoreCase(relatedKeyword);
    }
}
