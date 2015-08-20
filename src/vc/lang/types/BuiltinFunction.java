package vc.lang.types;

import vc.lang.impl.EvaluationContext;

interface BuiltinFunction {
    public abstract void exec(EvaluationContext context);
}
