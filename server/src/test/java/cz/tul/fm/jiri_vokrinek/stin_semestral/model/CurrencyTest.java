package cz.tul.fm.jiri_vokrinek.stin_semestral.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CurrencyTest {

    @Test
    public void testCtor() {
        assertDoesNotThrow(() -> new Currency());
    }

    @Test
    public void testSetters() {
        Currency currency = new Currency();

        double rate = 90;
        String code = "PHP";
        String country = "India";

        currency.setRate(rate);
        currency.setCode(code);
        currency.setCountry(country);

        assertEquals(currency.getRate(), rate);
        assertEquals(currency.getCode(), code);
        assertEquals(currency.getCountry(), country);
    }
}
