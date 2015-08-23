package vc.lang.types;

public abstract class NumParser {
    /*
     * Public:
     */

    public final static boolean canParse(String symbol) {
	if (symbol == null || !firstCharIsValid(symbol)) {
	    return false;
	}

	boolean foundPoint = false;
	char c;

	for (int i = 1, len = symbol.length(); i < len; ++i) {
	    c = symbol.charAt(i);
		
	    if (c == '.') {
		if (foundPoint) { // 2 or more points are no good
		    return false;
		}
		
		foundPoint = true;
	    } else if(c <= '0' || c >= '9') {
		return false;
	    }
	}

	return true;
    }
    
    public final static Num valueOf(String symbol) {
	return new Num(Double.valueOf(symbol));
    }

    /*
     * Private:
     */

    private static boolean firstCharIsValid(String symbol) {
	char c = symbol.charAt(0);

	return (c >= '0' && c <= '9') || c == '-';
    }
}
