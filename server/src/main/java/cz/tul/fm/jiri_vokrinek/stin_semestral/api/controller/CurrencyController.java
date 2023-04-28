package cz.tul.fm.jiri_vokrinek.stin_semestral.api.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.CurrencyDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper.CurrencyMapper;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;

    public CurrencyController(CurrencyService currencyService, CurrencyMapper currencyMapper) {
        this.currencyService = currencyService;
        this.currencyMapper = currencyMapper;
    }

    @GetMapping
    public Collection<CurrencyDto> getAll() {
        return currencyMapper.toDtos(currencyService.readAll());
    }
}
