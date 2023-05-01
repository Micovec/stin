package cz.tul.fm.jiri_vokrinek.stin_semestral.currency;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CurrencyGetterFactoryTest {

    @Test
    public void testGetCNBCurrencyGetter() {
        ICurrencyGetter currencyGetter = CurrencyGetterFactory.getCurrencyGetter(CurrencyGetterFactory.Source.CNB);
        assertNotNull(currencyGetter);
    }
}
