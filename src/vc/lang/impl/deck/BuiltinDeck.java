package vc.lang.impl.deck;

import vc.lang.impl.EvaluationContext;

public class BuiltinDeck extends Deck<ExecutableCard> {
    public BuiltinDeck() {
	allocate(1);
	
	insertCard("xx", this::hello);
    }

    public void hello(EvaluationContext context) {
	System.out.println("wow! it works!");
    }
}
