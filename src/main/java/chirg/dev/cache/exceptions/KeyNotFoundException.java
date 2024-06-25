package chirg.dev.cache.exceptions;

public class KeyNotFoundException extends RuntimeException{

    private final static String DEFAULT_MSG = "Key Not found in the cache!!";

    public KeyNotFoundException(){
        super(DEFAULT_MSG);
    }

    public KeyNotFoundException(String message){
        super(message);
    }
}
