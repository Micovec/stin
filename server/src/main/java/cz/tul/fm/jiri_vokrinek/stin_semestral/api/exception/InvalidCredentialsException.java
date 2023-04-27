package cz.tul.fm.jiri_vokrinek.stin_semestral.api.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException() {
        super("Wrong email or password.");
    }
}
