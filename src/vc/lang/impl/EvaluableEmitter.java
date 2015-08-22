package vc.lang.impl;

import vc.lang.types.num.NumParser;
import vc.lang.types.num.Num;
import vc.lang.types.str.Str;
import vc.lang.types.Token;
import vc.lang.types.Evaluable;

public class EvaluableEmitter {
    private interface BytePredicate {
	public boolean test(Byte value);
    }
    
    private int lpos = 0, rpos = 0;
    private byte buf[];
    
    public EvaluableEmitter(String input) {
	buf = input.getBytes();
	skipWhile(Character::isWhitespace);
    }

    public boolean canScan() {
	return rpos < buf.length;
    }

    public Evaluable emit() throws Exception {
	Evaluable result = fetchEvaluable();

	skipWhile(Character::isWhitespace);
	
	return result;
    }

    private void skipWhile(BytePredicate proceedPredicate) {
	while (canScan() && proceedPredicate.test(buf[rpos])) {
	    ++rpos;
	}
    }

    private String slice(int from, int len) {
	return new String(buf, from, len);
    }
    
    private Evaluable fetchEvaluable() throws Exception {
	switch (buf[rpos]) {
	case '\'':
	    return fetchQuoted();
	    
	case '[':
	    return new Token(slice(rpos++, 1));
	    
	default:
	    return fetch();
	}
    }

    private Evaluable fetchQuoted() throws Exception {
	try {
	    lpos = ++rpos; // Step over first quote
	   
	    skipWhile((c) -> { return c != '\''; });

	    // One additional increment to bypass enclosing quote
	    return new Str(slice(lpos, (rpos++) - lpos));
	} catch (Exception e) {
	    throw new Exception("unbalanced quote in str literal");
	}
    }
    
    private Evaluable fetch() {
	lpos = rpos;
	skipWhile((c) -> { return !Character.isWhitespace(c); });
	
	if (buf[rpos - 1] == ']') {
	    if ((rpos - lpos) == 1) { // Single bracket -- valid token
		return new Token("]"); 
	    } else {
		--rpos; // We need this bracket to be separate token
	    }
	}
	
	String symbol = slice(lpos, rpos - lpos);

	// Symbol can represent a number
	Num num = NumParser.valueOf(symbol);

	return num == null ? new Token(symbol) : num;
	
    }
}


