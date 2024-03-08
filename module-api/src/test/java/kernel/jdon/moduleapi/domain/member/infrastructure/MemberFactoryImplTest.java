package kernel.jdon.moduleapi.domain.member.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.domain.member.core.MemberStore;
import kernel.jdon.moduleapi.domain.skill.core.SkillReader;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.member.domain.Gender;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.member.domain.MemberAccountStatus;
import kernel.jdon.moduledomain.member.domain.MemberRole;
import kernel.jdon.moduledomain.member.domain.SocialProviderType;
import kernel.jdon.moduledomain.skill.domain.Skill;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member Factory 테스트")
class MemberFactoryImplTest {
	@Mock
	private JobCategoryReader jobCategoryReader;

	@Mock
	private SkillReader skillReader;

	@Mock
	private MemberStore memberStore;

	@InjectMocks
	private MemberFactoryImpl memberFactory;

	@Test
	@DisplayName("1: member와 사용자 수정 정보가 주어지고 정보 수정 요청 시, update 메서드가 member를 업데이트한다.")
	void givenMemberAndUpdateCommand_whenUpdate() {
		//given
		final var mockMember = mockMember();
		final var mockUpdateCommand = mockUpdateCommand();
		final var mockFindJobCategory = mock(JobCategory.class);
		final var mockFindSkillList = mockSkillList();

		//when
		when(jobCategoryReader.findById(mockUpdateCommand.getJobCategoryId())).thenReturn(mockFindJobCategory);
		when(skillReader.findAllByIdList(mockUpdateCommand.getSkillList())).thenReturn(mockFindSkillList);
		doNothing().when(memberStore).update(any(Member.class), any(Member.class));
		memberFactory.update(mockMember, mockUpdateCommand);

		//assert

		//verify
		verify(jobCategoryReader, times(1)).findById(mockUpdateCommand.getJobCategoryId());
		verify(skillReader, times(1)).findAllByIdList(mockUpdateCommand.getSkillList());
		verify(memberStore, times(1)).update(any(Member.class), any(Member.class));
	}

	@Test
	@DisplayName("2: 회원가입 정보가 주어지면, save 메서드가 저장한 Member를 반환한다.")
	void givenRegisterCommand_whenSave_thenReturnSavedMember() {
		//given
		final var mockRegisterCommand = mockRegisterCommand();
		final var mockUserInfo = Map.of("email", "email", "provider", "kakao");
		final var mockFindJobCategory = mock(JobCategory.class);
		final var mockSaveMember = mockSavedMember(mockUserInfo, mockFindJobCategory);
		final var mockSkillList = mockSkillList();

		//when
		when(jobCategoryReader.findById(mockRegisterCommand.getJobCategoryId())).thenReturn(mockFindJobCategory);
		when(memberStore.save(any(Member.class))).thenReturn(mockSaveMember);
		when(skillReader.findAllByIdList(mockRegisterCommand.getSkillList())).thenReturn(mockSkillList);
		final var savedMember = memberFactory.save(mockRegisterCommand, mockUserInfo);

		//assert
		assertThat(savedMember).isEqualTo(mockSaveMember);
		assertThat(savedMember.getId()).isEqualTo(mockSaveMember.getId());

		//verify
		verify(jobCategoryReader, times(1)).findById(mockRegisterCommand.getJobCategoryId());
		verify(memberStore, times(1)).save(any(Member.class));
		verify(skillReader, times(1)).findAllByIdList(mockRegisterCommand.getSkillList());
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

	private MemberCommand.UpdateMemberRequest mockUpdateCommand() {
		return MemberCommand.UpdateMemberRequest.builder()
			.jobCategoryId(1L)
			.skillList(mockSkillIdList())
			.build();
	}

	private List<Skill> mockSkillList() {
		return List.of(mock(Skill.class), mock(Skill.class), mock(Skill.class));
	}

	private List<Long> mockSkillIdList() {
		return List.of(1L, 2L, 3L);
	}

	private MemberCommand.RegisterRequest mockRegisterCommand() {
		return MemberCommand.RegisterRequest.builder()
			.jobCategoryId(2L)
			.skillList(mockSkillIdList())
			.build();
	}

	private Member mockSavedMember(final Map<String, String> userInfo, JobCategory jobCategory) {
		return Member.builder()
			.id(1L)
			.email(userInfo.get("email"))
			.nickname("nickname")
			.birth("2020-02-02")
			.gender(Gender.FEMALE)
			.role(MemberRole.ROLE_USER)
			.accountStatus(MemberAccountStatus.ACTIVE)
			.jobCategory(jobCategory)
			.socialProvider(SocialProviderType.ofType(userInfo.get("provider")))
			.joinDate(LocalDateTime.now())
			.build();
	}
}