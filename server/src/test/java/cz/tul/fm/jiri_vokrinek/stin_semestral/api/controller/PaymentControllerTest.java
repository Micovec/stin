package cz.tul.fm.jiri_vokrinek.stin_semestral.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.EmailDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentRequestDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper.PaymentMapper;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Payment;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.supporting.PaymentRequest;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.PaymentService;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @MockBean
    PaymentService paymentService;

    @MockBean
    UserService userService;

    @MockBean
    PaymentMapper paymentMapper;

    @Autowired
    MockMvc mock;

    @Test
    public void testGetPayments() throws Exception {
        Optional<User> user = Optional.of(new User("test@test.test", "Test", "Test", "test"));

        Currency sourceCurrency = new Currency("CZK", "Česká republika", 1);
        Currency targetCurrency = new Currency("EUR", "EUM", 20);

        Payment payment0 = new Payment(user.get(), 10, sourceCurrency, targetCurrency, true);
        Payment payment1 = new Payment(user.get(), 50, sourceCurrency, targetCurrency, false);
        Payment payment2 = new Payment(user.get(), 4.8, sourceCurrency, targetCurrency, true);

        Collection<Payment> payments = new ArrayList<>();
        payments.add(payment0);
        payments.add(payment1);
        payments.add(payment2);

        Collection<PaymentDto> paymentDtos = new ArrayList<>();
        paymentDtos.add(new PaymentDto(payment0.getAmount(), payment0.getSourceCurrency().getCode(), payment0.getTargetCurrency().getCode(), payment0.getDate(), payment0.isValid()));
        paymentDtos.add(new PaymentDto(payment1.getAmount(), payment1.getSourceCurrency().getCode(), payment1.getTargetCurrency().getCode(), payment1.getDate(), payment1.isValid()));
        paymentDtos.add(new PaymentDto(payment2.getAmount(), payment2.getSourceCurrency().getCode(), payment2.getTargetCurrency().getCode(), payment2.getDate(), payment2.isValid()));

        Mockito.when(userService.readById(user.get().getEmail())).thenReturn(user);
        Mockito.when(paymentService.getUserPayments(user.get())).thenReturn(payments);
        Mockito.when(paymentMapper.toDtos(payments)).thenReturn(paymentDtos);

        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mock.perform(post("/payment/records")
                        .header("Content-Type", "application/json")
                        .content(writer.writeValueAsString(new EmailDto(user.get().getEmail()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].amount", Matchers.is(payment0.getAmount())))
                .andExpect(jsonPath("$[0].currencyCode", Matchers.is(payment0.getSourceCurrency().getCode())))
                .andExpect(jsonPath("$[0].targetCurrencyCode", Matchers.is(payment0.getTargetCurrency().getCode())))
                .andExpect(jsonPath("$[0].date", Matchers.is(payment0.getDate().toString())))
                .andExpect(jsonPath("$[0].valid", Matchers.is(payment0.isValid())))
                .andExpect(jsonPath("$[1].amount", Matchers.is(payment1.getAmount())))
                .andExpect(jsonPath("$[1].currencyCode", Matchers.is(payment1.getSourceCurrency().getCode())))
                .andExpect(jsonPath("$[1].targetCurrencyCode", Matchers.is(payment1.getTargetCurrency().getCode())))
                .andExpect(jsonPath("$[1].date", Matchers.is(payment1.getDate().toString())))
                .andExpect(jsonPath("$[1].valid", Matchers.is(payment1.isValid())))
                .andExpect(jsonPath("$[2].amount", Matchers.is(payment2.getAmount())))
                .andExpect(jsonPath("$[2].currencyCode", Matchers.is(payment2.getSourceCurrency().getCode())))
                .andExpect(jsonPath("$[2].targetCurrencyCode", Matchers.is(payment2.getTargetCurrency().getCode())))
                .andExpect(jsonPath("$[2].date", Matchers.is(payment2.getDate().toString())))
                .andExpect(jsonPath("$[2].valid", Matchers.is(payment2.isValid())));
    }

    @Test
    public void testPay() throws Exception {
        Optional<User> user = Optional.of(new User("test@test.test", "Test", "Test", "test"));

        Currency sourceCurrency = new Currency("CZK", "Česká republika", 1);
        Currency targetCurrency = new Currency("EUR", "EUM", 20);

        Payment payment = new Payment(user.get(), 10, sourceCurrency, targetCurrency, true);

        PaymentRequest paymentRequest = new PaymentRequest(user.get().getEmail(), sourceCurrency.getCode(), targetCurrency.getCode(), payment.getAmount());
        PaymentDto paymentDto = new PaymentDto(payment.getAmount(), payment.getSourceCurrency().getCode(), payment.getTargetCurrency().getCode(), payment.getDate(), payment.isValid());

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(paymentRequest.email(), payment.getAmount(), paymentRequest.currencyCode(), paymentRequest.targetCurrencyCode());

        Mockito.when(paymentMapper.paymentRequestDtoToModel(paymentRequestDto)).thenReturn(paymentRequest);
        Mockito.when(paymentService.pay(paymentRequest)).thenReturn(payment);
        Mockito.when(paymentMapper.paymentToDto(payment)).thenReturn(paymentDto);

        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mock.perform(post("/payment")
                    .header("Content-Type", "application/json")
                    .content(writer.writeValueAsString(paymentRequestDto)))
                .andExpect(status().isOk());
    }
}
