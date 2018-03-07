package datastructures.concrete.dictionaries;

import java.util.Iterator;
import java.util.NoSuchElementException;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    public static final int DEFAULT_SIZE = 10;
    private Pair<K, V>[] pairs;
    private int size;
    // You're encouraged to add extra fields (and helper methods) though!

    public ArrayDictionary() {
        pairs = makeArrayOfPairs(DEFAULT_SIZE);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);
    }

    @Override
    public V get(K key) {
        for (int i = 0; i < size; i++) {
            if ((key == null && key == pairs[i].key) || (key != null && key.equals(pairs[i].key))) {
                return pairs[i].value;
            }
        }
        throw new NoSuchKeyException();
    }

    @Override
    public void put(K key, V value) {
        boolean exist = false;
        for (int i = 0; i < size; i++) {
            if ((key == null && key == pairs[i].key) || (key != null && key.equals(pairs[i].key))) {
                pairs[i].value = value;
                exist = true;
            }
        }
        if (!exist) {
            if (size >= pairs.length) {
                Pair<K, V>[] prev = pairs;
                pairs = makeArrayOfPairs(size * 2);
                for (int i = 0; i < size; i++) {
                    pairs[i] = prev[i];
                }
            }
            pairs[size] = new Pair<K, V>(key, value);
            size++;
        }
    }

    @Override
    public V remove(K key) {
        int i = 0;
        while (i < size && ((key == null && key != pairs[i].key) || (key != null && !key.equals(pairs[i].key)))) {
            i++;
        }
        if (i < size) {
            V value = pairs[i].value;
            while (i < size - 1) {
                pairs[i] = pairs[i + 1];
                i++;
            }
            size--;
            return value;
        }
        throw new NoSuchKeyException();
    }

    @Override
    public boolean containsKey(K key) {
        int i = 0;
        while (i < size && ((key == null && key != pairs[i].key) || (key != null && !key.equals(pairs[i].key)))) {
            i++;
        }
        return i < size;
    }

    @Override
    public int size() {
        return size;
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
    
    
    private static class ArrayDictionaryIterator<K, V> implements Iterator<KVPair<K, V>> {
    	
        private int index;   
        private Pair<K, V>[] arr;
        private int size;

        public ArrayDictionaryIterator(Pair<K, V>[] arr, int size) {
            index = 0;
            this.arr = arr;
            this.size = size;
        }       
        
        public boolean hasNext() {
            return index < size;
        }
   
    
        public KVPair<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
        		Pair<K, V> cur = arr[index];
        		index++;
        		return new KVPair<K, V>(cur.key, cur.value);
        }
    }          
    
    public Iterator<KVPair<K, V>> iterator() {
        return new ArrayDictionaryIterator<K, V>(pairs, size);
    }

}
