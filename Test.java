import vc.lang.impl.*;
import vc.lang.runtime.*;

public class Test {
    public static void main(String[] args) throws Exception {
	/*
	String script = "[1 2 3] '#three' bind\n"
	              + "[three three] '#three-2' bind\n"
	              + "three-2";
	*/

	String script = "0 if 'true' else 'false' endif 'always'";
	
	new Interpreter(script).eval();
    }
}


