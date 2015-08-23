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
	allocate(20);

	insertCard("eval", this::evalToken);
	insertCard("bind", this::bindToken);
	
	insertCard("[", this::vecCollect);
	
	insertCard("+", this::numAddBinOp);
	insertCard("-", this::numSubBinOp);
	insertCard("*", this::numMulBinOp);
	insertCard("/", this::numDivBinOp);
	
	insertCard("=", this::eqBinOp);
	insertCard(">", this::numGtBinOp);
	insertCard("<", this::numLtBinOp);
    }

    public void evalToken(EvaluationContext context)
    throws ExecException {
	DataStack stack = context.getDataStack();
	
	if (stack.top() instanceof MetaToken) {
	    ((MetaToken) stack.pop()).unwrap(context);
	}
    }

    public void bindToken(EvaluationContext context)
    throws ExecException {
	DataStack stack = context.getDataStack();
	Str str = (Str) stack.pop();
	
	insertCard(str.makeSymbol(context), (Vec) stack.pop());
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

    public void vecCollect(EvaluationContext context) throws ExecException {
	context.getDataStack().push(vecCollectRecur(context.getTokenizer()));
    }

    public Vec vecCollectRecur(Tokenizer tokenizer) throws ExecException {
	ArrayList<Token> tokens = new ArrayList<>(8);

	while (true) {
	    Token token = tokenizer.nextToken();
	    String symbol = token.getSymbol();

	    if ("]".equals(symbol)) {
		return new Vec(tokens);
	    }

	    tokens.add(
		"[".equals(symbol) ? vecCollectRecur(tokenizer) : token
	    );
	}
    }
}
