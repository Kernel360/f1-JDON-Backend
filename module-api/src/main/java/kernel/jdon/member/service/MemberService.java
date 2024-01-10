package kernel.jdon.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.auth.dto.object.RegisterMemberDto;
import kernel.jdon.auth.dto.request.RegisterRequest;
import kernel.jdon.error.code.api.MemberErrorCode;
import kernel.jdon.error.exception.api.ApiException;
import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.jobcategory.repository.JobCategoryRepository;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JobCategoryRepository jobCategoryRepository;

	@Transactional
	public Long register(RegisterRequest registerRequest) {
		JobCategory findJobCategory = jobCategoryRepository.findById(registerRequest.getJobCategoryId())
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_JOB_CATEGORY));

		RegisterMemberDto registerMemberDto = RegisterMemberDto.builder()
			.jobCategory(findJobCategory)
			.memberRole(MemberRole.ROLE_USER)
			.socialProvider(SocialProviderType.KAKAO) // TODO: email과 socialprovider에 대한 정보를 저장해두는 로직 구현후 수정 예정
			.build();

		return (memberRepository.save(registerRequest.toEntity(registerMemberDto))).getId();
	}
}
