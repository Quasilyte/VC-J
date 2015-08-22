package vc.lang.impl.dict;

import java.util.HashMap;

abstract class Dictionary<T> {
    private HashMap<String, T> entries;

    public void allocate(int capacity) {
	entries = new HashMap<String, T>(capacity);
    }

    public void register(String key, T entry) {
	entries.put(key, entry);
    }

    public T getWord(String key) {
	return entries.get(key);
    }
}
