package com.gt.scr.imdb.common;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class BoundedPriorityMap<K, V> extends AbstractMap<K, V>
        implements java.io.Serializable {
    private static final int MAX_SIZE = 20;
    private final Map<K, V> objects;
    private final TreeSet<WrappedType<K>> uniqueObjects;
    private final int size;
    private Entry<K, V> lastEviction;

    public BoundedPriorityMap(int size) {
        this.size = Math.min(size, MAX_SIZE);

        //Least Recently used is the one whose access time is much further in the future.
        final Comparator<WrappedType<K>> wrappedTypeComparator = (o1, o2) -> {
            if (Objects.equals(o1.key, o2.key)) return 0;
            if (o1.getLastAccessTime() > o2.getLastAccessTime()) {
                return 1;
            } else if (o1.getLastAccessTime() < o2.getLastAccessTime()) {
                return -1;
            }

            return 0;
        };
        objects = new HashMap<>();
        uniqueObjects = new TreeSet<>(wrappedTypeComparator);
    }

    /**
     * Adds an object of type E. If the size exceeds max, then
     * it evicts the LRU object from the set and returns boolean flag - true.
     * If no eviction happens, then it returns false.
     */
    public boolean putWithEviction(K k, V v) {
        boolean isEvicted = false;
        final WrappedType<K> wrappedKey = new WrappedType<>(k);
        if (!uniqueObjects.contains(wrappedKey) && uniqueObjects.size() == size) {
            //Evict LRU element. - Use pollFirst()
            final WrappedType<K> lastEvictedKey = uniqueObjects.pollFirst();
            lastEviction = toEntry(lastEvictedKey.getWrappedKey(), objects.remove(lastEvictedKey.getWrappedKey()));
            isEvicted = true;
        }

        objects.put(k, v);
        uniqueObjects.add(wrappedKey);
        return isEvicted;
    }

    private Entry<K, V> toEntry(K k, V v) {
        return new SimpleImmutableEntry<>(k, v);
    }

    public Entry<K, V> getLastEvictedElement() {
        return Optional.ofNullable(lastEviction)
                .orElse(null);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return objects.entrySet();
    }

    private static class WrappedType<K> {
        private final K key;
        private final long lastAccessTime;

        public WrappedType(K key) {
            this.key = key;
            lastAccessTime = System.nanoTime();
        }

        public K getWrappedKey() {
            return key;
        }

        public long getLastAccessTime() {
            return lastAccessTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WrappedType<?> that = (WrappedType<?>) o;
            return Objects.equals(key, that.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
}
