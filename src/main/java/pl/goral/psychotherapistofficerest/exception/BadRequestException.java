package pl.goral.psychotherapistofficerest.exception;

/**
 * Rzucany gdy żądanie jest niepoprawne (400).
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}