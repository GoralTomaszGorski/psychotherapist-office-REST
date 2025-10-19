package pl.goral.psychotherapistofficerest.exception;

/**
 * Rzucany gdy żądany zasób nie istnieje (404).
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}