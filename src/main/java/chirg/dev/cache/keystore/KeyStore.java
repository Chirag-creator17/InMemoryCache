package chirg.dev.cache.keystore;

import chirg.dev.cache.exceptions.*;
public interface KeyStore<KEY, VAL> {

    void insert(KEY key, VAL value) throws CacheOverflowException;

    Bucket<KEY,VAL> get(KEY key) throws KeyNotFoundException;
    Bucket<KEY, VAL> remove(KEY key) throws KeyNotFoundException;
}
