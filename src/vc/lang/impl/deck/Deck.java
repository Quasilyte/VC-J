package vc.lang.impl.deck;

import java.util.HashMap;

abstract class Deck<T> {
    private HashMap<String, T> cards;

    public void allocate(int capacity) {
	cards = new HashMap<String, T>(capacity);
    }

    public void insertCard(String key, T card) {
	cards.put(key, card);
    }

    public T getCard(String key) {
	return cards.get(key);
    }
}
