package vc.lang.impl.deck;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;

import vc.lang.impl.*;
import vc.lang.runtime.*;
import vc.lang.types.*;

public class Deck {
    private interface BinaryOpInvoker {
	public Num invoke(Num b);
    }

    private HashMap<String, ExecutableCard> cards;
    
    private Pattern condTermination;
    
    public Deck(int capacity) {
	cards = new HashMap<String, ExecutableCard>(capacity);

	condTermination = Pattern.compile("\\bendif\\b|\\belse\\b");
	
	cards.put("eval", this::evalToken);
	cards.put("bind", this::bindToken);

	cards.put("if", this::condIf);
	cards.put("else", this::condElse);
	cards.put("endif", this::condEndIf);
	
	cards.put("[", this::vecCollect);
	
	cards.put("+", this::numAddBinOp);
	cards.put("-", this::numSubBinOp);
	cards.put("*", this::numMulBinOp);
	cards.put("/", this::numDivBinOp);
	
	cards.put("=", this::eqBinOp);
	cards.put(">", this::numGtBinOp);
	cards.put("<", this::numLtBinOp);
    }

    public ExecutableCard cardByKey(String key) {
	return cards.get(key);
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
	
	cards.put(str.makeSymbol(context), (Vec) stack.pop());
    }

    public void condIf(EvaluationContext context)
    throws ExecException {
	Box top = (Box) context.getDataStack().pop();

	if (!top.isTruth()) {
	    context.getTokenizer().skipUntil(context, condTermination);
	}
    }

    public void condElse(EvaluationContext context)
    throws ExecException {
	context.getTokenizer().skipUntil(context, condTermination);
    }

    public void condEndIf(EvaluationContext context) {
	// Do nothing!
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
