package vc.lang.types;

import vc.lang.impl.EvaluationContext;
import vc.lang.impl.Syntax;
import vc.lang.runtime.ExecException;

public class Str extends Box<String> implements MetaToken {
    public Str(String value) {
	this.value = value;
    }

    @Override
    public Box toNum(EvaluationContext context) throws ExecException {
        if (NumParser.canParse(value)) {
	    return NumParser.valueOf(value);
	}

	context.exception("type assert failed")
	    .details("STR->NUM can't handle `%s'", value).toss();
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
	} else if(len > Syntax.MAX_SYMBOL_LEN || len < Syntax.MIN_SYMBOL_LEN) {
	    details = String.format(
		"STR length must be in range of [%d, %d]",
		Syntax.MIN_SYMBOL_LEN,
		Syntax.MAX_SYMBOL_LEN
	    );
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
