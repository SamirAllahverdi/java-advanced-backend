package org.example.das;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ThreadUnsafeMap<K, V> implements Map<K, V> {

    private final Map<K, V> map = new HashMap<>();

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
        throw new RuntimeException("Not implementedr");
    }

    @Override
    public boolean containsValue(Object value) {
        throw new RuntimeException("Not implementedr");
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        throw new RuntimeException("Not implementedr");
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new RuntimeException("Not implementedr");

    }

    @Override
    public void clear() {
        throw new RuntimeException("Not implementedr");

    }

    @Override
    public Set<K> keySet() {
        throw new RuntimeException("Not implementedr");
    }

    @Override
    public Collection<V> values() {
        throw new RuntimeException("Not implementedr");
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
