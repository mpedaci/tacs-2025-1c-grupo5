package utn.tacs.grupo5.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import utn.tacs.grupo5.TestSecurityConfig;
import utn.tacs.grupo5.dto.auth.AuthInputDto;
import utn.tacs.grupo5.dto.auth.AuthOutputDto;
import utn.tacs.grupo5.service.IAuthService;

@WebMvcTest(controllers = AuthController.class)
@Import(TestSecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    IAuthService authService;

    @Test
    void testLogin() throws Exception {
        AuthInputDto authInputDto = new AuthInputDto();
        authInputDto.setUsername("testUser");
        authInputDto.setPassword("testPassword");
        AuthOutputDto authOutputDto = new AuthOutputDto("mockToken");

        when(authService.login(authInputDto)).thenReturn(authOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("mockToken"));

        verify(authService, times(1)).login(authInputDto);
    }

    @Test
    void testRefresh() throws Exception {
        String token = "mockToken";
        AuthOutputDto authOutputDto = new AuthOutputDto("newMockToken");

        when(authService.refreshToken(token)).thenReturn(authOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/refresh")
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("newMockToken"));

        verify(authService, times(1)).refreshToken(token);
    }

    @Test
    void testLogout() throws Exception {
        String token = "mockToken";

        doNothing().when(authService).logout(token);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/logout")
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(authService, times(1)).logout(token);
    }

}
