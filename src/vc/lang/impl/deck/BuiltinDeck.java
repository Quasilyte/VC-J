package vc.lang.impl.deck;

import java.util.ArrayList;
    
import vc.lang.impl.*;
import vc.lang.runtime.*;
import vc.lang.types.*;

public class BuiltinDeck extends Deck<ExecutableCard> {
    private interface BinaryOpInvoker {
	public Num invoke(Num b);
    }
    
    public BuiltinDeck() {
	allocate(9);

	insertCard("eval", this::evalToken);
	
	insertCard("[", this::vecCollect);
	
	insertCard("+", this::numAddBinOp);
	insertCard("-", this::numSubBinOp);
	insertCard("*", this::numMulBinOp);
	insertCard("/", this::numDivBinOp);
	
	insertCard("=", this::eqBinOp);
	insertCard(">", this::numGtBinOp);
	insertCard("<", this::numLtBinOp);
    }

    public void evalToken(EvaluationContext context) throws ExecException {
	DataStack stack = context.getDataStack();

	stack.pop().eval(context);
	// context.getTokenizer()
    }

    public void numBinOp(EvaluationContext context, BinaryOpInvoker invoker) {
	DataStack stack = context.getDataStack();
	
	Num operand = (Num) stack.pop();

	stack.push(invoker.invoke(operand));
    }

    public void numAddBinOp(EvaluationContext context) {
	numBinOp(context, ((Num) context.getDataStack().pop())::add);
    }

    public void numSubBinOp(EvaluationContext context) {
	numBinOp(context, ((Num) context.getDataStack().pop())::sub);
    }

    public void numMulBinOp(EvaluationContext context) {
	numBinOp(context, ((Num) context.getDataStack().pop())::mul);
    }

    public void numDivBinOp(EvaluationContext context) {
	numBinOp(context, ((Num) context.getDataStack().pop())::div);
    }

    public void eqBinOp(EvaluationContext context) {
	DataStack stack = context.getDataStack();
	Box a = (Box) stack.pop(), b = (Box) stack.pop();

	if (a.sameType(b)) {
	    stack.push(a.eq(b));
	} else {
	    stack.push(new Num(0.0));
	}	
    }
    
    public void numGtBinOp(EvaluationContext context) {
	numBinOp(context, ((Num) context.getDataStack().pop())::gt);
    }
    
    public void numLtBinOp(EvaluationContext context) {
	numBinOp(context, ((Num) context.getDataStack().pop())::lt);
    }

    public void vecCollect(EvaluationContext context) {
	int depth = 0; // For processing nested vectors
	Tokenizer tokenizer = context.getTokenizer();
	ArrayList<Token> tokens = new ArrayList<>(6);

	while (true) {
	    try {
		Token token = tokenizer.nextToken();
		String symbol = token.getSymbol();
		
		if ("[".equals(symbol)) {
		    ++depth;
		} else if ("]".equals(symbol)) {
		    if (depth == 0) {
			context.getDataStack().push(new Vec(tokens));
			
		        return;
		    } else {
			--depth;
		    }
		}

		tokens.add(token);
	    } catch (Exception e) {}
	}
    }
}
