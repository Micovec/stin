package cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.CurrencyDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyMapperTest {

    CurrencyMapper mapper;

    @BeforeEach
    public void initialize() {
        mapper = new CurrencyMapper();
    }

    @Test
    public void testToDtos() {
        Currency currency0 = new Currency("CZK", "EUR", 50);
        Currency currency1 = new Currency("PHP", "GBR", 60);

        Collection<Currency> currencies = new ArrayList<>();
        currencies.add(currency0);
        currencies.add(currency1);

        Collection<CurrencyDto> dtos = mapper.toDtos(currencies);

        assertEquals(currencies.size(), dtos.size());

        List<Currency> currencyList = new ArrayList<>(currencies);
        List<CurrencyDto> currencyDtoList = new ArrayList<>(dtos);

        for (int i = 0; i < currencies.size(); i++) {
            assertEquals(currencyList.get(i).getRate(), currencyDtoList.get(i).rate);
            assertEquals(currencyList.get(i).getCode(), currencyDtoList.get(i).code);
            assertEquals(currencyList.get(i).getCountry(), currencyDtoList.get(i).country);
        }
    }
}
