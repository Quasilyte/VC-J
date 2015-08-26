package vc.lang.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;

import vc.lang.types.Token;
import vc.lang.runtime.ExecException;

public class Tokenizer {
    public interface TokenMatcher {
	public boolean matches(byte[] symbol);
    }
    
    private TokenEmitter emitter;
    private ArrayDeque<Token> stock;
    private Token lastEmitted;

    /*
     * Public:
     */
    
    public void resetWith(String input) {
	emitter = new TokenEmitter(input);
	reset();
    }

    public void resetWith(InputStream input) throws IOException {
	byte[] buf = new byte[input.available()];
	input.read(buf);
	
	emitter = new TokenEmitter(new String(buf));
	reset();
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

    public void skipUntil(EvaluationContext context, TokenMatcher pattern)
    throws ExecException {
	try {
	    while (true) {
		byte[] symbol = nextToken().getSymbol();
		
		if (symbol != null) {
		    if (pattern.matches(symbol)) {
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

    /*
     * Private:
     */

    private void reset() {
	stock = new ArrayDeque<>(32);
	lastEmitted = null;
    }
}
