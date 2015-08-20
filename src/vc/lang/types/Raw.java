package vc.lang.types;

import java.util.HashMap;

import vc.lang.impl.EvaluationContext;

public class Raw extends Token<String> {
    private static final HashMap<String, BuiltinFunction> symbolTable;
    static {
        symbolTable = new HashMap<String, BuiltinFunction>();

	symbolTable.put("xx", Raw::hello);
    }

    public Raw(String value) {
	this.value = value;
    }

    public static void hello() {
	System.out.println("yeah, hi!");
    }
    
    @Override
    public void evalInsideContext(EvaluationContext context) {
	Box box = Box.create(value);

	if (box == null) { // Not a box literal, must be a word to execute
	    if (symbolTable.containsKey(value)) {
		symbolTable.get(value).exec(context);
	    } else {
		System.out.printf("`%s' do not want to populate anything\n", value);
	    }
	} else { // Box scalar; collect it
	    context.getDataStack().push(box);
	}
    }
}
