package vc.lang.types;

import vc.lang.impl.tokens.Tokenizer;
import vc.lang.runtime.DataStack;

import vc.lang.types.num.*;
import vc.lang.types.str.*;
import vc.lang.types.vec.*;

public abstract class Box<WrappedType> implements Evaluable {
    public WrappedType value;
    
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
    public Evaluable eval() {
	return this;
    }

    @Override
    public void populate(Tokenizer tokenizer, DataStack stack) {
	stack.push(this);
    }

    public static Box create(String token) {
	return BoxFactory.createBoxFrom(token);
    }
}

