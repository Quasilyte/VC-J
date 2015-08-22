package vc.lang.types;

import java.util.HashMap;

import vc.lang.impl.EvaluationContext;
import vc.lang.impl.dict.ExecutableWord;

public class Raw extends Token<String> {
    public Raw(String value) {
	this.value = value;
    }
    
    @Override
    public void evalInsideContext(EvaluationContext context) {
	Box box = Box.create(value);

	if (box == null) { // Not a box literal, must be a word to execute
	    ExecutableWord word = context.getBuiltinDictionary().getWord(value);
	    
	    if (word == null) {
		System.out.printf("`%s' do not want to populate anything\n", value);
	    } else {
		word.execute(context);
	    }
	} else { // Box scalar; collect it
	    context.getDataStack().push(box);
	}
    }
}
