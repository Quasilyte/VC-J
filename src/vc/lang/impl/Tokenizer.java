package vc.lang.impl;

import java.util.ArrayDeque;

import vc.lang.types.Token;

class Tokenizer {
    private ArrayDeque<Token> tokens;
    private Token lastPopped;

    public void resetWith(String input) {
	tokens = new ArrayDeque<Token>(Preprocessor.preprocess(input));
	
	lastPopped = null;
    }

    public void insertTokens(Token[] newTokens) {
	for (Token newToken : newTokens) {
	    tokens.push(newToken);
	}
    }

    public boolean hasTokens() {
	return !tokens.isEmpty();
    }
   
    public Token nextToken() {
	lastPopped = tokens.pop();

	return lastPopped;
    }
    
    public Token currentToken() {
	return lastPopped;
    }

    public void dump() {
	System.out.print("dumping: <top ");

	for (Token token : tokens) {
	    System.out.printf("[%s] ", token);
	}
	
	System.out.println(">");
    }
}
