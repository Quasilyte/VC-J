package vc.lang.impl.tokens;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;

import vc.lang.types.Token;
import vc.lang.types.Raw;

abstract class Preprocessor {
    private static final HashMap<Character, String> substitutionTable;
    static {
        substitutionTable = new HashMap<Character, String>();

	// To be sure we can see those tokens separately
	for (Character token : new Character[] { '[', ']' } ) {
	    substitutionTable.put(token, " " + token + " ");
	}
    }
    
    public static ArrayDeque<Token> preprocess(String input) {
	boolean insideString = false;
	StringBuilder output = new StringBuilder();

	for (char c : input.toCharArray()) {
	    if (c == '\'') {
		insideString = !insideString;
	    }

	    if (insideString && c == ' ') {
		output.append('\0');
	    } else {
		String substitution = substitutionTable.get(c);

		output.append(substitution == null ? c : substitution);
	    }
	}

	return makeTokens(output.toString());
    }

    /**
     * @todo optimize me! Should assemble resulting deque inside
     * callee for loop. Need precise benchmarks though.
     */
    private static ArrayDeque<Token> makeTokens(String output) {
	String[] parts = output.toString().split("\\s+");
	ArrayDeque<Token> tokens = new ArrayDeque<Token>(parts.length);
	
	for (String part : parts) {
	    tokens.push(new Raw(part));
	}

	return tokens;
    }
}
