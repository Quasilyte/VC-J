package vc.lang.types.num;

import vc.lang.types.*;

public abstract class NumParser {
    public static boolean canParse(String token) {
	return Character.isDigit(token.charAt(0));
    }

    public static Box valueOf(String token) {
	int weight = 0;

	for (int i = 0, len = token.length(); i < len; ++i) {
	    final char c = token.charAt(i);

	    if (c == '.') {
		++weight;
	    } else if (!Character.isDigit(c)) {
		weight += 2;
	    }
	}

	if (weight == 0) {
	    return new IntNum(Integer.valueOf(token));
	} else if (weight == 1) {
	    return new DoubleNum(Double.valueOf(token));
	} else {
	    return null;
	}
    }
}
