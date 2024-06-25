package chirg.dev.cache.eviction_policies;

import chirg.dev.cache.exceptions.*;
import chirg.dev.cache.keystore.KeyStoreImpl;

public class LIFOPolicy<KEY, VAL> implements EvictionPolicy<KEY, VAL> {

    private KeyStoreImpl<KEY, VAL> keyStore;

    @Override
    public void setKeyStore(KeyStoreImpl<KEY, VAL> keyStore) {
        this.keyStore = keyStore;
    }

    @Override
    public void keyAccessed(KEY key) throws KeyNotFoundException {
        if (keyStore.get(key) == null) throw new KeyNotFoundException();

        /*no-op*/
    }

    @Override
    public void evictKey() {
        keyStore.removeLast();
    }
}