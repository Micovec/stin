package cz.tul.fm.jiri_vokrinek.stin_semestral.service.exception;

public class NegativeBalanceException extends RuntimeException {

    public NegativeBalanceException() {
        super("Balance cannot be negative");
    }
}
