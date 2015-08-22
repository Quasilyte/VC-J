package vc.lang.types.num;

import vc.lang.types.*;
import vc.lang.types.str.*;
import vc.lang.types.vec.*;

public abstract class Num<WrappedType> extends Box<WrappedType> {
    @Override
    public Box toStr() {
	return new Str(String.valueOf(value));
    }
    
    @Override
    public Box toVec() {
	return new Vec(new Box[] { this });
    }

    public Num add(Num otherNum) {
	this.wrap(otherNum.unwrap() + unwrap());

	return this;
    }

    public Num sub(Num otherNum) {
	this.wrap(otherNum.unwrap() - unwrap());

	return this;
    }

    public Num mul(Num otherNum) {
	this.wrap(otherNum.unwrap() * unwrap());

	return this;
    }

    public Num div(Num otherNum) {
	this.wrap(otherNum.unwrap() / unwrap());

	return this;
    }

    public Num eq(Num otherNum) {
	return new IntNum(cmp(otherNum) == 0 ? -1 : 0);
    }
    
    public Num gt(Num otherNum) {
	return new IntNum(cmp(otherNum) > 0 ? -1 : 0);
    }

    public Num lt(Num otherNum) {
	return new IntNum(cmp(otherNum) < 0 ? -1 : 0);
    }

    private int cmp(Num otherNum) {
	return Double.compare(otherNum.unwrap(), unwrap());
    }

    protected abstract void wrap(double value);
    public abstract double unwrap();
}

