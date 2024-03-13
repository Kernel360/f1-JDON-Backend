package kernel.jdon.moduleapi.domain.jd.core;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kernel.jdon.moduleapi.domain.jd.presentation.JdCondition;
import kernel.jdon.moduleapi.domain.skill.core.SkillReader;
import kernel.jdon.moduleapi.domain.skill.core.keyword.SkillKeywordReader;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JdServiceImpl implements JdService {
    private final JdReader jdReader;
    private final SkillKeywordReader skillKeywordReader;
    private final JdInfoMapper jdInfoMapper;
    private final SkillReader skillReader;

    @Override
    public JdInfo.FindWantedJdResponse getJd(final Long jdId) {
        final WantedJd findWantedJd = jdReader.findWantedJd(jdId);
        final List<JdInfo.FindSkill> findSkillList = jdReader.findSkillListByWantedJd(findWantedJd);

        return jdInfoMapper.of(findWantedJd, findSkillList);
    }

    @Override
    public JdInfo.FindWantedJdListResponse getJdList(final PageInfoRequest pageInfoRequest,
        final JdCondition jdCondition) {
        final String searchKeyword = Optional.ofNullable(jdCondition.getSkill())
            .filter(StringUtils::hasText)
            .orElse(null);
        final List<SkillKeyword> findSkillKeywordList = skillKeywordReader.findAllByRelatedKeywordIgnoreCase(
            searchKeyword);
        final List<String> findOriginSkillKeywordList = skillReader.findOriginSkillKeywordListBySkillKeywordList(
            findSkillKeywordList);

        return jdReader.findWantedJdList(pageInfoRequest, jdCondition, findOriginSkillKeywordList);
    }
}
