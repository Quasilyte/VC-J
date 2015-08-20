package vc.lang.impl.tokens;

import java.util.ArrayDeque;

public class Tokenizer {
    private ArrayDeque<String> parts;
    private String lastPopped;

    public void resetWith(String input) {
	parts = new ArrayDeque<String>(Preprocessor.preprocess(input));
	
	lastPopped = null;
    }

    public void insertTokens(String[] newTokens) {
	for (String newToken : newTokens) {
	    parts.push(newToken);
	}
    }

    public boolean hasTokens() {
	return !parts.isEmpty();
    }
   
    public String nextToken() {
	lastPopped = parts.pop();

	return lastPopped;
    }
    
    public String currentToken() {
	return lastPopped;
    }

    public void dump() {
	System.out.print("dumping: <top ");

	for (String part : parts) {
	    System.out.printf("[%s] ", part);
	}
	
	System.out.println(">");
    }
}
