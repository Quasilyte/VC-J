package vc.lang.impl;

import vc.lang.runtime.*;
import vc.lang.impl.deck.Deck;

public abstract class EvaluationContext {
    protected Tokenizer tokenizer = new Tokenizer();
    protected DataStack stack = new DataStack();
    protected Deck deck = new Deck(48);
    
    public abstract Tokenizer getTokenizer();
    public abstract DataStack getDataStack();
    public abstract Deck getDeck();

    public abstract ExecExceptionBuilder exception(String msg);
}
