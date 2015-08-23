import vc.lang.impl.*;
import vc.lang.runtime.*;

public class Test {
    public static void main(String[] args) throws Exception {
	String script = "[1 [x y 4] ]";
	
	new Interpreter(script).eval();
    }
}


