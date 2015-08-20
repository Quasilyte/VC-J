package vc.lang.impl;

import vc.lang.types.*;
import vc.lang.runtime.*;
import vc.lang.impl.tokens.*;

/**
 * High level Scanner which iterates over token sequence,
 * performing evaluation and accumulates result in data Stack.
 * Most job done by helper classes, so this one can be 
 * represent good high level view of parsing process.
 */
public class Interpreter {
    private Tokenizer tokenizer = new Tokenizer();
    private DataStack stack = new DataStack();
    
    public Interpreter(String input) {
	tokenizer.resetWith(input);
    }
    
    public void eval() throws Exception {
	while (tokenizer.hasTokens()) {
	    evalToken(tokenizer.nextToken());
	}
	
	System.out.println(stack.toString());
    }

    public void executeWord(String token) {
	System.out.printf("executing `%s'\n", token);
    }

    public void evalToken(String token) throws Exception {
	Box box = Box.create(token);

	if (box == null) { // Not a box literal, must be a word to execute
	    executeWord(token);
	} else { // Box scalar; collect it
	    stack.push(box);
	}
    }
}

