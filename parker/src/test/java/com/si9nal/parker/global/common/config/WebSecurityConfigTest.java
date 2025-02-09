package com.si9nal.parker.global.common.config;

import com.si9nal.parker.ParkerApplication;
import com.si9nal.parker.global.common.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ParkerApplication.class)
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application.yml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WebSecurityConfigTest {

    @MockBean
    private TokenProvider tokenProvider;  // TokenProvider를 MockBean으로 등록

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("CORS Preflight 요청 테스트 - 허용된 Origin")
    void corsPreflightWithAllowedOrigin() throws Exception {
        // given
        String allowedOrigin = "http://localhost:3000";

        // when
        ResultActions result = mockMvc.perform(options("/api/user/signup")
                .header("Origin", allowedOrigin)
                .header("Access-Control-Request-Method", "POST")
                .header("Access-Control-Request-Headers", "Authorization"));

        // then
        result.andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", allowedOrigin))
                .andExpect(header().string("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS"))
                .andExpect(header().exists("Access-Control-Allow-Headers"))
                .andExpect(header().string("Access-Control-Allow-Credentials", "true"))
                .andExpect(header().exists("Access-Control-Max-Age"));
    }

    @Test
    @DisplayName("CORS Preflight 요청 테스트 - 허용되지 않은 Origin")
    void corsPreflightWithNotAllowedOrigin() throws Exception {
        // given
        String notAllowedOrigin = "http://malicious-site.com";

        // when
        ResultActions result = mockMvc.perform(options("/api/user/signup")
                .header("Origin", notAllowedOrigin)
                .header("Access-Control-Request-Method", "POST")
                .header("Access-Control-Request-Headers", "Authorization"));

        // then
        result.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("CORS Preflight 요청 테스트 - 허용되지 않은 HTTP 메소드")
    void corsPreflightWithNotAllowedMethod() throws Exception {
        // given
        String allowedOrigin = "http://localhost:3000";

        // when
        ResultActions result = mockMvc.perform(options("/api/user/signup")
                .header("Origin", allowedOrigin)
                .header("Access-Control-Request-Method", "TRACE")
                .header("Access-Control-Request-Headers", "Authorization"));

        // then
        result.andExpect(status().isForbidden());
    }
}