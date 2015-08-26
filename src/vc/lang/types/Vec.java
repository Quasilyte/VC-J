package vc.lang.types;

import java.util.List;

import vc.lang.impl.EvaluationContext;
import vc.lang.runtime.ExecException;
import vc.lang.impl.deck.ExecutableCard;

public class Vec extends Box implements ExecutableCard, Seq {
    public Token[] value;
    
    /*
     * Public:
     */
    
    public Vec(Token[] value) {
	this.value = value;
    }

    public Vec(List<Token> elements) {
	value = new Token[elements.size()];
	value = elements.toArray(value);
    }
    
    @Override
    public Num len() {
	return new Num((double) value.length);
    }

    @Override
    public Token nth(int index) {
	return value[index];
    }

    @Override
    public void set(int index, Box value) {
	this.value[index] = value;
    }

    @Override
    public Num toNum(EvaluationContext context) throws ExecException {
	ensureNotEmpty(context);
	
	if (value[0].getClass() != Num.class) {
	    
	context.exception("type assert failed")
	    .details("VEC->NUM possible only when first element is NUM")
	    .toss();
	}

	return (Num) value[0];
    }

    @Override
    public Str toStr(EvaluationContext context) throws ExecException {
	ensureNotEmpty(context);
	
	if (value[0].getClass() != Str.class) {
	    context.exception("type assert failed")
		.details("VEC->STR possible only when first element is STR")
		.toss();
	}

	return (Str) value[0];
    }

    @Override
    public Vec toVec() {
	return this;
    }

    @Override
    public boolean sameValue(Token other) {
	Token[] otherValue = ((Vec) other).value;
	
	if (value.length != otherValue.length) {
	    return false;
	}
        
	for (int n = 0; n < value.length; ++n) {
	    Token a = value[n], b = otherValue[n];
	    
	    if (!a.sameType(b) || !a.sameValue(b)) {
		return false;
	    }
	}

	return true;
    }

    public String toString() {
	if (value.length == 0) {
	    return "Vec[len=0]: <empty>";
	} 
	
	StringBuilder dump = new StringBuilder();
	dump.append(String.format("Vec[len=%d]: {\n", value.length));

	for (Token token : value) {
	    dump.append("\t\t").append(token.toString()).append("\n");
	}

	return dump.append("\t}").toString();
    }

    @Override
    public void execute(EvaluationContext context)
    throws ExecException {
	context.getTokenizer().insert(value);
    }

    /*
     * Private:
     */

    private void ensureNotEmpty(EvaluationContext context) throws ExecException {
	if (value.length == 0) {
	    context.exception("accessing empty VEC is forbidden").toss();
	}
    }
}
