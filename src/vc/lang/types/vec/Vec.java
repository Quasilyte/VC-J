package vc.lang.types.vec;

import vc.lang.types.*;
import vc.lang.types.num.*;
import vc.lang.types.str.*;

public class Vec extends Box<Box[]> {    
    public Vec(Box[] value) {
	this.value = value;
    }

    public Vec(int size) {
	this.value = new Box[size];
    }

    public Box toNum() throws Exception {
	if (value[0] instanceof Num) {
	    return value[0];
	}

	throw new Exception("error during type assertion");
    }

    public Box toStr() throws Exception {
	if (value[0] instanceof Str) {
	    return value[0];
	}

	throw new Exception("error during type assertion");
    }

    public String toString() {
	return String.format("Vec[len=%d]: [...]", value.length);
    }
}
