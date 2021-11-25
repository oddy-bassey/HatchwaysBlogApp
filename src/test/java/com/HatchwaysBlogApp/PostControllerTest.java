package com.HatchwaysBlogApp;

import com.HatchwaysBlogApp.controller.PostController;
import com.HatchwaysBlogApp.dto.AuthenticationResponse;
import com.HatchwaysBlogApp.dto.LoginRequest;
import com.HatchwaysBlogApp.dto.PostRequest;
import com.HatchwaysBlogApp.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private PostController postController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private final String headerName = "Authorization";

    @Test
    public void contextLoads(){
        assertThat(postController).isNotNull();
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void createBlogPostTest() throws Exception {
        createBlogPost("World's Most Deadly/Beautiful Thing",
                "https://github.com/oddy-bassey/HatchwaysBlogApp/",
                "The human mind can without a doubt be the most deadliest thing there is in the world " +
                        "or the most beautiful thing there is in the world.");
    }

    @Test
    public void getAllBlogPostTest() throws Exception {

        String authToken = createBlogPost("TOGAF",
                "https://en.wikipedia.org/wiki/The_Open_Group_Architecture_Framework",
                "The Open Group Architecture Framework (TOGAF) is the most used framework for enterprise architecture" +
                        " as of 2020 that provides an approach for designing, planning, implementing, and governing an enterprise" +
                        " information technology architecture. TOGAF is a high-level approach to design.");

        getAllBlogPost(authToken);
    }

    @Test
    public void getBlogPostByIdTest() throws Exception {
        String authToken = createBlogPost("Java Stream",
                "https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwjqwoOyxrP0AhUMSvEDHbUOBxAQFnoECA4QAw&url=https%3A%2F%2Fwww.geeksforgeeks.org%2Fstream-in-java%2F&usg=AOvVaw3fTbCEFEjMLFawELMY0WBq",
                "A stream is a sequence of objects that supports various methods" +
                        " which can be pipelined to produce the desired result. ");

        getBlogPostById(1L, authToken);
    }

    @Test
    public void getBlogPostByUsernameTest() throws Exception {
        String authToken = createBlogPost("Java Stream",
                "https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwjqwoOyxrP0AhUMSvEDHbUOBxAQFnoECA4QAw&url=https%3A%2F%2Fwww.geeksforgeeks.org%2Fstream-in-java%2F&usg=AOvVaw3fTbCEFEjMLFawELMY0WBq",
                "A stream is a sequence of objects that supports various methods" +
                        " which can be pipelined to produce the desired result. ");

        getBlogPostByUsername("Dave", authToken);
    }


    private String createBlogPost(String title, String url, String description) throws Exception{

        PostRequest newPost = getPostRequest(title, url, description);
        String newPostJson = objectMapper.writeValueAsString(newPost);
        String authToken = getAuthToken();

        mockMvc.perform(post("/api/posts/")
                .contentType(MediaType.APPLICATION_JSON)
                .header(headerName, authToken)
                .content(newPostJson))
                .andExpect(status().isCreated());

        return authToken;
    }

    private void getAllBlogPost(String authToken) throws Exception{

        mockMvc.perform(get("/api/posts/")
                .header(headerName, authToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void getBlogPostById(long postId, String authToken) throws Exception{

        mockMvc.perform(get("/api/posts/"+postId)
                .header(headerName, authToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void getBlogPostByUsername(String username, String authToken) throws Exception{

        mockMvc.perform(get("/api/posts/by-user/"+username)
                .header(headerName, authToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private PostRequest getPostRequest(String title, String url, String description){

        return PostRequest.builder()
                .title(title)
                .url(url)
                .description(description)
                .build();
    }

    private String getAuthToken() throws Exception{

        //creating a user
        RegisterRequest newUser = RegisterRequest.builder()
                .firstname("David")
                .lastname("Solomon")
                .username("Dave")
                .email("davidsolomon@hatchway.com")
                .password("password")
                .build();

        String newUserJson = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andExpect(status().isOk());

        //creating a login request with new user credentials
        LoginRequest user = LoginRequest.builder()
                .username("Dave")
                .password("password")
                .build();

        String userJson = objectMapper.writeValueAsString(user);

        ResultActions result = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());

        String responseJson = result.andReturn().getResponse().getContentAsString();
        AuthenticationResponse authResponse = objectMapper.readValue(responseJson, AuthenticationResponse.class);

        return "Bearer "+authResponse.getAuthenticationToken();
    }
}
