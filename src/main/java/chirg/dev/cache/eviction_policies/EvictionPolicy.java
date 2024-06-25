package chirg.dev.cache.eviction_policies;
import chirg.dev.cache.exceptions.*;
import chirg.dev.cache.keystore.KeyStoreImpl;

public interface EvictionPolicy<KEY, VAL> {
    void keyAccessed(KEY key) throws KeyNotFoundException;
    void evictKey();
    void setKeyStore(KeyStoreImpl<KEY, VAL> keyStore);
}
