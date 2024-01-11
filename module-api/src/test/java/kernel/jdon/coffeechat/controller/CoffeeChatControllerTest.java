package kernel.jdon.coffeechat.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import kernel.jdon.coffeechat.service.CoffeeChatService;

@WebMvcTest(CoffeeChatController.class)
class CoffeeChatControllerTest {

	@MockBean
	private CoffeeChatService coffeeChatService;

	@Autowired
	private MockMvc mockMvc;

}