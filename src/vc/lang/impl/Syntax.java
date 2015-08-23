package vc.lang.impl;

import java.util.regex.Pattern;

public abstract class Syntax {
    public static final Pattern condTermination;

    public static final int MIN_SYMBOL_LEN = 2;
    public static final int MAX_SYMBOL_LEN = 2;

    static {
	condTermination = Pattern.compile("\\bendif\\b|\\belse\\b");
    }
}
