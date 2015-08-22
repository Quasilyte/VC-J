package vc.lang.impl;

import vc.lang.types.*;
import vc.lang.runtime.*;
import vc.lang.impl.deck.BuiltinDeck;

public class Interpreter extends EvaluationContext {
    public Interpreter(String input) {
	tokenizer.resetWith(input);
    }
    
    public void eval() throws Exception {
	while (tokenizer.hasMore()) {
	    tokenizer.nextEvaluable().evalInsideContext(this);
	}
	
	System.out.println(stack.toString());
    }
    
    @Override
    public Tokenizer getTokenizer() {
	return tokenizer;
    }

    @Override
    public DataStack getDataStack() {
	return stack;
    }

    @Override
    public BuiltinDeck getBuiltinDeck() {
	return builtins;
    }
}

