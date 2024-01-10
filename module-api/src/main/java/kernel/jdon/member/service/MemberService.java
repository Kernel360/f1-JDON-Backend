package kernel.jdon.member.service;

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
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JobCategoryRepository jobCategoryRepository;

	@Transactional
	public Long register(RegisterRequest registerRequest) {
		String emailAndProvider = null;
		try {
			if (true == HmacUtil.validateHMAC(registerRequest.getHmac(), registerRequest.getEncrypted())) {
				emailAndProvider = AesUtil.decryptAESCBC(registerRequest.getEncrypted());
				log.info("emailAndProvider: {}", emailAndProvider);
			} else {
				throw new ApiException(MemberErrorCode.UNAUTHORIZED_EMAIL_OAUTH2);
			}
		} catch (Exception e) {
			throw new ApiException(MemberErrorCode.SERVER_ERROR_DECRYPTION);
		}

		JobCategory findJobCategory = jobCategoryRepository.findById(registerRequest.getJobCategoryId())
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_JOB_CATEGORY));

		String[] userInfo = emailAndProvider.split("&");
		RegisterMemberDto registerMemberDto = RegisterMemberDto.builder()
			.email(userInfo[0])
			.jobCategory(findJobCategory)
			.memberRole(MemberRole.ROLE_USER)
			.socialProvider(SocialProviderType.valueOf(userInfo[1]))
			.build();

		return (memberRepository.save(registerRequest.toEntity(registerMemberDto))).getId();
	}
}
