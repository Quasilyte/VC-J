package vc.lang.types;

import vc.lang.impl.EvaluationContext;
import vc.lang.runtime.ExecException;

public abstract class Box<WrappedType> extends Token {
    public WrappedType value;
    
    @Override
    public void eval(EvaluationContext context) throws ExecException {
	context.getDataStack().push(this);
    }

    @Override
    public String getSymbol() {
	return null;
    }
    
    public abstract Box toNum(EvaluationContext context) throws ExecException;
    
    public abstract Box toStr(EvaluationContext context) throws ExecException;
    
    public abstract Box toVec();

    public Box eq(Box other) {
	return new Num(sameValue(other) ? -1.0 : 0.0);
    }

    public boolean isTruth() {
	return true;
    }
}

