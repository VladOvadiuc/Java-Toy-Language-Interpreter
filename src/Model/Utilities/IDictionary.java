package Model.Utilities;

import java.util.List;
import java.util.Map;

public interface IDictionary<K,V> {
    void setValue(K key, V value);
    void delete(K key);
    V getValue(K key);
    List<V> getValues();
    boolean exists(K key);
    Iterable<K> getElements();
    IDictionary<K, V> copy();
    void setMap(Map<K, V> m);

    Map<K, V> getMap();
}
