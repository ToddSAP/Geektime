package mqmaster.course16.lru;


import mqmaster.course16.lru.datastructure.ArrayLinkedList;

public class LruCacheImpl<K,V> extends LruCache<K,V>{
    private final ArrayLinkedList<K,V> cache;

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public Object[] keys() {
        return cache.keys();
    }

    @Override
    public String toString() {
        return cache.toString();
    }

    public LruCacheImpl() {
        super(64);
        cache = new ArrayLinkedList(64,true);
    }

    public LruCacheImpl(int capacity) {
        super(capacity);
        cache = new ArrayLinkedList(capacity, true);
    }

}
