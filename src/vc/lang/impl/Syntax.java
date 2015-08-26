package vc.lang.impl;

import java.util.Arrays;

public abstract class Syntax {
    public static final Tokenizer.TokenMatcher doTermination;
    
    public static final int
	MIN_SYMBOL_LEN = 2,
	MAX_SYMBOL_LEN = 21;

    public static final byte[]
	STACK_SHAKE_KEY = "shake".getBytes(),
	BIN_OP_ADD_KEY = "+".getBytes(),
	BIN_OP_SUB_KEY = "-".getBytes(),
	BIN_OP_MUL_KEY = "*".getBytes(),
	BIN_OP_DIV_KEY = "/".getBytes(),
	BIN_OP_EQ_KEY = "=".getBytes(),
	BIN_OP_GT_KEY = ">".getBytes(),
	BIN_OP_LT_KEY = "<".getBytes(),
	VEC_LIT_OPEN_KEY = "[".getBytes(),
	EVAL_KEY = "eval".getBytes(),
	FUNC_REG_KEY = "as".getBytes(),
	DO_TRUE_KEY = "if".getBytes(),
	DO_FALSE_KEY = "else".getBytes(),
	END_DO_KEY = "endif".getBytes(),
	NUM_ASSERT_KEY = "num!".getBytes(),
	STR_ASSERT_KEY = "str!".getBytes(),
	VEC_ASSERT_KEY = "vec!".getBytes(),
	SEQ_LEN_KEY = "len".getBytes(),
	SEQ_NTH_KEY = "nth".getBytes(),
	SEQ_SET_KEY = "set".getBytes(),
	SEQ_QNTH_KEY = "quoted-nth".getBytes(),
	SEQ_USET_KEY = "unquoted-set".getBytes();

    static {
	doTermination = (token) -> {
	    return Arrays.equals(token, END_DO_KEY) || Arrays.equals(token, DO_FALSE_KEY);
	};
    }
}
