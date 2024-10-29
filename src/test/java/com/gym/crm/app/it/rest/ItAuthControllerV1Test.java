package com.gym.crm.app.it.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.crm.app.it.AbstractItTest;
import com.gym.crm.app.rest.model.ActivateDeactivateProfileRequest;
import com.gym.crm.app.rest.model.ChangePasswordRequest;
import com.gym.crm.app.rest.model.UserCredentials;
import com.gym.crm.app.utils.EntityTestData;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.gym.crm.app.rest.exception.ErrorCode.INVALID_USERNAME;
import static com.gym.crm.app.rest.exception.ErrorCode.INVALID_USERNAME_OR_PASSWORD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class ItAuthControllerV1Test extends AbstractItTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test login by valid user functionality")
    void givenValidUserCredentials_whenLogin_thenSuccessResponse() throws Exception {
        // given
        UserCredentials userCredentials = EntityTestData.getValidJohnDoeAuthCredentials();

        // when
        ResultActions result = mvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCredentials)));

        // then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test login by invalid user functionality")
    void givenInvalidUserCredentials_whenLogin_thenSuccessResponse() throws Exception {
        // given
        UserCredentials userCredentials = EntityTestData.getInvalidJohnDoeAuthCredentials();

        // when
        ResultActions result = mvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCredentials)));

        // then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is(INVALID_USERNAME_OR_PASSWORD.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Invalid username or password")));
    }

    @Test
    @DisplayName("Test logout functionality")
    void givenAuthenticatedUser_whenLogout_thenSuccessResponse() throws Exception {
        // given
        UserCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
        String token = login(credentials);

        // when
        ResultActions result = mvc.perform(get("/api/v1/logout")
                .header("Authorization", token));

        // then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test change password by valid request functionality")
    void givenValidRequest_whenChangePassword_thenSuccessResponse() throws Exception {
        // given
        UserCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
        String token = login(credentials);

        ChangePasswordRequest request = EntityTestData.getValidChangePasswordRequest();

        // when
        ResultActions result = mvc.perform(post("/api/v1/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", token));

        // then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test change password by invalid request functionality")
    void givenInvalidRequest_whenChangePassword_thenSuccessResponse() throws Exception {
        // given
        UserCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
        String token = login(credentials);

        ChangePasswordRequest request = EntityTestData.getInvalidChangePasswordRequest();

        // when
        ResultActions result = mvc.perform(post("/api/v1/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", token));

        // then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is(INVALID_USERNAME_OR_PASSWORD.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Invalid username or password")));
    }

    @Test
    @DisplayName("Test activate profile functionality")
    void givenValidRequest_whenActivateProfile_thenSuccessResponse() throws Exception {
        // given
        UserCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
        String token = login(credentials);

        String username = "John.Doe";
        ActivateDeactivateProfileRequest request = EntityTestData.getActivateProfileRequest();

        // when
        String uri = UriComponentsBuilder.fromPath("/api/v1/{username}/activate")
                .buildAndExpand(username)
                .toUriString();

        ResultActions result = mvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", token));

        // then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test activate profile by incorrect user functionality")
    void givenInvalidUser_whenActivateProfile_thenSuccessResponse() throws Exception {
        // given
        UserCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
        String token = login(credentials);

        String username = "Emily.Davis";
        ActivateDeactivateProfileRequest request = EntityTestData.getActivateProfileRequest();

        // when
        String uri = UriComponentsBuilder.fromPath("/api/v1/{username}/activate")
                .buildAndExpand(username)
                .toUriString();

        ResultActions result = mvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", token));

        // then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is(INVALID_USERNAME.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Invalid username")));
    }

    @Test
    @DisplayName("Test deactivate profile functionality")
    void givenValidRequest_whenDeactivateProfile_thenSuccessResponse() throws Exception {
        // given
        UserCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
        String token = login(credentials);

        String username = "John.Doe";
        ActivateDeactivateProfileRequest request = EntityTestData.getDeactivateProfileRequest();

        // when
        String uri = UriComponentsBuilder.fromPath("/api/v1/{username}/activate")
                .buildAndExpand(username)
                .toUriString();

        ResultActions result = mvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", token));

        // then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String login(UserCredentials userCredentials) throws Exception {
        ResultActions result = mvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCredentials)));

        return result.andReturn().getResponse().getHeader("Authorization");
    }
}
