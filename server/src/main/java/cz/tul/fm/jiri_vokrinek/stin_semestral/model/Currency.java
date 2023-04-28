package cz.tul.fm.jiri_vokrinek.stin_semestral.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "currency")
public class Currency {

    @Id
    private String code;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private double rate;

    protected Currency() {}

    public Currency(String code, String country, double rate) {
        this.code = code;
        this.country = country;
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    public double getRate() {
        return rate;
    }

    public double makeExchange(double amount, Currency destinationCurrency) {
        double currencyRate = rate / destinationCurrency.getRate();
        return amount * currencyRate;
    }
}
