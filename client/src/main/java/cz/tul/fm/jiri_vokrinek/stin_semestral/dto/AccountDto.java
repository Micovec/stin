package cz.tul.fm.jiri_vokrinek.stin_semestral.dto;

public class AccountDto {

    public String currencyCode;

    public double balance;

    protected AccountDto() {}

    public AccountDto(String currencyCode, double balance) {
        this.currencyCode = currencyCode;
        this.balance = balance;
    }
}
