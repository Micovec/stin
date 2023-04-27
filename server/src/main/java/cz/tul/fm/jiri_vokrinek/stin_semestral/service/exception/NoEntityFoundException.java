package cz.tul.fm.jiri_vokrinek.stin_semestral.service.exception;

public class NoEntityFoundException extends RuntimeException {

    public NoEntityFoundException() {
        super("No entity found");
    }
}
