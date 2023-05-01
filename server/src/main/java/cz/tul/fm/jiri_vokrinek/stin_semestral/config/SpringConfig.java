package cz.tul.fm.jiri_vokrinek.stin_semestral.config;

import cz.tul.fm.jiri_vokrinek.stin_semestral.currency.CurrencyGetterFactory;
import cz.tul.fm.jiri_vokrinek.stin_semestral.currency.ICurrencyGetter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SpringConfig {

    @Bean
    ICurrencyGetter currencyGetter() {
        return CurrencyGetterFactory.getCurrencyGetter(CurrencyGetterFactory.Source.CNB);
    }
}
