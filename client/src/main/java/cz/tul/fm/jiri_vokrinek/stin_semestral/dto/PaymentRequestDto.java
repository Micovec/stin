package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

public class PaymentRequestDto {

    public String email;

    public double amount;

    public String currencyCode;

    public PaymentRequestDto(String email, double amount, String currencyCode) {
        this.email = email;
        this.amount = amount;
        this.currencyCode = currencyCode;
    }
}
