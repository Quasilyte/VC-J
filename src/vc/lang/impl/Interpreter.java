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
	    collectResult(tokenizer.nextToken().eval());
	}
	
	System.out.println(stack.toString());
    }

    public void collectResult(Evaluable result) {
	if (result != null) {
	    result.populate(tokenizer, stack);
	}
    }
}

