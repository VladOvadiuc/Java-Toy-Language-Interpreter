package File;

import java.io.BufferedReader;
import java.util.*;

public class FileTable <K,V> implements IFileTable <K,V> {

    private Map<K, V> dict = new HashMap<>();

    @Override
    public void setMap(Map<K,V> m){ this.dict = m;}

    @Override
    public Map<K,V> getMap(){return dict;}

    @Override
    public void add(K key, V val) {
        dict.put(key, val);
    }

    @Override
    public void delete(K key) {
        dict.remove(key);
    }

    @Override
    public V get(K key) {
        return dict.get(key);
    }


    @Override
    public boolean contains(K key) {
        return dict.containsKey(key);
    }

    @Override
    public Iterable<K> getAllKeys() {
        return dict.keySet();
    }

    @Override
    public Collection<V> getAllValues() {
        return dict.values();
    }

    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();
        for(Map.Entry<K, V> e : dict.entrySet()){
            str.append("<"+e.getKey() + ", " + e.getValue() + ">; ");
        }
        return str.toString();
    }

    @Override
    public void clear(){dict.clear();}

}