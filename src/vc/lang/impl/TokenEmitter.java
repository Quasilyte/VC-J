package vc.lang.impl;

import vc.lang.types.*;
import vc.lang.runtime.ExecException;

public class TokenEmitter {
    private interface BytePredicate {
	public boolean test(Byte value);
    }

    public int line = 1;
    
    private int lpos = 0, rpos = 0;
    private byte buf[];
    
    public TokenEmitter(String input) {
	buf = input.getBytes();
	skipWhile(Character::isWhitespace);
    }

    public boolean canScan() {
	return rpos < buf.length;
    }

    public Token emit() throws ExecException {
	Token token = fetchToken();

	skipWhile(Character::isWhitespace);
	
	return token;
    }

    private void skipWhile(BytePredicate proceedPredicate) {
	while (canScan() && proceedPredicate.test(buf[rpos])) {
	    if (buf[rpos++] == '\n') {
		++line;
	    }
	}
    }

    private String slice(int from, int len) {
	return new String(buf, from, len);
    }
    
    private Token fetchToken() throws ExecException {
	switch (buf[rpos]) {
	case '\'':
	    return fetchQuoted();
	    
	case '[':
	    return new Function(slice(rpos++, 1));
	    
	default:
	    return fetch();
	}
    }

    private Token fetchQuoted() throws ExecException {
	try {
	    lpos = ++rpos; // Step over first quote
	   
	    skipWhile((c) -> { return c != '\''; });

	    // One additional increment to bypass enclosing quote
	    return new Str(slice(lpos, (rpos++) - lpos));
	} catch (Exception e) {	    
	    throw new ExecException("unbalanced quote in str literal");
	}
    }
    
    private Token fetch() {
	lpos = rpos;
	skipWhile((c) -> { return !Character.isWhitespace(c); });
	
	if (buf[rpos - 1] == ']') {
	    if ((rpos - lpos) == 1) { // Single bracket -- valid token
		return new Function("]"); 
	    } else {
		--rpos; // We need this bracket to be separate token
	    }
	}
	
	String symbol = slice(lpos, rpos - lpos);

	if (NumParser.canParse(symbol)) {
	    return NumParser.valueOf(symbol);
	} else {
	    return new Function(symbol);
	}
    }
}


