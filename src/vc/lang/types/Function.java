package vc.lang.types;

import java.util.HashMap;

import vc.lang.impl.EvaluationContext;
import vc.lang.impl.deck.ExecutableCard;
import vc.lang.runtime.ExecException;

public class Function extends Token {
    private byte[] name;
    
    public Function(byte[] name) {
	this.name = name;
    }
    
    @Override
    public void eval(EvaluationContext context) throws ExecException {
	ExecutableCard card = context.getDeck().cardByKey(name);
	
	if (card == null) {
	    context.exception("undefined function invocation")
		.details("name: `%s'", name).toss();
	} else {
	    card.execute(context);
	}
    }
    
    @Override
    public boolean sameValue(Token other) {
	return name.equals(((Function) other).getSymbol());
    }

    @Override
    public byte[] getSymbol() {
	return name;
    }

    public String toString() {
	return String.format("Function: `%s'", new String(name));
    }
}

