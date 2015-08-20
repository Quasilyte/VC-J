package vc.lang.types;

import vc.lang.types.num.NumParser;
import vc.lang.types.str.StrParser;
import vc.lang.types.vec.VecParser;

abstract class BoxFactory {
    public static Box createBoxFrom(String token) {
	if (NumParser.canParse(token)) {
	    return NumParser.valueOf(token);
	} else if (StrParser.canParse(token)) {
	    return StrParser.valueOf(token);
	} else if (VecParser.canParse(token)) {
	    return VecParser.valueOf(token);
	}

	return null;
    }
}
