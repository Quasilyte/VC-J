package vc.lang.impl;

import vc.lang.runtime.DataStack;
import vc.lang.impl.dict.BuiltinDictionary;

public interface EvaluationContext {
    public Tokenizer getTokenizer();
    public DataStack getDataStack();
    public BuiltinDictionary getBuiltinDictionary();
}
