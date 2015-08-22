import vc.lang.impl.*;

public class Test {
    public static void main(String[] args) throws Exception {
	String script = "1 1 = [x]";
	
	new Interpreter(script).eval();
    }
}


