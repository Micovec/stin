package cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.CurrencyDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CurrencyMapper {

    public CurrencyDto toDto(Currency currency) {
        return new CurrencyDto(currency.getCode(), currency.getCountry(), currency.getRate());
    }

    public Collection<CurrencyDto> toDtos(Collection<Currency> currencies) {

        return currencies.stream().map(this::toDto).collect(Collectors.toList());
    }
}
