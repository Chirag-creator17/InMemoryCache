package chirg.dev.cache;

import chirg.dev.cache.eviction_policies.EvictionPolicy;
import chirg.dev.cache.exceptions.*;
import chirg.dev.cache.keystore.KeyStoreImpl;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Cache<KEY, VAL> {
    private EvictionPolicy<KEY, VAL> evictionPolicy;
    private final KeyStoreImpl<KEY, VAL> keyStore;

    private final int capacity;
    private final Semaphore semaphore;
    private final Lock mutex = new ReentrantLock();

    public Cache(int capacity, EvictionPolicy<KEY, VAL> evictionPolicy) {
        this.capacity = capacity;
        this.keyStore = new KeyStoreImpl<>(capacity);
        this.evictionPolicy = evictionPolicy;
        evictionPolicy.setKeyStore(this.keyStore);

        this.semaphore = new Semaphore(1); // Only one thread can access the critical section at a time
    }

    public void changeEvictionPolicy(EvictionPolicy<KEY, VAL> evictionPolicy) throws InvalidEvictionPolicy {
        if (evictionPolicy == null) throw new InvalidEvictionPolicy();

        evictionPolicy.setKeyStore(this.keyStore);
        this.evictionPolicy = evictionPolicy;
    }

    public VAL get(KEY key) {
        try {
            semaphore.acquire();
            try {
                mutex.lock();
                evictionPolicy.keyAccessed(key);

                keyStore.print();
                return keyStore.getVal(key);
            } catch (Exception exc) {
                System.out.println(exc.getMessage());
                return null;
            } finally {
                mutex.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            semaphore.release();
        }
    }

    public void put(KEY key, VAL value) {
        try {
            semaphore.acquire();
            try {
                mutex.lock();
                evictionPolicy.keyAccessed(key);

                keyStore.update(key, value);
                keyStore.print();
            } catch (KeyNotFoundException knf) {
                try {
                    keyStore.insert(key, value);
                    keyStore.print();
                } catch (CacheOverflowException coe) {
                    evictionPolicy.evictKey();
                    keyStore.insert(key, value);
                    keyStore.print();
                }
            } catch (Exception exc) {
                System.out.println(exc.getMessage());
            } finally {
                mutex.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public void clearCache() {
        keyStore.clear();
    }
}
