package vc.lang.types;

import vc.lang.impl.EvaluationContext;

public interface Evaluable {
    public void evalInsideContext(EvaluationContext context);
    public boolean sameValue(Evaluable x);
}
