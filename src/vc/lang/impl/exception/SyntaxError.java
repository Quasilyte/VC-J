package vc.lang.impl.exception;

public class SyntaxError extends Exception {
    public SyntaxError(String expected, String found) {
        super("expected `" + expected + "' token, found `" + found + '\'');
    }
};
