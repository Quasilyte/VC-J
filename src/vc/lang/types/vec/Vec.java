package vc.lang.types.vec;

import java.util.List;

import vc.lang.types.*;
import vc.lang.types.num.*;
import vc.lang.types.str.*;

public class Vec extends Box<Evaluable[]> {    
    public Vec(Evaluable[] value) {
	this.value = value;
    }

    public Vec(List<Evaluable> elements) {
	value = new Evaluable[elements.size()];
	value = elements.toArray(value);
    }

    public Box toNum() throws Exception {
	if (value[0] instanceof Num) {
	    // return value[0];
	    return null;
	}

	throw new Exception("error during type assertion");
    }

    public Box toStr() throws Exception {
	if (value[0] instanceof Str) {
	    // return value[0];
	    return null;
	}

	throw new Exception("error during type assertion");
    }

    public String toString() {
	return String.format("Vec[len=%d]: [...]", value.length);
    }
}
