package vc.lang.types;

import vc.lang.impl.EvaluationContext;
import vc.lang.runtime.ExecException;

public class Str extends Box<String> implements MetaToken {
    public Str(String value) {
	this.value = value;
    }

    @Override
    public Box toNum() throws Exception {
        if (Character.isDigit(value.charAt(0))) {
	    return NumParser.valueOf(value);
	}
        
	throw new Exception("error during type assertion");
    }

    @Override
    public Box toVec() {
	return new Vec(new Box[] { this });
    }

    @Override
    public boolean sameValue(Token other) {
	return ((Str) other).value.equals(value);
    }

    @Override
    public void unwrap(EvaluationContext context)
    throws ExecException {
	if (value.charAt(0) == '#') {
	    context.getTokenizer().insert(new Function(makeSymbol(context)));
	} else {
	    eval(context);
	}
    }

    public String toString() {
	return String.format(
	    "Str[len=%d]: `%s'", value.length(),
	    (value.length() > 60) ? value.substring(0, 60) + "..." : value
	);
    }

    public String makeSymbol(EvaluationContext context)
    throws ExecException {
	String details = null;
	char c = value.charAt(0);
	int len = value.length();

	if (c != '#') {
	    details = "not found `#' at the begining of STR";
	} else if(len > 21 || len < 2) {
	    details = "STR length must be in range of [2, 21]";
	} else {
	    c = value.charAt(1);
	    if((c >= '0' && c <= '9') || c == '-' || c == ' ') {
		details = "first char after `#' is invalid";
	    }
	    
	    for (int i = 2; i < len; ++i) {
		c = value.charAt(i);

		if (c == ' ') {
		    details = "spaces in value are forbidden";
		}
	    }
	}
	
	if (details != null) {
	    context.exception("treated STR as symbol, but it's malformed")
		.details("given: `%s'; " + details, value).toss();
	}

	return value.substring(1);
    }
}
