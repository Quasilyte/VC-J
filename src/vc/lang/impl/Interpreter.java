package vc.lang.impl;

import vc.lang.types.*;
import vc.lang.runtime.*;
import vc.lang.impl.dict.BuiltinDictionary;

/**
 * High level Scanner which iterates over token sequence,
 * performing evaluation and accumulates result in data Stack.
 * Most job done by helper classes, so this one can be 
 * represent good high level view of parsing process.
 */
public class Interpreter implements EvaluationContext {
    private Tokenizer tokenizer = new Tokenizer();
    private DataStack stack = new DataStack();
    private BuiltinDictionary builtins = new BuiltinDictionary();
    
    public Interpreter(String input) {
	tokenizer.resetWith(input);
    }
    
    public void eval() throws Exception {
	while (tokenizer.hasTokens()) {
	    tokenizer.nextToken().evalInsideContext(this);
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
    public BuiltinDictionary getBuiltinDictionary() {
	return builtins;
    }
}

