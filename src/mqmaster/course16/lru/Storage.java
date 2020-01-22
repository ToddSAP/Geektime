package mqmaster.course16.lru;

public interface Storage<K,V>{
    V get(K key);

    void put(K key, V value);

    int size();

    Object[] keys();

    String toString();
}
