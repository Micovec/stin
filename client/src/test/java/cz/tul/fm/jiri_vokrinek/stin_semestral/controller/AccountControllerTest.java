package cz.tul.fm.jiri_vokrinek.stin_semestral.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.data.AccountData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.UserLoginInfoDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.security.CCUserDetails;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @MockBean
    AccountData accountData;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @MockBean
    SecurityFilterChain web;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAccountsSignedIn() throws Exception {
        UserLoginInfoDto loginDto = new UserLoginInfoDto("test@test.test", "test", "secret");
        CCUserDetails userDetails = new CCUserDetails(loginDto);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/account"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAccountsSignedOut() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/account"))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", "/"));
    }
}
