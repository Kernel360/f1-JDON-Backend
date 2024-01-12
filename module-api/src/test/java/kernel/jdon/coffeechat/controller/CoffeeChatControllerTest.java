package kernel.jdon.coffeechat.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kernel.jdon.coffeechat.dto.request.CreateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.response.CreateCoffeeChatResponse;
import kernel.jdon.coffeechat.service.CoffeeChatService;

@WebMvcTest(CoffeeChatController.class)
class CoffeeChatControllerTest {

	@MockBean
	private CoffeeChatService coffeeChatService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@DisplayName("커피챗 생성 성공")
	@Test
	void createCoffeeChat() throws Exception {
		//given
		CreateCoffeeChatRequest request = createCoffeeChatRequest();
		CreateCoffeeChatResponse response = createCoffeeChatResponse();

		doReturn(response).when(coffeeChatService)
			.create(any(CreateCoffeeChatRequest.class));

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post("/api/v1/coffeechats")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request))
		);

		//then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.coffeeChatId").value(1L));

	}

	private CreateCoffeeChatRequest createCoffeeChatRequest() {
		return CreateCoffeeChatRequest.builder()
			.title("chat1")
			.content("content1")
			.totalRecruitCount(5L)
			.meetDate(LocalDateTime.parse("2024-02-06T00:00:00"))
			.openChatUrl("https://open.kakao.com/o/abc")
			.build();
	}

	private CreateCoffeeChatResponse createCoffeeChatResponse() {
		return new CreateCoffeeChatResponse(1L);
	}

	private <T> String toJson(T data) throws JsonProcessingException {
		return objectMapper.writeValueAsString(data);
	}

}