package vc.lang.types;

import vc.lang.types.*;

public class Str extends Box<String> {
    public Str(String value) {
	this.value = value;
    }

    @Override
    public Box toNum() throws Exception {
        if (Character.isDigit(value.charAt(0))) {
	    return NumParser.valueOf(value);
	}
        
	throw new Exception("error during type assertion");
    }

    @Override
    public Box toVec() {
	return new Vec(new Box[] { this });
    }

    @Override
    public boolean sameValue(Token other) {
	return ((Str) other).value.equals(value);
    }

    public String toString() {
	return String.format(
	    "Str[len=%d]: `%s'", value.length(),
	    (value.length() > 60) ? value.substring(0, 60) + "..." : value
	);
    }
}
