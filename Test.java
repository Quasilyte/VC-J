import vc.lang.impl.*;
import vc.lang.runtime.*;

public class Test {
    public static void main(String[] args) throws Exception {
	String script = "'#34' eval";
	
	new Interpreter(script).eval();
    }
}


