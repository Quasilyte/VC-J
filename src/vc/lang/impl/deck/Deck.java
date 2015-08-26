package vc.lang.impl.deck;

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
        
    public Deck(int capacity) {
	cards = new HashMap<String, ExecutableCard>(capacity);

	cards.put("shake", this::stackShake);
	
	cards.put(Syntax.EVAL_KEY, this::evalToken);
	cards.put(Syntax.FUNC_REG_KEY, this::funcRegToken);

	cards.put(Syntax.IF_KEY, this::condIf);
	cards.put(Syntax.ELSE_KEY, this::condElse);
	cards.put(Syntax.ENDIF_KEY, this::condEndIf);

	cards.put(Syntax.NUM_ASSERT_KEY, this::numAssert);
	cards.put(Syntax.STR_ASSERT_KEY, this::strAssert);
	cards.put(Syntax.VEC_ASSERT_KEY, this::vecAssert);

	cards.put(Syntax.SEQ_LEN_KEY, this::seqLen);
	cards.put(Syntax.SEQ_NTH_KEY, this::seqNth);
	cards.put("quoted-nth", this::seqQnth);
	cards.put(Syntax.SEQ_SET_KEY, this::seqSet);
	cards.put("unquoted-set", this::seqUset);
	
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

    // n, fmt; take n elems, parse fmt
    public void stackShake(EvaluationContext context)
    throws ExecException {
	DataStack stack = context.getDataStack();

	int n = (int) ((Num) stack.pop()).value;
	// char[] fmt = ((Str) stack.pop()).value.chars();
	/*
	for (char c : fmt) {
	    
	}
	*/
    }

    public void evalToken(EvaluationContext context)
    throws ExecException {
	DataStack stack = context.getDataStack();
	
	if (stack.top().getClass() == Vec.class) {
	    context.getTokenizer().insert(((Vec) stack.pop()).value);
	}
    }

    public void funcRegToken(EvaluationContext context)
    throws ExecException {
	cards.put(
	    new String(context.getTokenizer().nextToken().getSymbol()),
	    context.getDataStack().pop().toVec()
	);
    }

    public void condIf(EvaluationContext context)
    throws ExecException {
	Box top = (Box) context.getDataStack().pop();

	if (!top.isTruth()) {
	    context.getTokenizer().skipUntil(context, Syntax.condTermination);
	}
    }

    public void condElse(EvaluationContext context)
    throws ExecException {
	context.getTokenizer().skipUntil(context, Syntax.condTermination);
    }

    public void condEndIf(EvaluationContext context) {
	// Do nothing!
    }
    
    public void numAssert(EvaluationContext context) throws ExecException {
	DataStack stack = context.getDataStack();
	
	Box top = stack.pop();

	stack.push(top.toNum(context));
    }
    
    public void strAssert(EvaluationContext context) throws ExecException {
	DataStack stack = context.getDataStack();
	
	Box top = stack.pop();

	stack.push(top.toStr(context));
    }

    public void vecAssert(EvaluationContext context) throws ExecException {
	DataStack stack = context.getDataStack();
	
	Box top = stack.pop();

	stack.push(top.toVec());
    }

    public void seqLen(EvaluationContext context) throws ExecException {
	DataStack stack = context.getDataStack();

	stack.push(((Seq) stack.top()).len());
    }

    public void seqNth(EvaluationContext context) throws ExecException {
	DataStack stack = context.getDataStack();
        
	int index = (int) ((Num) stack.pop()).value;

	((Seq) stack.top()).nth(index).eval(context);
    }
    
    public void seqQnth(EvaluationContext context) throws ExecException {
	DataStack stack = context.getDataStack();
        
	int index = (int) ((Num) stack.pop()).value;

	stack.push(
	    new Str(((Function) ((Vec) stack.top()).value[index]).getSymbol())
	);
    }

    public void seqSet(EvaluationContext context) throws ExecException {
	DataStack stack = context.getDataStack();

	Box value = stack.pop();
	int index = (int) ((Num) stack.pop()).value;

	((Seq) stack.top()).set(index, value);
    }
    
    public void seqUset(EvaluationContext context) throws ExecException {
	DataStack stack = context.getDataStack();

	Str symbol = (Str) stack.pop();
	int index = (int) ((Num) stack.pop()).value;

	((Vec) stack.top()).value[index] = new Function(symbol.value);
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
	    byte[] bs = token.getSymbol();
	    
	    String symbol = bs == null ? "" : new String(bs);

	    if ("]".equals(symbol)) {
		return new Vec(tokens);
	    }

	    tokens.add(
		"[".equals(symbol) ? vecCollectRecur(tokenizer) : token
	    );
	    
	}
    }
}
