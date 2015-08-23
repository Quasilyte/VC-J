package vc.lang.impl;

import java.util.ArrayDeque;

import vc.lang.types.Token;

public class Tokenizer {
    private TokenEmitter emitter;
    private ArrayDeque<Token> stock;
    private Token lastEmitted;

    public void resetWith(String input) {
	emitter = new TokenEmitter(input);
	stock = new ArrayDeque<>(32);
	lastEmitted = null;
    }
    
    public int line() {
	return emitter.line;
    }

    public boolean hasTokens() {
	return !stock.isEmpty() || emitter.canScan();
    }

    public void insertTokens(Token[] newTokens) {
	for (Token newToken : newTokens) {
	    stock.push(newToken);
	}
    }
   
    public Token nextToken() throws Exception {
	if (!stock.isEmpty()) {
	    lastEmitted = stock.pop();
	} else {
	    lastEmitted = emitter.emit();
	}
	
	return lastEmitted;
    }
    
    public Token currentToken() {
	return lastEmitted;
    }
}
