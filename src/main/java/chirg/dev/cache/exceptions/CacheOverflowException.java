package chirg.dev.cache.exceptions;

public class CacheOverflowException extends RuntimeException {

    private final static String DEFAULT_MSG = "Cache overflow!!";

    public CacheOverflowException() {
        super(DEFAULT_MSG);
    }

    public CacheOverflowException(String message) {
        super(message);
    }
}
