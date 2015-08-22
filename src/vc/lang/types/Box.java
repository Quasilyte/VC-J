package vc.lang.types;

import vc.lang.impl.EvaluationContext;

import vc.lang.types.num.*;
import vc.lang.types.str.*;
import vc.lang.types.vec.*;

public abstract class Box<WrappedType> implements Evaluable {
    public WrappedType value;

    public WrappedType unbox() {
	return value;
    }
    
    protected Box toNum() throws Exception {
	return this;
    }
    
    protected Box toStr() throws Exception {
	return this;
    }
    
    protected Box toVec() {
	return this;
    }

    @Override
    public void evalInsideContext(EvaluationContext context) {
	context.getDataStack().push(this);
    }
}

