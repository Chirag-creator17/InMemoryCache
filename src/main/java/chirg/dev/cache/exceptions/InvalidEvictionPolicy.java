package chirg.dev.cache.exceptions;

public class InvalidEvictionPolicy extends Exception {

    private final static String DEFAULT_MSG = "Invalid Eviction Policy!!";

    public InvalidEvictionPolicy() {
        super(DEFAULT_MSG);
    }

    public InvalidEvictionPolicy(String message) {
        super(message);
    }
}
