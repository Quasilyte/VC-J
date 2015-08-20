package vc.lang.types.str;

import vc.lang.types.*;

public abstract class StrParser {
    public static boolean canParse(String token) {
	return token.charAt(0) == '\'';
    }

    public static Box valueOf(String token) {
	char[] cs = new char[token.length() - 2];

	for (int i = 1, len = token.length() - 1; i < len; ++i) {
	    char c = token.charAt(i);

	    cs[i - 1] = (c == '\0') ? ' ' : c;
	}

	return new Str(new String(cs));
    }
}
