package cz.tul.fm.jiri_vokrinek.stin_semestral.service.exception;

public class EntityStateException extends RuntimeException {

    public EntityStateException() { }

    public <E> EntityStateException(E entity) {
        super("Illegal state of entity " + entity);
    }
}

