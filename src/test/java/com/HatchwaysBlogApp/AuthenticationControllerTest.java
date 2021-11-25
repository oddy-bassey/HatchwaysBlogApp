package com.HatchwaysBlogApp;

import com.HatchwaysBlogApp.controller.AuthenticationController;
import com.HatchwaysBlogApp.dto.LoginRequest;
import com.HatchwaysBlogApp.dto.RegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

	@Autowired
	private AuthenticationController authController;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void contextLoads(){
		assertThat(authController).isNotNull();
	}

	@Test
	void registerUserTest() throws Exception {

		//register a new user
		registerUser("David", "Richard", "Richie");
	}

	@Test
	void loginUserExistsTest() throws Exception {

		//register a new user
		registerUser("Mike", "Coleman", "Micole");

		//login with new user
		loginUser("Micole");
	}

	private void registerUser(String firstname, String lastname, String username) throws Exception {

		RegisterRequest newUser = getRegisterRequest(firstname, lastname, username);
		String newUserJson = objectMapper.writeValueAsString(newUser);

		mockMvc.perform(post("/api/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newUserJson))
				.andExpect(status().isOk());
	}

	private void loginUser(String username) throws Exception{

		LoginRequest user = getLoginRequest(username);
		String userJson = objectMapper.writeValueAsString(user);

		mockMvc.perform(post("/api/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
				.andExpect(status().isOk());
	}

	private RegisterRequest getRegisterRequest(String firstname, String lastname, String username){

		return RegisterRequest.builder()
				.firstname(firstname)
				.lastname(lastname)
				.username(username)
				.email(lastname+firstname+"@hatchway.com")
				.password("password")
				.build();
	}

	private LoginRequest getLoginRequest(String username){

		return LoginRequest.builder()
				.username(username)
				.password("password")
				.build();
	}
}
