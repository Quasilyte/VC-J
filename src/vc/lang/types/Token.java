package vc.lang.types;

import java.util.HashMap;

import vc.lang.types.num.NumParser;
import vc.lang.impl.EvaluationContext;
import vc.lang.impl.deck.ExecutableCard;

public class Token implements Evaluable {
    private String symbol;
    
    public Token(String symbol) {
	this.symbol = symbol;
    }
    
    @Override
    public void evalInsideContext(EvaluationContext context) {
	ExecutableCard card = context.getBuiltinDeck().getCard(symbol);
	
	if (card == null) {
	    System.out.printf("`%s' do not want to populate anything\n", symbol);
	} else {
	    card.execute(context);
	}
    }

    public String toString() {
	return symbol;
    }
}
