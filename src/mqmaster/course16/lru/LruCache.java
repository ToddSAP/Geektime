package mqmaster.course16.lru;

public abstract class LruCache<K,V> implements Storage<K,V>{
    protected final int capacity;

    public LruCache (int capacity) {
        this.capacity = capacity;
    }
}
