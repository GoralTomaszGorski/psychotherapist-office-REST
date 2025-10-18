package pl.goral.psychotherapistofficerest.exception;

/**
 * Rzucany w konfliktach (409) np. gdy użytkownik już istnieje.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}