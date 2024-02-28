package kernel.jdon.moduleapi.domain.coffeechat.core;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;

@ExtendWith(MockitoExtension.class)
class CoffeeChatServiceImplTest {

    @Mock
    private CoffeeChatReader coffeeChatReader;
    @Mock
    private CoffeeChatInfoMapper coffeeChatInfoMapper;

    @InjectMocks
    private CoffeeChatServiceImpl coffeeChatService;

    @Test
    @DisplayName("유효한 ID로 커피챗 조회 성공 시, 올바른 응답을 반환한다")
    void givenValidCoffeeChatId_whenGetCoffeeChat_thenReturnCorrectCoffeeChat() {
        //given
        Long memberId = 2L;
        Long coffeeChatId = 1L;
        CoffeeChat mockCoffeeChat = mockCoffeeChat();
        CoffeeChatInfo.FindCoffeeChatResponse mockFindCoffeeChatResponse = mockFindResponse();

        //when
        when(coffeeChatReader.findExistCoffeeChat(coffeeChatId)).thenReturn(mockCoffeeChat);
        when(coffeeChatInfoMapper.of(eq(mockCoffeeChat), anyBoolean())).thenReturn(mockFindCoffeeChatResponse);

        //then
        CoffeeChatInfo.FindCoffeeChatResponse response = coffeeChatService.getCoffeeChat(coffeeChatId, memberId);
        Assertions.assertThat(response).isEqualTo(mockFindCoffeeChatResponse);

        //verify
        verify(coffeeChatReader, times(1)).findExistCoffeeChat(coffeeChatId);
        verify(coffeeChatInfoMapper, times(1)).of(eq(mockCoffeeChat), anyBoolean());
    }

    private CoffeeChat mockCoffeeChat() {
        return CoffeeChat.builder()
            .id(1L)
            .build();
    }

    private CoffeeChatInfo.FindCoffeeChatResponse mockFindResponse() {
        return CoffeeChatInfo.FindCoffeeChatResponse.builder()
            .coffeeChatId(1L)
            .hostId(2L)
            .nickname("마틴파울러")
            .hostJobCategoryName("bakcend")
            .title("커피챗제목1")
            .content("커피챗내용1")
            .status("OPEN")
            .createdDate(LocalDateTime.now().minusDays(1L))
            .meetDate(LocalDateTime.now().plusDays(1L))
            .openChatUrl("https://openchat.com")
            .viewCount(3000L)
            .currentRecruitCount(3L)
            .totalRecruitCount(5L)
            .build();
    }

}
