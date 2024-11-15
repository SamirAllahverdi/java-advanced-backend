package org.example.das;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeMap<K, V> implements Map<K, V> {

    private final Map<K, V> map = new HashMap<>();
    private final Lock lock = new ReentrantLock();

    @Override
    public int size() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean containsKey(Object key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean containsValue(Object value) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public V get(Object key) {
        try {
            lock.lock();
            return map.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        try {
            lock.lock();
            return map.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public void clear() {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public Set<K> keySet() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Collection<V> values() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        try {
            lock.lock();
            return new HashMap<>(map).entrySet();
        } finally {
            lock.unlock();
        }
    }
}
