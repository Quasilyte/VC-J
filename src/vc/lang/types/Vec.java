package vc.lang.types;

import java.util.List;

import vc.lang.impl.EvaluationContext;
import vc.lang.runtime.ExecException;

public class Vec extends Box<Token[]> implements MetaToken {    
    public Vec(Token[] value) {
	this.value = value;
    }

    public Vec(List<Token> elements) {
	value = new Token[elements.size()];
	value = elements.toArray(value);
    }

    @Override
    public Box toNum() throws Exception {
	if (value[0] instanceof Num) {
	    // return value[0];
	    return null;
	}

	throw new Exception("error during type assertion");
    }

    @Override
    public Box toStr() throws Exception {
	if (value[0] instanceof Str) {
	    // return value[0];
	    return null;
	}

	throw new Exception("error during type assertion");
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
    
    @Override
    public void unwrap(EvaluationContext context) throws ExecException {
	context.getTokenizer().insert(value);
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
}
