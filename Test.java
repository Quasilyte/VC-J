import vc.lang.impl.*;
import vc.lang.runtime.*;

public class Test {
    public static void main(String[] args) throws Exception {
	/*
	String script = "[1 2 3] '#three' bind\n"
	              + "[three three] '#three-2' bind\n"
	              + "three-2";
	*/

	// String script = "'' if 'true' else 'false' endif 'always'";

	// String script = "123 str! 123.7 str!";
	// String script = "'123' num!";
	String script = "1 vec! [1] vec!";
	
	new Interpreter(script).eval();
    }
}


