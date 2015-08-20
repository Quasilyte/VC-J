package vc.lang.types.str;

import vc.lang.types.*;
import vc.lang.types.num.*;
import vc.lang.types.vec.*;

public class Str extends Box<String> {
    public Str(String value) {
	this.value = value;
    }

    @Override
    public Box toNum() throws Exception {
        if (NumParser.canParse(value)) {
	    return NumParser.valueOf(value);
	}
        
	throw new Exception("error during type assertion");
    }

    @Override
    public Box toVec() {
	return new Vec(new Box[] { this });
    }

    public String toString() {
	return String.format("Str[len=%d]: `%s'", value.length(), value);
    }
}
