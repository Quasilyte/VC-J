package vc.lang.impl.dict;

import vc.lang.impl.EvaluationContext;

public class BuiltinDictionary extends Dictionary<ExecutableWord> {
    public BuiltinDictionary() {
	allocate(1);
	
	register("xx", this::hello);
    }

    public void hello(EvaluationContext context) {
	System.out.println("wow! it works!");
    }
}
