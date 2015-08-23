package vc.lang.impl.deck;

import java.util.ArrayList;
    
import vc.lang.impl.*;
import vc.lang.runtime.*;
import vc.lang.impl.exception.SyntaxError;
import vc.lang.types.*;
import vc.lang.types.num.*;
import vc.lang.types.str.Str;
import vc.lang.types.vec.Vec;

public class BuiltinDeck extends Deck<ExecutableCard> {
    private interface BinaryOpInvoker {
	public Num invoke(Num b);
    }
    
    public BuiltinDeck() {
	allocate(8);

	insertCard("[", this::vecCollect);
	
	insertCard("+", this::numAddBinOp);
	insertCard("-", this::numSubBinOp);
	insertCard("*", this::numMulBinOp);
	insertCard("/", this::numDivBinOp);
	
	insertCard("=", this::numEqBinOp);
	insertCard(">", this::numGtBinOp);
	insertCard("<", this::numLtBinOp);
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

    public void numEqBinOp(EvaluationContext context) {
	DataStack stack = context.getDataStack();
	Box a = (Box) stack.pop(), b = (Box) stack.pop();

	stack.push(a.eq(b));

	/*
	if (a.getClass() == b.getClass()) {
	    stack.push(a.eq(b));
	} else {
	    stack.push(new IntNum(0));
	}
	*/
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
	ArrayList<Evaluable> elements = new ArrayList<>(6);

	while (true) {
	    try {
		Evaluable element = tokenizer.nextEvaluable();
		
		String symbol = element.toString();
		
		if ("[".equals(symbol)) {
		    ++depth;
		} else if ("]".equals(symbol)) {
		    if (depth == 0) {
			context.getDataStack().push(new Vec(elements));
			return;
		    } else {
			--depth;
		    }
		}

		elements.add(element);
	    } catch (Exception e) {}
	}
    }
}
