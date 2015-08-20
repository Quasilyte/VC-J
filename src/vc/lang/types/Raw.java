package vc.lang.types;

import vc.lang.impl.tokens.Tokenizer;
import vc.lang.runtime.DataStack;

public class Raw extends Token<String> {
    public Raw(String value) {
	this.value = value;
    }
    
    @Override
    public Evaluable eval() {
	return this;
    }

    @Override
    public void populate(Tokenizer tokenizer, DataStack stack) {
	// Mockup.
	System.out.printf("`%s' do not want to populate anything\n", value);
    }
}
