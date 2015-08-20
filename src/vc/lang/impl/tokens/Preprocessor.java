package vc.lang.impl.tokens;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

abstract class Preprocessor {
    private static final HashMap<Character, String> substitutionTable;
    static {
        substitutionTable = new HashMap<Character, String>();

	// To be sure we can see those tokens separately by Scanner
	for (Character token : new Character[] { '[', ']' } ) {
	    substitutionTable.put(token, " " + token + " ");
	}
    }
    
    public static List<String> preprocess(String input) {
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

	// I think it is possible to build appropriate array above,
	// but for now this solution will do.
	return Arrays.asList(output.toString().split("\\s+"));
    }
}
