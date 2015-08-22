package vc.lang.impl;

import java.util.ArrayDeque;

import vc.lang.types.Evaluable;

public class Tokenizer {
    private EvaluableEmitter emitter;
    private Evaluable lastEmitted;

    public void resetWith(String input) {
	emitter = new EvaluableEmitter(input);
	lastEmitted = null;
    }

    /*
    public void insertTokens(Token[] newTokens) {
	for (Token newToken : newTokens) {
	    tokens.push(newToken);
	}
    }
    */

    public boolean hasMore() {
	return emitter.canScan();
    }
   
    public Evaluable nextEvaluable() throws Exception {
	lastEmitted = emitter.emit();

	return lastEmitted;
    }
    
    public Evaluable currentEvaluable() {
	return lastEmitted;
    }
}
