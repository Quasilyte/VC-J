package vc.lang.impl.deck;

import vc.lang.impl.EvaluationContext;
import vc.lang.runtime.ExecException;

public interface ExecutableCard {
    public void execute(EvaluationContext context) throws ExecException;
}
