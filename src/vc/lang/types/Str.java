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
    public void unwrap(EvaluationContext context) throws ExecException {
	if (value.charAt(0) == '#') {
	    context.getTokenizer().insert(new Function(value.substring(1)));
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
}
