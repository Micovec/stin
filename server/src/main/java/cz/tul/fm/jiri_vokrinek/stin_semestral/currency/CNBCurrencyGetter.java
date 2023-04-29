package cz.tul.fm.jiri_vokrinek.stin_semestral.currency;

import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Currency;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;

public class CNBCurrencyGetter implements ICurrencyGetter {

    private static final String URI = "https://www.cnb.cz/cs/financni-trhy/devizovy-trh/kurzy-devizoveho-trhu/kurzy-devizoveho-trhu/denni_kurz.txt";

    @Override
    public Collection<Currency> getCurrencies() {
        Collection<Currency> currencies = new ArrayList<>();

        LocalDate date = LocalDate.now(ZoneId.of("UTC+2"));
        try {
            URL url = new URL(URI +
                    "?date=" +
                    date.getDayOfMonth() + '.' +
                    date.getMonthValue() + '.' +
                    date.getYear());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            reader.readLine(); // Date
            reader.readLine(); // Table header

            while ((line = reader.readLine()) != null) {
                currencies.add(getCurrency(line));
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return currencies;
    }

    private Currency getCurrency(String line) {
        String[] columns = line.split("\\|");
        double rate = Double.parseDouble(columns[4].replace(',', '.')) / Double.parseDouble(columns[2].replace(',', '.'));

        return new Currency(columns[3], columns[0], rate);
    }
}
