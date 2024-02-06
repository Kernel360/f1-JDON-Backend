package kernel.jdon.moduleapi.coffeechat.controller;

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

import kernel.jdon.coffeechat.controller.CoffeeChatController;
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

	@DisplayName("유효한 요청으로 커피챗 생성 성공시 생성된 커피챗의 ID를 반환한다.")
	@WithMockUser
	@Test
	void givenValidRequest_whenCreateCoffeeChat_thenReturnCreatedCoffeeChatId() throws Exception {
		//given
		CreateCoffeeChatRequest request = createCoffeeChatRequest();
		CreateCoffeeChatResponse response = createCoffeeChatResponse();

		doReturn(response).when(coffeeChatService)
			.create(any(CreateCoffeeChatRequest.class), any(Long.class));

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

	@DisplayName("유효한 커피챗 ID로 상세 조회 시 정상 응답과 데이터를 반환한다.")
	@WithMockUser
	@Test
	void givenValidCoffeeChatId_whenFindCoffeeChat_thenReturnValidCoffeeChatResponse() throws Exception {
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
			.andExpect(jsonPath("$.data.meetDate").exists())
			.andExpect(jsonPath("$.data.createdDate").exists())
			.andExpect(jsonPath("$.data.totalRecruitCount").value(5))
			.andExpect(jsonPath("$.data.currentRecruitCount").value(3))
			.andExpect(jsonPath("$.data.nickname").value("마틴 파울러"));
	}

	@DisplayName("유효한 요청으로 커피챗 수정 성공시 수정된 커피챗의 ID를 반환한다.")
	@WithMockUser
	@Test
	void givenValidUpdateRequest_whenUpdateCoffeeChat_thenReturnUpdatedCoffeeChatId() throws Exception {
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

	@DisplayName("유효한 커피챗 ID로 삭제 성공 시 정상응답과 삭제된 커피챗의 ID를 반환한다.")
	@WithMockUser
	@Test
	void givenValidCoffeeChatId_whenDeleteCoffeeChat_thenReturnDeletedCoffeeChatId() throws Exception {
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