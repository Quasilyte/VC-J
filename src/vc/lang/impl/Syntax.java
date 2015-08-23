package vc.lang.impl;

import java.util.regex.Pattern;

public abstract class Syntax {
    public static final Pattern
	condTermination;
    
    public static final int
	MIN_SYMBOL_LEN = 2,
	MAX_SYMBOL_LEN = 21;

    public static final String
	EVAL_KEY = "eval",
	BIND_KEY = "bind",
	IF_KEY = "if",
	ELSE_KEY = "else",
	ENDIF_KEY = "endif",
	STR_CAST_KEY = "str!";

    static {
	condTermination = Pattern.compile(
	    "\\b" + ENDIF_KEY + "\\b|\\b" + ELSE_KEY + "\\b"
	);
    }
}
