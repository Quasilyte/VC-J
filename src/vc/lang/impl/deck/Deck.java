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

    private HashMap<CardIdentifier, ExecutableCard> cards;
        
    public Deck(int capacity) {
	cards = new HashMap<CardIdentifier, ExecutableCard>(capacity);

	cardInsert(Syntax.STACK_SHAKE_KEY, this::stackShake);
	
	cardInsert(Syntax.EVAL_KEY, this::evalToken);
	cardInsert(Syntax.FUNC_REG_KEY, this::funcRegToken);

	cardInsert(Syntax.DO_TRUE_KEY, this::condIf);
	cardInsert(Syntax.DO_FALSE_KEY, this::condElse);
	cardInsert(Syntax.END_DO_KEY, this::condEndIf);

	cardInsert(Syntax.NUM_ASSERT_KEY, this::numAssert);
	cardInsert(Syntax.STR_ASSERT_KEY, this::strAssert);
	cardInsert(Syntax.VEC_ASSERT_KEY, this::vecAssert);

	cardInsert(Syntax.SEQ_LEN_KEY, this::seqLen);
	cardInsert(Syntax.SEQ_NTH_KEY, this::seqNth);
	cardInsert(Syntax.SEQ_QNTH_KEY, this::seqQnth);
	cardInsert(Syntax.SEQ_SET_KEY, this::seqSet);
	cardInsert(Syntax.SEQ_USET_KEY, this::seqUset);
	
	cardInsert(Syntax.VEC_LIT_OPEN_KEY, this::vecCollect);
	
	cardInsert(Syntax.BIN_OP_ADD_KEY, this::numAddBinOp);
	cardInsert(Syntax.BIN_OP_SUB_KEY, this::numSubBinOp);
	cardInsert(Syntax.BIN_OP_MUL_KEY, this::numMulBinOp);
	cardInsert(Syntax.BIN_OP_DIV_KEY, this::numDivBinOp);
	
	cardInsert(Syntax.BIN_OP_EQ_KEY, this::eqBinOp);
	cardInsert(Syntax.BIN_OP_GT_KEY, this::numGtBinOp);
	cardInsert(Syntax.BIN_OP_LT_KEY, this::numLtBinOp);
    }

    public void cardInsert(byte[] key, ExecutableCard card) {
	cards.put(new CardIdentifier(key), card);
    }

    public ExecutableCard cardByKey(byte[] key) {
	return cards.get(new CardIdentifier(key));
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
	cardInsert(
	    context.getTokenizer().nextToken().getSymbol(),
	    context.getDataStack().pop().toVec()
	);
    }

    public void condIf(EvaluationContext context)
    throws ExecException {
	Box top = (Box) context.getDataStack().pop();

	if (!top.isTruth()) {
	    context.getTokenizer().skipUntil(context, Syntax.doTermination);
	}
    }

    public void condElse(EvaluationContext context)
    throws ExecException {
	context.getTokenizer().skipUntil(context, Syntax.doTermination);
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
	    byte[] symbol = token.getSymbol();

	    if (symbol != null && symbol.length == 1 && symbol[0] == ']') {
		return new Vec(tokens);
	    }

	    tokens.add(
		symbol != null && symbol.length == 1 && symbol[0] == '['
		? vecCollectRecur(tokenizer) : token
	    );
	}
    }
}
