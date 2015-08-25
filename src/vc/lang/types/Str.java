package vc.lang.types;

import vc.lang.impl.EvaluationContext;
import vc.lang.impl.Syntax;
import vc.lang.runtime.ExecException;

public class Str extends Box<String> implements Seq {
    public Str(String value) {
	this.value = value;
    }

    @Override
    public Num len() {
	return new Num((double) value.length());
    }

    @Override
    public Token nth(int index) {
	return new Num((double) value.charAt(index));
    }
    
    @Override
    public void set(int index, Box value) {
	// TBA, need to change Str wrapped type to Character[]
    }

    @Override
    public Num toNum(EvaluationContext context) throws ExecException {
        if (!NumParser.canParse(value)) {
	    context.exception("type assert failed")
		.details("STR->NUM can't handle `%s'", value).toss();
	}

	return NumParser.valueOf(value);
    }

    @Override
    public Str toStr(EvaluationContext context) {
	return this;
    }

    @Override
    public Vec toVec() {
	return new Vec(new Box[] { this });
    }

    @Override
    public boolean sameValue(Token other) {
	return ((Str) other).value.equals(value);
    }

    public String toString() {
	return String.format(
	    "Str[len=%d]: `%s'", value.length(),
	    (value.length() > 60) ? value.substring(0, 60) + "..." : value
	);
    }
}
