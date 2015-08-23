import vc.lang.impl.*;
import vc.lang.runtime.*;

public class Test {
    public static void main(String[] args) throws Exception {
	String script = "[1 2 [1 2] ] eval eval";
	
	new Interpreter(script).eval();
    }
}


