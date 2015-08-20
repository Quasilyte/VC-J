package vc.lang.impl;

import vc.lang.runtime.DataStack;

public interface EvaluationContext {
    public Tokenizer getTokenizer();
    public DataStack getDataStack();
}
