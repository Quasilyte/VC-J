package vc.lang.types;

import vc.lang.types.*;

public final class Num extends Box<Double> {             
    private static final double FRACTIONAL_THRESHOLD = 0.000001;
    
    /*
     * Public:
     */

    public Num(double value) {
	this.value = value;
    }
    
    @Override
    public Box toStr() {
	return new Str(String.valueOf(value));
    }
    
    @Override
    public Box toVec() {
	return new Vec(new Box[] { this });
    }

    @Override
    public boolean sameValue(Token x) {
	return cmp((Num) x) == 0;
    }

    public Num add(Num otherNum) {
	return withNewValue(otherNum.value + value);
    }

    public Num sub(Num otherNum) {
	return withNewValue(otherNum.value - value);
    }

    public Num mul(Num otherNum) {
	return withNewValue(otherNum.value * value);
    }

    public Num div(Num otherNum) {
	return withNewValue(otherNum.value / value);
    }

    public Num gt(Num otherNum) {
	return new Num(cmp(otherNum) > 0 ? -1.0 : 0.0);
    }

    public Num lt(Num otherNum) {
	return new Num(cmp(otherNum) < 0 ? -1.0 : 0.0);
    }

    public String toString() {
	boolean asInt = (value % 1) < FRACTIONAL_THRESHOLD;

	if (asInt) {
	    return String.format("Num[int]: `%d'", value.intValue());
	} else {
	    return String.format("Num[double]: `%f'", value);
	}
    }

    /*
     * Private:
     */

    private Num withNewValue(double newValue) {
	value = newValue;

	return this;
    }
    
    private int cmp(Num otherNum) {
	return Double.compare(otherNum.value, value);
    }
}

