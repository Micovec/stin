package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.dao.CurrencyJpaRepository;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {

    @InjectMocks
    CurrencyService currencyService;

    @Mock
    CurrencyJpaRepository currencyRepository;

    @Test
    public void testExists() {
        Currency currency = new Currency("CZK", "Česká republika", 5);

        Mockito.when(currencyRepository.existsById(currency.getCode())).thenReturn(true);

        assertTrue(currencyService.exists(currency));
    }
}
