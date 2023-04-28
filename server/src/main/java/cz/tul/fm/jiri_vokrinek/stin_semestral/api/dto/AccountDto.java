package cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto;

public class AccountDto {

    public String currencyCode;

    public double balance;

    public AccountDto(String currencyCode, double balance) {
        this.currencyCode = currencyCode;
        this.balance = balance;
    }
}
