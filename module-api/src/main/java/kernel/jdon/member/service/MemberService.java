package kernel.jdon.member.service;

import static kernel.jdon.auth.encrypt.HmacUtil.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.auth.dto.object.RegisterMemberDto;
import kernel.jdon.auth.dto.request.RegisterRequest;
import kernel.jdon.auth.encrypt.AesUtil;
import kernel.jdon.auth.encrypt.HmacUtil;
import kernel.jdon.error.code.api.MemberErrorCode;
import kernel.jdon.error.exception.api.ApiException;
import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.jobcategory.repository.JobCategoryRepository;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.member.repository.MemberRepository;
import kernel.jdon.memberskill.domain.MemberSkill;
import kernel.jdon.memberskill.repository.MemberSkillRepository;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final SkillRepository skillRepository;
	private final MemberSkillRepository memberSkillRepository;
	private final JobCategoryRepository jobCategoryRepository;

	@Transactional
	public Long register(RegisterRequest registerRequest) {
		String emailAndProvider = getEmailAndProviderString(registerRequest.getHmac(), registerRequest.getEncrypted());
		Map<String, String> userInfo = parseQueryString(emailAndProvider);

		JobCategory findJobCategory = findJobCategory(registerRequest.getJobCategoryId());
		RegisterMemberDto registerMemberDto = RegisterMemberDto.builder()
			.email(userInfo.get("email"))
			.jobCategory(findJobCategory)
			.memberRole(MemberRole.ROLE_USER)
			.socialProvider(SocialProviderType.ofType(userInfo.get("provider")))
			.build();
		Member registeredMember = memberRepository.save(registerRequest.toEntity(registerMemberDto));
		saveMemberSkillList(registerRequest.getSkillList(), registeredMember);

		return registeredMember.getId();
	}

	private void saveMemberSkillList(List<Long> skillIdList, Member member) {
		List<Skill> findSkillList = skillIdList.stream()
			.map(skillId -> skillRepository.findById(skillId)
				.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_SKILL))).toList();
		List<MemberSkill> memberSkillList = findSkillList.stream()
			.map(skill -> MemberSkill.builder()
				.member(member)
				.skill(skill)
				.build())
			.collect(Collectors.toList());
		memberSkillRepository.saveAll(memberSkillList);
	}

	private JobCategory findJobCategory(Long jobCategoryId) {
		return jobCategoryRepository.findById(jobCategoryId)
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_JOB_CATEGORY));
	}

	private String getEmailAndProviderString (String hmac, String encrypted) {
		String emailAndProvider = null;
		try {
			if (isValidHMAC(hmac, encrypted)) {
				emailAndProvider = AesUtil.decryptAESCBC(encrypted);
			} else {
				throw new ApiException(MemberErrorCode.UNAUTHORIZED_EMAIL_OAUTH2);
			}
		} catch (Exception e) {
			throw new ApiException(MemberErrorCode.SERVER_ERROR_DECRYPTION);
		}
		return emailAndProvider;
	}

	private Map<String, String> parseQueryString(String queryString) {
		Map<String, String> params = new HashMap<>();

		String[] pairs = queryString.split("&");

		try {
			for (String pair : pairs) {
				String[] keyValue = pair.split("=");
				String key = URLDecoder.decode(keyValue[0], "UTF-8");
				String value = URLDecoder.decode(keyValue[1], "UTF-8");

				params.put(key, value);
			}
		} catch (UnsupportedEncodingException e) {
			throw new ApiException(MemberErrorCode.SERVER_ERROR_PARSE_QUERY_STRING);
		}
		return params;
	}
}
