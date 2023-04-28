package cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto;

public class CurrencyDto {

    public String code;

    public String country;

    public double rate;

    public CurrencyDto(String code, String country, double rate) {
        this.code = code;
        this.country = country;
        this.rate = rate;
    }
}
