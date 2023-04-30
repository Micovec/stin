package cz.tul.fm.jiri_vokrinek.stin_semestral.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.UserLoginInfoDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.security.CCUserDetails;
import cz.tul.fm.jiri_vokrinek.stin_semestral.security.MfaAuthentication;
import dev.samstevens.totp.code.CodeVerifier;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @MockBean
    SecurityFilterChain web;

    @MockBean
    AuthenticationSuccessHandler successHandler;

    @MockBean
    AuthenticationFailureHandler failureHandler;

    @MockBean
    CodeVerifier codeVerifier;

    @InjectMocks
    LoginController loginController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetVerify() throws Exception {
        mockMvc.perform(get("/verify"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostVerifyValidCode() {
        String code = "code";

        UserLoginInfoDto loginDto = new UserLoginInfoDto("test@test.test", "test", "secret");
        CCUserDetails userDetails = new CCUserDetails(loginDto);

        Mockito.when(codeVerifier.isValidCode(loginDto.getSecret(), code)).thenReturn(true);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        Assertions.assertThrows(NullPointerException.class, () -> loginController.processVerify(code, new MfaAuthentication(authentication), null, null));
    }

    @Test
    public void testPostVerifyInvalidCode() {
        String code = "code";

        UserLoginInfoDto loginDto = new UserLoginInfoDto("test@test.test", "test", "secret");
        CCUserDetails userDetails = new CCUserDetails(loginDto);

        Mockito.when(codeVerifier.isValidCode(loginDto.getSecret(), code)).thenReturn(false);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        Assertions.assertThrows(NullPointerException.class, () -> loginController.processVerify(code, new MfaAuthentication(authentication), null, null));
    }
}
