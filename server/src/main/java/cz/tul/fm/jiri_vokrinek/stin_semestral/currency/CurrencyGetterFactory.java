package cz.tul.fm.jiri_vokrinek.stin_semestral.currency;

import com.beust.jcommander.ParameterException;

public class CurrencyGetterFactory {

    public static ICurrencyGetter getCurrencyGetter(Source source) {
        return switch (source) {
            case CNB -> new CNBCurrencyGetter();
        };
    }

    public enum Source {
        CNB
    }
}
