package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteReader;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteStore;
import kernel.jdon.moduleapi.domain.favorite.error.FavoriteErrorCode;
import kernel.jdon.moduleapi.domain.inflearncourse.core.InflearnReader;
import kernel.jdon.moduleapi.domain.inflearncourse.error.InflearncourseErrorCode;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import kernel.jdon.moduledomain.member.domain.Member;

@DisplayName("Favorite Factory Impl 테스트")
@ExtendWith(MockitoExtension.class)
class FavoriteFactoryImplTest {

    @Mock
    private FavoriteReader favoriteReader;
    @Mock
    private FavoriteStore favoriteStore;
    @Mock
    private MemberReader memberReader;
    @Mock
    private InflearnReader inflearnReader;
    @InjectMocks
    private FavoriteFactoryImpl favoriteFactoryImpl;

    private Member mockMember;
    private InflearnCourse mockInflearnCourse;
    private Favorite mockFavorite;
    private Long mockMemberId;
    private Long mockLectureId;

    @BeforeEach
    void setUp() {
        mockMember = mock(Member.class);
        mockInflearnCourse = mock(InflearnCourse.class);
        mockFavorite = new Favorite(mockMember, mockInflearnCourse);

        when(mockMember.getId()).thenReturn(1L);
        when(mockInflearnCourse.getId()).thenReturn(100L);

        mockMemberId = mockMember.getId();
        mockLectureId = mockInflearnCourse.getId();
    }

    @DisplayName("1: 회원 id와 강의 id가 주어지면, create 메서드가 생성된 Favorite 엔티티를 반환한다.")
    @Test
    void givenMemberIdAndLectureId_whenCreate_thenReturnSavedFavorite() throws Exception {
        // given
        given(memberReader.findById(mockMemberId)).willReturn(mockMember);
        given(inflearnReader.findById(mockLectureId)).willReturn(mockInflearnCourse);
        given(favoriteReader.findOptionalByMemberIdAndInflearnCourseId(mockMemberId, mockLectureId)).willReturn(
            Optional.empty());
        given(favoriteStore.save(any(Favorite.class))).willReturn(mockFavorite);

        // when
        Favorite actualFavorite = favoriteFactoryImpl.create(mockMemberId, mockLectureId);

        // then
        assertAll(
            () -> assertThat(actualFavorite).isEqualTo(mockFavorite),
            () -> assertThat(actualFavorite.getMember()).isEqualTo(mockFavorite.getMember()),
            () -> assertThat(actualFavorite.getInflearnCourse()).isEqualTo(mockFavorite.getInflearnCourse())
        );
        then(memberReader).should(times(1)).findById(mockMemberId);
        then(inflearnReader).should(times(1)).findById(mockLectureId);
        then(favoriteReader).should(times(1)).findOptionalByMemberIdAndInflearnCourseId(mockMemberId, mockLectureId);
        then(favoriteStore).should(times(1)).save(any(Favorite.class));
    }

    @DisplayName("2: 회원 id와 강의 id가 주어지면, 기존에 존재하던 favorite이 존재한다면 create 메서드가 존재하던 Favorite 엔티티를 반환한다.")
    @Test
    void givenMemberIdAndLectureId_whenCreate_thenReturnExistingFavorite() throws Exception {
        // given
        given(memberReader.findById(mockMemberId)).willReturn(mockMember);
        given(inflearnReader.findById(mockLectureId)).willReturn(mockInflearnCourse);
        given(favoriteReader.findOptionalByMemberIdAndInflearnCourseId(mockMemberId, mockLectureId)).willReturn(
            Optional.of(mockFavorite));

        // when
        Favorite actualFavorite = favoriteFactoryImpl.create(mockMemberId, mockLectureId);

        // then
        assertAll(
            () -> assertThat(actualFavorite).isEqualTo(mockFavorite),
            () -> assertThat(actualFavorite.getMember()).isEqualTo(mockFavorite.getMember()),
            () -> assertThat(actualFavorite.getInflearnCourse()).isEqualTo(mockFavorite.getInflearnCourse())
        );
        then(memberReader).should(times(1)).findById(mockMemberId);
        then(inflearnReader).should(times(1)).findById(mockLectureId);
        then(favoriteReader).should(times(1)).findOptionalByMemberIdAndInflearnCourseId(mockMemberId, mockLectureId);
        then(favoriteStore).should(times(0)).save(any(Favorite.class));
    }

    @DisplayName("3: 회원 id와 강의 id가 주어지면, delete 메서드가 삭제된 Favorite 엔티티를 반환한다.")
    @Test
    void givenMemberIdAndLectureId_whenDelete_thenReturnDeletedFavorite() throws Exception {
        // given
        given(memberReader.findById(mockMemberId)).willReturn(mockMember);
        given(inflearnReader.findById(mockLectureId)).willReturn(mockInflearnCourse);
        given(favoriteReader.findFavoriteByMemberIdAndInflearnCourseId(mockMemberId, mockLectureId)).willReturn(
            mockFavorite);
        willDoNothing().given(favoriteStore).delete(mockFavorite);
        // when
        Favorite actualFavorite = favoriteFactoryImpl.delete(mockMemberId, mockLectureId);

        // then
        assertAll(
            () -> assertThat(actualFavorite).isEqualTo(mockFavorite),
            () -> assertThat(actualFavorite.getMember()).isEqualTo(mockFavorite.getMember()),
            () -> assertThat(actualFavorite.getInflearnCourse()).isEqualTo(mockFavorite.getInflearnCourse())
        );
        then(memberReader).should(times(1)).findById(mockMemberId);
        then(inflearnReader).should(times(1)).findById(mockLectureId);
        then(favoriteReader).should(times(1)).findFavoriteByMemberIdAndInflearnCourseId(mockMemberId, mockLectureId);
        then(favoriteStore).should(times(1)).delete(any(Favorite.class));
    }

    @DisplayName("4: 존재하지 않는 강의 정보에 대해 찜 삭제를 하면, NOT_FOUND_INFLEARN_COURSE 에러를 반환한다.")
    @Test
    void givenInvalidInflearnCourse_whenDeleteFavorite_thenReturnNotFoundFavoriteError() throws Exception {
        // given
        given(memberReader.findById(mockMemberId)).willReturn(mockMember);
        given(inflearnReader.findById(mockLectureId)).willThrow(
            new ApiException(InflearncourseErrorCode.NOT_FOUND_INFLEARN_COURSE));

        // when
        ApiException actualException = assertThrows(ApiException.class,
            () -> favoriteFactoryImpl.delete(mockMemberId, mockLectureId));

        // then
        assertThat(actualException.getErrorCode()).isEqualTo(InflearncourseErrorCode.NOT_FOUND_INFLEARN_COURSE);
        then(memberReader).should(times(1)).findById(mockMemberId);
        then(inflearnReader).should(times(1)).findById(mockLectureId);
        then(favoriteReader).should(times(0)).findFavoriteByMemberIdAndInflearnCourseId(mockMemberId, mockLectureId);
        then(favoriteStore).should(times(0)).delete(any(Favorite.class));
    }

    @DisplayName("5: 존재하지 않는 찜 정보에 대해 찜 삭제를 하면, NOT_FOUND_FAVORITE 에러를 반환한다.")
    @Test
    void givenInvalidFavorite_whenDeleteFavorite_thenReturnNotFoundFavoriteError() throws Exception {
        // given
        given(memberReader.findById(mockMemberId)).willReturn(mockMember);
        given(inflearnReader.findById(mockLectureId)).willReturn(mockInflearnCourse);
        given(favoriteReader.findFavoriteByMemberIdAndInflearnCourseId(mockMemberId, mockLectureId))
            .willThrow(new ApiException(FavoriteErrorCode.NOT_FOUND_FAVORITE));

        // when
        ApiException actualException = assertThrows(ApiException.class,
            () -> favoriteFactoryImpl.delete(mockMemberId, mockLectureId));

        // then
        assertThat(actualException.getErrorCode()).isEqualTo(FavoriteErrorCode.NOT_FOUND_FAVORITE);
        then(memberReader).should(times(1)).findById(mockMemberId);
        then(inflearnReader).should(times(1)).findById(mockLectureId);
        then(favoriteReader).should(times(1)).findFavoriteByMemberIdAndInflearnCourseId(mockMemberId, mockLectureId);
        then(favoriteStore).should(times(0)).delete(any(Favorite.class));
    }

}