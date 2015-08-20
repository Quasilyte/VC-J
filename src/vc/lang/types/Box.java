package vc.lang.types;

import vc.lang.types.num.*;
import vc.lang.types.str.*;
import vc.lang.types.vec.*;

public abstract class Box<WrappedType> {
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

    public static Box create(String token) {
	return BoxFactory.createBoxFrom(token);
    }
}

