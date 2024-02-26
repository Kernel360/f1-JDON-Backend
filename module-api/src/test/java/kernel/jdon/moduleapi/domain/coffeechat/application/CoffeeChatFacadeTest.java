package kernel.jdon.moduleapi.domain.coffeechat.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatService;

@ExtendWith(MockitoExtension.class)
class CoffeeChatFacadeTest {

    @Mock
    private CoffeeChatService coffeeChatService;
    @InjectMocks
    private CoffeeChatFacade coffeeChatFacade;

    @Test
    @DisplayName("커피챗 조회 성공 시, 해당 커피챗 조회수를 증가시키고, 올바른 응답을 반환한다")
    void givenValidId_whenGetCoffeeChat_thenIncreaseViewCount_thenReturnCorrectResponse() {
        //given
        Long coffeeChatId = 1L;
        CoffeeChatInfo.FindCoffeeChatResponse mockFindCoffeeChatResponse = mock(
            CoffeeChatInfo.FindCoffeeChatResponse.class);
        when(coffeeChatService.getCoffeeChat(coffeeChatId)).thenReturn(mockFindCoffeeChatResponse);

        //when
        CoffeeChatInfo.FindCoffeeChatResponse response = coffeeChatFacade.getCoffeeChat(coffeeChatId);

        //then
        assertThat(response).isEqualTo(mockFindCoffeeChatResponse);

        //verify
        verify(coffeeChatService, times(1)).getCoffeeChat(coffeeChatId);
        verify(coffeeChatService, times(1)).increaseViewCount(coffeeChatId);
    }
}
