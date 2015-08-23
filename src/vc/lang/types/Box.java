package vc.lang.types;

import vc.lang.impl.EvaluationContext;

import vc.lang.types.num.*;
import vc.lang.types.str.*;
import vc.lang.types.vec.*;

public abstract class Box<WrappedType> implements Evaluable {
    public WrappedType value;
    
    public Box toNum() throws Exception {
	return this;
    }
    
    public Box toStr() throws Exception {
	return this;
    }
    
    public Box toVec() {
	return this;
    }

    public Box eq(Box otherBox) {
	return new IntNum(sameValue(otherBox) ? -1 : 0);
    }

    public abstract boolean sameValue(Evaluable x);

    @Override
    public void evalInsideContext(EvaluationContext context) {
	context.getDataStack().push(this);
    }
}

