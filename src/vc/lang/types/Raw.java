package vc.lang.types;

import java.util.HashMap;

import vc.lang.impl.EvaluationContext;
import vc.lang.impl.deck.ExecutableCard;

public class Raw extends Token<String> {
    public Raw(String value) {
	this.value = value;
    }
    
    @Override
    public void evalInsideContext(EvaluationContext context) {
	Box box = Box.create(value);

	if (box == null) { // Not a box literal, must be a card to execute
	    ExecutableCard card = context.getBuiltinDeck().getCard(value);
	    
	    if (card == null) {
		System.out.printf("`%s' do not want to populate anything\n", value);
	    } else {
		card.execute(context);
	    }
	} else { // Box scalar; collect it
	    context.getDataStack().push(box);
	}
    }
}
