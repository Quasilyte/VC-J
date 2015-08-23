package vc.lang.impl;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayDeque;

import vc.lang.types.Token;
import vc.lang.runtime.ExecException;

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

    public void insert(Token newToken) {
	stock.push(newToken);
    }
    
    public void insert(Token[] newTokens) {
	for (int i = newTokens.length - 1; i > -1; --i) {
	    stock.push(newTokens[i]);
	}
    }
   
    public Token nextToken() throws ExecException {
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

    public void skipUntil(EvaluationContext context, Pattern pattern)
    throws ExecException {
	try {
	    while (true) {
		String symbol = nextToken().getSymbol();
		
		if (symbol != null) {
		    Matcher matcher = pattern.matcher(symbol);

		    if (matcher.matches()) {
			return;
		    }
		}
	    }
	} catch (Exception e) {
	    context.exception("unexpected end of buffer")
		.details("scan pattern: `%s'", pattern)
		.toss();
	}
    }
}
