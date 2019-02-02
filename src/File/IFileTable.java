package File;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IFileTable<K, V> {
    void add(K key, V val);
    void delete(K key);
    V get(K key);
    boolean contains(K key);
    Iterable<K> getAllKeys();
    Collection<V> getAllValues();
    void clear();
    void setMap(Map<K, V> m);
    Map<K, V> getMap();

}