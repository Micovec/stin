package cz.tul.fm.jiri_vokrinek.stin_semestral.model.supporting;

public record PaymentRequest(String email, String currencyCode, String targetCurrencyCode, double amount) {

}
