package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

import java.time.LocalDate;

public class PaymentDto {

    public double amount;

    public String currencyCode;

    public String targetCurrencyCode;

    public LocalDate date;

    public boolean valid;

    protected PaymentDto() {}

    public PaymentDto(double amount, String currencyCode, String targetCurrencyCode, LocalDate date, boolean valid) {
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.date = date;
        this.valid = valid;
    }
}
