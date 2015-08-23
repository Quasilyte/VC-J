package vc.lang.runtime;

import java.util.Map.Entry;
import java.util.LinkedHashMap;

import vc.lang.impl.EvaluationContext;

public class ExecExceptionBuilder {
    private LinkedHashMap<String, String> parts;
    
    /*
     * Public:
     */

    public ExecExceptionBuilder(String contextInfo, String msg) {
	parts = new LinkedHashMap<>(3);
	
	parts.put("context", contextInfo);
	parts.put("message", msg);
	parts.put("details", null);
    }

    public void toss() throws ExecException {
	throw new ExecException(assembleMessageString());
    }

    public ExecExceptionBuilder details(String fmt, Object ... data) {
	parts.put("details", String.format(fmt, data));

	return this;
    }

    /*
     * Private:
     */

    private String assembleMessageString() {
        String msg = "    { \u001B[31mVC execution error\u001B[0m }\n";

	for (Entry<String, String> part : parts.entrySet()) {
	    if (part.getValue() != null) {
		String label = "\033[1;37m" + part.getKey() + "\u001B[0m";
		
		msg += "[" + label + "] " + part.getValue() + "\n";
	    }
	}

	return msg;
    }
}
