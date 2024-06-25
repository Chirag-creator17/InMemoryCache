# In-Memory Caching Library

## Overview

This project is an in-memory caching library designed for general use. It supports multiple standard eviction policies and allows for custom eviction policies. The library is thread-safe, ensuring reliable operation in concurrent environments.

## Features

- **Multiple Standard Eviction Policies**: FIFO, LRU, LIFO, and No Eviction.
- **Custom Eviction Policies**: Users can define and add their own eviction policies.
- **Thread Safety**: Ensures safe access and modification in concurrent scenarios.
- **Dynamic Policy Modification**: Eviction policies can be changed at runtime.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher.
- An Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or VS Code.

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Chirag-creator17/InMemoryCache.git
    ```
2. Open the project in your IDE.

## Usage

### Key Classes and Interfaces

- **Cache**: Main class for cache operations.
- **KeyStore**: Generic collection for storing key-value pairs.
- **EvictionPolicy**: Interface for defining eviction policies.
- **NoEvictionPolicy, LRUEvictionPolicy**: Example implementations of `EvictionPolicy`.

### Example Code

Here's how to use the cache with different eviction policies:

```java
public class Main {
    public static void main(String[] args) {
        KeyStore<String, String> keyStore = new KeyStore<>();
        EvictionPolicy<String, String> noEvictionPolicy = new NoEvictionPolicy<>(keyStore, 3);
        Cache<String, String> cache = new Cache<>(3, noEvictionPolicy);
        
        cache.put("1", "One");
        cache.put("2", "Two");
        cache.put("3", "Three");

        // Change to LRU Eviction Policy
        EvictionPolicy<String, String> lruEvictionPolicy = new LRUEvictionPolicy<>(keyStore, 3);
        cache.setEvictionPolicy(lruEvictionPolicy);

        cache.put("4", "Four"); // This should evict the least recently used entry

        System.out.println(cache.get("1")); // Might print null or "One" based on access pattern
        System.out.println(cache.get("2")); // Might print null or "Two"
        System.out.println(cache.get("3")); // Might print null or "Three"
        System.out.println(cache.get("4")); // Should print "Four"

        cache.clearCache(); // Clear the cache

        System.out.println(cache.get("1")); // Should print null
        System.out.println(cache.get("2")); // Should print null
        System.out.println(cache.get("3")); // Should print null
        System.out.println(cache.get("4")); // Should print null
    }
}
```

### API Documentation

#### Cache Class

- **Constructor**: `public Cache(int capacity, EvictionPolicy<K, V> evictionPolicy)`
- **Methods**:
  - `public V get(K key)`
  - `public void put(K key, V value)`
  - `public void clearCache()`
  - `public void setEvictionPolicy(EvictionPolicy<K, V> evictionPolicy)`

#### KeyStore Class

- **Constructor**: `public KeyStore()`
- **Methods**:
  - `public V get(K key)`
  - `public void put(K key, V value)`
  - `public void remove(K key)`
  - `public void clear()`
  - `public Node<K, V> getEvictionCandidate()`

### Custom Eviction Policies

To create a custom eviction policy, implement the `EvictionPolicy` interface:

```java
public class CustomEvictionPolicy<K, V> implements EvictionPolicy<K, V> {
    private final KeyStore<K, V> keyStore;
    private final int capacity;

    public CustomEvictionPolicy(KeyStore<K, V> keyStore, int capacity) {
        this.keyStore = keyStore;
        this.capacity = capacity;
    }

    @Override
    public void onKeyAccess(K key) {
        // Custom logic for key access
    }

    @Override
    public void onPut(K key, V value) {
        // Custom logic for put operation
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
}
```
