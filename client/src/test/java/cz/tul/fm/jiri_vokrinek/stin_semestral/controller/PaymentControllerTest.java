package cz.tul.fm.jiri_vokrinek.stin_semestral.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cz.tul.fm.jiri_vokrinek.stin_semestral.data.AccountData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.data.CurrencyData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.data.PaymentData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.data.UserData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.PaymentDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.PaymentRequestDto;
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

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @MockBean
    PaymentData paymentData;

    @MockBean
    AccountData accountData;

    @MockBean
    CurrencyData currencyData;

    @Autowired
    MockMvc mockMvc;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @MockBean
    SecurityFilterChain web;

    @Test
    public void testGetPageSignedIn() throws Exception {
        UserLoginInfoDto loginDto = new UserLoginInfoDto("test@test.test", "test", "secret");
        CCUserDetails userDetails = new CCUserDetails(loginDto);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/payment"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPageSignedOut() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/payment"))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", "/"));
    }

    @Test
    public void testPaySignedIn() throws Exception {
        UserLoginInfoDto loginDto = new UserLoginInfoDto("test@test.test", "test", "secret");
        CCUserDetails userDetails = new CCUserDetails(loginDto);

        PaymentRequestDto request = new PaymentRequestDto(loginDto.getEmail(), 10, "CZK", "EUR");
        PaymentDto paymentDto = new PaymentDto(request.amount, request.currencyCode, request.targetCurrencyCode, LocalDate.now(), true);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(paymentData.pay(request.email, request.amount, request.currencyCode, request.targetCurrencyCode)).thenReturn(paymentDto);

        SecurityContextHolder.setContext(securityContext);

        String body = String.join("&", "amount=" + request.amount, "currencyCode=" + request.currencyCode, "targetCurrencyCode=" + request.targetCurrencyCode);

        mockMvc.perform(post("/payment")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .content(body))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", "/"));
    }

    @Test
    public void testPaySignedInInvalidPayment() throws Exception {
        UserLoginInfoDto loginDto = new UserLoginInfoDto("test@test.test", "test", "secret");
        CCUserDetails userDetails = new CCUserDetails(loginDto);

        PaymentRequestDto request = new PaymentRequestDto(loginDto.getEmail(), 10, "CZK", "EUR");
        PaymentDto paymentDto = new PaymentDto(request.amount, request.currencyCode, request.targetCurrencyCode, LocalDate.now(), false);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(paymentData.pay(request.email, request.amount, request.currencyCode, request.targetCurrencyCode)).thenReturn(paymentDto);

        SecurityContextHolder.setContext(securityContext);

        String body = String.join("&", "amount=" + request.amount, "currencyCode=" + request.currencyCode, "targetCurrencyCode=" + request.targetCurrencyCode);

        mockMvc.perform(post("/payment")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .content(body))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", "/payment/invalid"));
    }

    @Test
    public void testPaySignedOut() throws Exception {
        UserLoginInfoDto loginDto = new UserLoginInfoDto("test@test.test", "test", "secret");

        PaymentRequestDto request = new PaymentRequestDto(loginDto.getEmail(), 10, "CZK", "EUR");
        PaymentDto paymentDto = new PaymentDto(request.amount, request.currencyCode, request.targetCurrencyCode, LocalDate.now(), false);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(null);
        Mockito.when(paymentData.pay(request.email, request.amount, request.currencyCode, request.targetCurrencyCode)).thenReturn(paymentDto);

        SecurityContextHolder.setContext(securityContext);

        String body = String.join("&", "amount=" + request.amount, "currencyCode=" + request.currencyCode, "targetCurrencyCode=" + request.targetCurrencyCode);

        mockMvc.perform(post("/payment")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .content(body))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", "/"));
    }

    @Test
    public void testGetInvalid() throws Exception {
        UserLoginInfoDto loginDto = new UserLoginInfoDto("test@test.test", "test", "secret");
        CCUserDetails userDetails = new CCUserDetails(loginDto);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/payment/invalid"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetShowRecordsSignedIn() throws Exception {
        UserLoginInfoDto loginDto = new UserLoginInfoDto("test@test.test", "test", "secret");
        CCUserDetails userDetails = new CCUserDetails(loginDto);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/payment/records"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetShowRecordsSignedOut() throws Exception {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/payment/records"))
                .andExpect(status().is(302))
                .andExpect(header().string("Location", "/"));
    }
}
