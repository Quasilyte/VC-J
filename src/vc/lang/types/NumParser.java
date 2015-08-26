package vc.lang.types;

public abstract class NumParser {
    /*
     * Public:
     */

    public static boolean canParse(byte[] symbol) {
	byte c = symbol[0];
	
	if (symbol == null || charIsNotDigit(c)) {
	    // Maybe it is negative numerical literal?
       	    if (c != '-' || symbol.length == 1) {
		return false;
	    }
	}

	boolean foundPoint = false;

	for (int i = 1; i < symbol.length; ++i) {
	    c = symbol[i];
		
	    if (c == '.') {
		if (foundPoint) { // 2 or more points are no good
		    return false;
		}
		
		foundPoint = true;
	    } else if(charIsNotDigit(c)) {
		return false;
	    }
	}

	return true;
    }
    
    public static Num valueOf(byte[] symbol) {
	return new Num(Double.valueOf(new String(symbol)));
    }

    /*
     * Private:
     */

    private static boolean charIsNotDigit(byte c) {
	return c < '0' || c > '9';
    }
}
