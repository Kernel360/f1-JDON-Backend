package kernel.jdon.moduleapi.domain.faq.core;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduledomain.faq.domain.Faq;

@DisplayName("FAQ Service Impl 테스트")
@ExtendWith(MockitoExtension.class)
class FaqServiceImplTest {
    @Mock
    private FaqReader faqReader;
    @Mock
    private FaqStore faqStore;
    @InjectMocks
    private FaqServiceImpl faqServiceImpl;

    @Test
    @DisplayName("1: getFaqList 메서드가 존재하는 faq 개수 만큼 데이터를 응답한다.")
    void givenValidFaqList_whenFindList_thenReturn_whenCorrectFaqList() {
        // given
        final List<Faq> faqList = Collections.singletonList(new Faq(1L, "title", "content"));

        // when
        when(faqReader.findAllFaqList()).thenReturn(faqList);
        var response = faqServiceImpl.getFaqList();

        // then
        assertThat(response.getFaqList()).hasSize(1);
        verify(faqReader, times(1)).findAllFaqList();
    }

    @Test
    @DisplayName("2: 유효한 FAQ 정보가 주어졌을 때 create 메서드가 등록한 FAQ의 ID를 반환한다.")
    void givenValidCreateRequest_whenCreate_thenReturnCreatedFaqId() throws Exception {
        // given
        final var request = new FaqCommand.CreateFaqRequest("FAQ 제목", "FAQ 내용");
        final Faq initFaq = request.toEntity();

        // when
        when(faqStore.save(any(Faq.class))).thenReturn(initFaq);
        var response = faqServiceImpl.create(request);

        // then
        assertAll(
            () -> assertThat(response).isNotNull(),
            () -> assertThat(response.getFaqId()).isEqualTo(initFaq.getId())
        );
        verify(faqStore, times(1)).save(any(Faq.class));
    }

    @Test
    @DisplayName("3: 유효한 FAQ ID가 주어졌을 때 remove 메서드에서 삭제한 FAQ의 ID를 반환한다.")
    void givenValidFaqId_whenRemove_thenReturnRemovedFaqId() throws Exception {
        //given
        final Long faqId = 1L;
        final Faq faq = new Faq(faqId, "제목", "내용");

        // when
        when(faqReader.findById(faqId)).thenReturn(faq);
        doNothing().when(faqStore).delete(faq);
        var response = faqServiceImpl.remove(faqId);

        // then
        assertThat(faqId).isEqualTo(response.getFaqId());
        verify(faqReader, times(1)).findById(faqId);
        verify(faqStore, times(1)).delete(faq);
    }

    @Test
    @DisplayName("4: 유효한 FAQ ID가 주어졌을 때 modify 메서드에서 수정한 FAQ의 ID를 반환한다.")
    void givenValidFaqId_whenModify_thenModifiedFaqId() throws Exception {
        //given
        final Long faqId = 1L;
        final var request = new FaqCommand.UpdateFaqRequest(faqId, "FAQ 제목", "FAQ 내용");
        final Faq faq = new Faq(faqId, "제목", "내용");

        //when
        when(faqReader.findById(faqId)).thenReturn(faq);
        var response = faqServiceImpl.modify(request);

        //then
        assertThat(response.getFaqId()).isEqualTo(faqId);
        verify(faqReader, times(1)).findById(faqId);
    }
}