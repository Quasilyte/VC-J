package vc.lang.impl;

import vc.lang.runtime.*;
import vc.lang.impl.deck.BuiltinDeck;

public abstract class EvaluationContext {
    protected Tokenizer tokenizer = new Tokenizer();
    protected DataStack stack = new DataStack();
    protected BuiltinDeck builtins = new BuiltinDeck();
    
    public abstract Tokenizer getTokenizer();
    public abstract DataStack getDataStack();
    public abstract BuiltinDeck getBuiltinDeck();

    public abstract ExecExceptionBuilder exception(String msg);
}
