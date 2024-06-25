package chirg.dev.cache.eviction_policies;

import chirg.dev.cache.exceptions.*;
import chirg.dev.cache.keystore.*;

public class LRUPolicy<KEY, VAL> implements EvictionPolicy<KEY, VAL> {
    private KeyStoreImpl<KEY, VAL> keyStore;

    @Override
    public void setKeyStore(KeyStoreImpl<KEY, VAL> keyStore) {
        this.keyStore = keyStore;
    }
    @Override
    public void keyAccessed(KEY key) throws KeyNotFoundException, CacheOverflowException {
        Bucket<KEY, VAL> buck = keyStore.remove(key);
        if (buck == null) {
            throw new KeyNotFoundException();
        } else {
            keyStore.insert(key, buck.value);
        }
    }

    @Override
    public void evictKey() {
        keyStore.removeFirst();
    }
}