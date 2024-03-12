package kernel.jdon.moduleapi.domain.skill.infrastructure.keyword;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.skill.core.keyword.SkillKeywordReader;
import kernel.jdon.moduleapi.domain.skill.error.SkillErrorCode;
import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillKeywordReaderImpl implements SkillKeywordReader {

    private final SkillKeywordRepository skillKeywordRepository;

    @Override
    public SkillKeyword findSkillKeywordByRelatedKeywordIgnoreCase(String relatedKeyword) {
        return skillKeywordRepository.findSkillKeywordByRelatedKeywordIgnoreCase(relatedKeyword)
            .orElseThrow(SkillErrorCode.NOT_FOUND_SKILL_KEYWORD::throwException);
    }
}
