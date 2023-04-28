package cz.tul.fm.jiri_vokrinek.stin_semestral.data;

import java.time.LocalDate;

public class PaymentDto {

    public double amount;

    public LocalDate date;

    public boolean valid;

    protected PaymentDto() {}

    public PaymentDto(double amount, LocalDate date, boolean valid) {
        this.amount = amount;
        this.date = date;
        this.valid = valid;
    }
}
