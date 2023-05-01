package cz.tul.fm.jiri_vokrinek.stin_semestral.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PaymentTest {

    @Test
    public void testCtor() {
        assertDoesNotThrow(() -> new Payment());
    }

    @Test
    public void testGetUser() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Currency sourceCurrency = new Currency("CZK", "Česká republika", 1);
        Currency targetCurrency = new Currency("EUR", "EUM", 20);

        Payment payment = new Payment(user, 20, sourceCurrency, targetCurrency, true);

        assertEquals(user, payment.getUser());
    }

    @Test
    public void testSetValid() {
        User user = new User("test@test.test", "Test", "Test", "test");

        Currency sourceCurrency = new Currency("CZK", "Česká republika", 1);
        Currency targetCurrency = new Currency("EUR", "EUM", 20);

        Payment payment = new Payment(user, 20, sourceCurrency, targetCurrency, true);

        payment.setValid(false);

        assertFalse(payment.isValid());
    }
}
