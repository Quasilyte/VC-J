package vc.lang.types;

import vc.lang.impl.EvaluationContext;

public final class Num extends Box {
    public double value;
    
    private static final double FRACTIONAL_THRESHOLD = 0.000001;
    
    /*
     * Public:
     */

    public Num(double value) {
	this.value = value;
    }
    
    @Override
    public Num toNum(EvaluationContext context) {
	return this;
    }
    
    @Override
    public Str toStr(EvaluationContext context) {
	if (asInt()) {
	    return new Str(String.valueOf((int) value).getBytes());
	} else {
	    return new Str(String.valueOf(value).getBytes());
	}
    }
        
    @Override
    public Vec toVec() {
	return new Vec(new Box[] { this });
    }

    @Override
    public boolean sameValue(Token other) {
	return cmp((Num) other) == 0;
    }

    @Override
    public boolean isTruth() {
	return Double.compare(value, 0.0) != 0;
    }

    public Num add(Num otherNum) {
	return new Num(otherNum.value + value);
    }

    public Num sub(Num otherNum) {
	return new Num(otherNum.value - value);
    }

    public Num mul(Num otherNum) {
	return new Num(otherNum.value * value);
    }

    public Num div(Num otherNum) {
	return new Num(otherNum.value / value);
    }

    public Num gt(Num otherNum) {
	return new Num(cmp(otherNum) > 0 ? -1.0 : 0.0);
    }

    public Num lt(Num otherNum) {
	return new Num(cmp(otherNum) < 0 ? -1.0 : 0.0);
    }

    public String toString() {
	if (asInt()) {
	    return String.format("Num[int]: `%d'", (int) value);
	} else {
	    return String.format("Num[double]: `%f'", value);
	}
    }

    /*
     * Private:
     */

    private boolean asInt() {
	return (value % 1) < FRACTIONAL_THRESHOLD;
    }

    private Num withNewValue(double newValue) {
	value = newValue;

	/*
	if (asInt()) {
	    value = (double) ((int) value);
	}
	*/

	return this;
    }
    
    private int cmp(Num otherNum) {
	return Double.compare(otherNum.value, value);
    }
}

