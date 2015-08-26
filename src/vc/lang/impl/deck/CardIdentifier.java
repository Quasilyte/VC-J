package vc.lang.impl.deck;

import java.util.Arrays;

final class CardIdentifier {
    public final byte[] symbol;
    
    public CardIdentifier(byte[] symbol) {
	this.symbol = symbol;
    }

    @Override
    public boolean equals(Object other) {
	return Arrays.equals(symbol, ((CardIdentifier) other).symbol);
    }

    @Override
    public int hashCode() {
	return Arrays.hashCode(symbol);
    }
}
