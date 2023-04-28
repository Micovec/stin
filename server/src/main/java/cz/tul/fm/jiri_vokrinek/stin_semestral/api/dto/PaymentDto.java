package cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentDto {

    public double amount;

    public String currencyCode;

    public LocalDate date;

    public boolean valid;

    public PaymentDto(double amount, String currencyCode, LocalDate date, boolean valid) {
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.date = date;
        this.valid = valid;
    }
}
