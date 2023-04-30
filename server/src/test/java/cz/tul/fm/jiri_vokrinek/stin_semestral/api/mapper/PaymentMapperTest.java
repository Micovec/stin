package cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentRequestDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Payment;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.supporting.PaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentMapperTest {

    PaymentMapper paymentMapper;

    @BeforeEach
    public void initialize() {
        paymentMapper = new PaymentMapper();
    }

    @Test
    public void testToDtos() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Currency sourceCurrency = new Currency("CZK", "Česká republika", 50);
        Currency targetCurrency = new Currency("EUR", "Německo", 90);

        Payment payment0 = new Payment(user, 10, sourceCurrency, targetCurrency, true);
        Payment payment1 = new Payment(user, 9, sourceCurrency, targetCurrency, false);

        List<Payment> payments = new ArrayList<>();
        payments.add(payment0);
        payments.add(payment1);

        List<PaymentDto> paymentDtos = paymentMapper.toDtos(payments).stream().toList();

        assertEquals(payments.size(), paymentDtos.size());

        for (int i = 0; i < payments.size(); i++) {
            assertEquals(payments.get(i).getAmount(), paymentDtos.get(i).amount);
            assertEquals(payments.get(i).getSourceCurrency().getCode(), paymentDtos.get(i).currencyCode);
            assertEquals(payments.get(i).getDate(), paymentDtos.get(i).date);
            assertEquals(payments.get(i).getTargetCurrency().getCode(), paymentDtos.get(i).targetCurrencyCode);
            assertEquals(payments.get(i).isValid(), paymentDtos.get(i).valid);
        }
    }

    @Test
    public void testPaymentRequestDtoToModel() {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto("test@test.test", 90, "CZK", "EUR");
        PaymentRequest paymentRequest = paymentMapper.paymentRequestDtoToModel(paymentRequestDto);

        assertEquals(paymentRequestDto.amount, paymentRequest.amount());
        assertEquals(paymentRequestDto.currencyCode, paymentRequest.currencyCode());
        assertEquals(paymentRequestDto.email, paymentRequest.email());
        assertEquals(paymentRequestDto.targetCurrencyCode, paymentRequest.targetCurrencyCode());
    }
}
