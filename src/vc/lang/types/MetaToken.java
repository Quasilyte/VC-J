package vc.lang.types;

import vc.lang.impl.EvaluationContext;
import vc.lang.runtime.ExecException;

public interface MetaToken {
    public abstract void unwrap(EvaluationContext context) throws ExecException;
}
