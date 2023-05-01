package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.currency.ICurrencyGetter;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class CurrencyUpdateServiceTest {

    @InjectMocks
    CurrencyUpdateService currencyUpdateService;

    @Mock
    CurrencyService currencyService;

    @Mock
    ICurrencyGetter currencyGetter;

    @Test
    public void testGetCNB() {
        Currency currency0 = new Currency("CZK", "Česká republika", 5);
        Currency currency1 = new Currency("EUR", "EMU", 20);

        List<Currency> currencyList = new ArrayList<>();
        currencyList.add(currency0);
        currencyList.add(currency1);

        Mockito.when(currencyGetter.getCurrencies()).thenReturn(currencyList);

        for (Currency currency : currencyList) {
            Mockito.when(currencyService.readById(currency.getCode())).thenReturn(Optional.of(currency));
            Mockito.when(currencyService.update(currency)).thenReturn(currency);
        }

        assertDoesNotThrow(() -> currencyUpdateService.updateCurrencies());
    }
}
