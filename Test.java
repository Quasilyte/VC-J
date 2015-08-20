import vc.lang.impl.*;

public class Test {
    public static void main(String[] args) {        
	try {
	    new Interpreter("50 20 7.85 [xx 'string'] 'a b c'").eval();
	} catch (Exception e) {
	    System.out.println("\t<<exception!>>");
	    System.out.println(e.getClass());
	    System.out.println(e.getMessage());
	}
    }
}


