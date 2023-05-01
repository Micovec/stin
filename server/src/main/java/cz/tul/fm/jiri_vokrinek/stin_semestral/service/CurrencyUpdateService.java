package cz.tul.fm.jiri_vokrinek.stin_semestral.service;

import cz.tul.fm.jiri_vokrinek.stin_semestral.currency.CurrencyGetterFactory;
import cz.tul.fm.jiri_vokrinek.stin_semestral.currency.ICurrencyGetter;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyUpdateService {

    private final CurrencyService currencyService;
    private final ICurrencyGetter currencyGetter;

    public CurrencyUpdateService(CurrencyService currencyService, ICurrencyGetter currencyGetter) {
        this.currencyService = currencyService;
        this.currencyGetter = currencyGetter;
    }

    @Scheduled(cron = "30 30 14 * * *", zone = "Europe/Prague")
    public void updateCurrencies() {
        for (Currency currency : currencyGetter.getCurrencies()) {
            Currency dbCurrency = currencyService.readById(currency.getCode()).orElseGet(() -> currencyService.create(currency));

            dbCurrency.setCode(currency.getCode());
            dbCurrency.setCountry(currency.getCountry());
            dbCurrency.setRate(currency.getRate());

            currencyService.update(dbCurrency);
        }
    }
}
