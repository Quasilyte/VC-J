package vc.lang.types.num;

import vc.lang.types.*;

public abstract class NumParser {
    public static Num valueOf(String symbol) {
	int weight = 0;

	for (int i = 0, len = symbol.length(); i < len; ++i) {
	    final char c = symbol.charAt(i);

	    if (c == '.') {
		++weight;
	    } else if (!Character.isDigit(c)) {
		weight += 2;
	    }
	}

	if (weight == 0) {
	    return new IntNum(Integer.valueOf(symbol));
	} else if (weight == 1) {
	    return new DoubleNum(Double.valueOf(symbol));
	} else {
	    return null;
	}
    }
}
