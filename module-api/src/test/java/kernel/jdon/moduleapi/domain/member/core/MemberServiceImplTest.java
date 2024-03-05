package kernel.jdon.moduleapi.domain.member.core;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.member.domain.Gender;
import kernel.jdon.moduledomain.member.domain.Member;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member Service 테스트")
class MemberServiceImplTest {

	@Mock
	private MemberReader memberReader;
	@Mock
	private MemberInfoMapper memberInfoMapper;

	@InjectMocks
	private MemberServiceImpl memberService;

	@Test
	@DisplayName("1: 사용자 정보 요청 시, get 메서드가 memberId에 해당되는 멤버의 정보를 응답으로 반환한다.")
	void givenMemberId_whenGetMember_thenReturnMemberInfo() {
		//given
		final var memberId = 1L;
		final var findMember = mockMember();
		final var mockFindMemberResponse = MemberInfo.FindMemberResponse.builder().email("email").build();
		final var mockSkillIdList = mockSkillIdList();

		//when
		when(memberReader.findById(memberId)).thenReturn(findMember);
		when(memberReader.findSkillIdListByMember(findMember)).thenReturn(mockSkillIdList);
		when(memberInfoMapper.of(findMember, mockSkillIdList)).thenReturn(mockFindMemberResponse);
		final var response = memberService.getMember(memberId);

		//then
		assertThat(response).isEqualTo(mockFindMemberResponse);
		assertThat(response.getEmail()).isEqualTo(findMember.getEmail());

		//verify
		verify(memberReader, times(1)).findById(memberId);
		verify(memberReader, times(1)).findSkillIdListByMember(findMember);
		verify(memberInfoMapper, times(1)).of(findMember, mockSkillIdList);
	}

	@Test
	void modifyMember() {
	}

	private Member mockMember() {
		return Member.builder()
			.id(1L)
			.email("email")
			.nickname("nickname")
			.birth("2020-02-02")
			.gender(Gender.MALE)
			.jobCategory(mock(JobCategory.class))
			.build();
	}

	private List<Long> mockSkillIdList() {
		return List.of(1L, 2L, 3L);
	}
}