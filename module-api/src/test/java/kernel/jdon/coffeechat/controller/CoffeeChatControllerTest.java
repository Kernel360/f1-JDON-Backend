package kernel.jdon.coffeechat.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kernel.jdon.coffeechat.dto.request.CreateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.request.UpdateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.response.CreateCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.DeleteCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.UpdateCoffeeChatResponse;
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
	@WithMockUser
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
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request))
		);

		//then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.coffeeChatId").value(1L));

	}

	@DisplayName("커피챗 상세조회 성공")
	@WithMockUser
	@Test
	void getCoffeeChat() throws Exception {
		//given
		Long coffeeChatId = 1L;
		FindCoffeeChatResponse response = findCoffeeChatResponse();
		when(coffeeChatService.find(coffeeChatId)).thenReturn(response);

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get("/api/v1/coffeechats/{id}", coffeeChatId));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.coffeeChatId").value(coffeeChatId))
			.andExpect(jsonPath("$.data.nickname").value("마틴 파울러"));

	}

	@DisplayName("커피챗 수정 성공")
	@WithMockUser
	@Test
	void modifyCoffeeChat() throws Exception {
		//given
		Long updateCoffeeChatID = 1L;
		UpdateCoffeeChatRequest request = updateCoffeeChatRequest();
		UpdateCoffeeChatResponse response = new UpdateCoffeeChatResponse(updateCoffeeChatID);
		doReturn(response).when(coffeeChatService).update(any(Long.class), any(UpdateCoffeeChatRequest.class));

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.put("/api/v1/coffeechats/{id}", updateCoffeeChatID)
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request))
		);

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.coffeeChatId").value(updateCoffeeChatID));
	}

	@DisplayName("커피챗 삭제 성공")
	@WithMockUser
	@Test
	void removeCoffeeChat() throws Exception {
		//given
		Long deleteCoffeeChatId = 1L;
		DeleteCoffeeChatResponse response = deleteCoffeeChatResponse();
		doReturn(response).when(coffeeChatService).delete(any(Long.class));

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.delete("/api/v1/coffeechats/{id}", deleteCoffeeChatId)
				.with(csrf())
		);

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.coffeeChatId").value(deleteCoffeeChatId));
	}

	private FindCoffeeChatResponse findCoffeeChatResponse() {

		return FindCoffeeChatResponse.builder()
			.coffeeChatId(1L)
			.nickname("마틴 파울러")
			.title("커피챗제목1")
			.content("커피챗내용1")
			.viewCount(1000L)
			.status("모집중")
			.meetDate(LocalDateTime.now())
			.createdDate(LocalDateTime.now())
			.openChatUrl("https://open.kakao.com/o/abc")
			.totalRecruitCount(5L)
			.currentRecruitCount(3L)
			.job("backend")
			.build();
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

	private UpdateCoffeeChatRequest updateCoffeeChatRequest() {

		return UpdateCoffeeChatRequest.builder()
			.title("커피챗제목1")
			.content("커피챗내용1")
			.totalRecruitCount(10L)
			.meetDate(LocalDateTime.now())
			.openChatUrl("https://open.kakao.com/o/def")
			.build();
	}

	private DeleteCoffeeChatResponse deleteCoffeeChatResponse() {
		return new DeleteCoffeeChatResponse(1L);
	}

}