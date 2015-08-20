package vc.lang.types;

public abstract class Token<WrappedType> implements Evaluable {
    public WrappedType value;
}
