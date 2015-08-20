package vc.lang.types.num;

import vc.lang.types.*;
import vc.lang.types.str.*;
import vc.lang.types.vec.*;

public abstract class Num<T> extends Box<T> {
    @Override
    public Box toStr() {
	return new Str(String.valueOf(value));
    }
    
    @Override
    public Box toVec() {
	return new Vec(new Box[] { this });
    }
}

