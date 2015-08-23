package vc.lang.types;

import vc.lang.impl.EvaluationContext;
import vc.lang.runtime.ExecException;

public abstract class Token {
    public abstract void eval(EvaluationContext context) throws ExecException;
    
    public abstract boolean sameValue(Token other);

    public abstract String getSymbol();
    
    public final boolean sameType(Token other) {
	return getClass() == other.getClass();
    }
}
