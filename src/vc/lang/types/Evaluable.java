package vc.lang.types;

import vc.lang.impl.tokens.Tokenizer;
import vc.lang.runtime.DataStack;

public interface Evaluable {
    public Evaluable eval();
    public void populate(Tokenizer tokenizer, DataStack stack);
}
