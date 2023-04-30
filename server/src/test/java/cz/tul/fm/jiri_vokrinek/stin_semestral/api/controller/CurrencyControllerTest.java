package cz.tul.fm.jiri_vokrinek.stin_semestral.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.CurrencyDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper.CurrencyMapper;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.CurrencyService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

    @MockBean
    CurrencyService currencyService;

    @MockBean
    CurrencyMapper currencyMapper;

    @Autowired
    MockMvc mock;

    @Test
    public void testGetAll() throws Exception {
        Currency currency0 = new Currency("CZK", "Česká republika", 1);
        Currency currency1 = new Currency("EUR", "EUM", 20);

        Collection<Currency> currencies = new ArrayList<>();
        currencies.add(currency0);
        currencies.add(currency1);

        Collection<CurrencyDto> currencyDtos = new ArrayList<>();
        currencyDtos.add(new CurrencyDto(currency0.getCode(), currency0.getCountry(), currency0.getRate()));
        currencyDtos.add(new CurrencyDto(currency1.getCode(), currency1.getCountry(), currency1.getRate()));

        Mockito.when(currencyService.readAll()).thenReturn(currencies);
        Mockito.when(currencyMapper.toDtos(currencies)).thenReturn(currencyDtos);

        mock.perform(get("/currency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].code", Matchers.is(currency0.getCode())))
                .andExpect(jsonPath("$[0].country", Matchers.is(currency0.getCountry())))
                .andExpect(jsonPath("$[0].rate", Matchers.is(currency0.getRate())))
                .andExpect(jsonPath("$[1].code", Matchers.is(currency1.getCode())))
                .andExpect(jsonPath("$[1].country", Matchers.is(currency1.getCountry())))
                .andExpect(jsonPath("$[1].rate", Matchers.is(currency1.getRate())));
    }
}
