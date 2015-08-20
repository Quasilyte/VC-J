package vc.lang.types;

import vc.lang.impl.EvaluationContext;

public class Raw extends Token<String> {
    public Raw(String value) {
	this.value = value;
    }
    
    @Override
    public void evalInsideContext(EvaluationContext context) {
	Box box = Box.create(value);

	if (box == null) { // Not a box literal, must be a word to execute
	    System.out.printf("`%s' do not want to populate anything\n", value);
	} else { // Box scalar; collect it
	    context.getDataStack().push(box);
	}
    }
}
