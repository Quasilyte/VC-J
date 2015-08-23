import vc.lang.impl.*;

public class Test {
    public static void main(String[] args) throws Exception {
	String script = "[2 1 'xyz'] [2 1 1] =";
	
	new Interpreter(script).eval();
    }
}


