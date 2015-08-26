import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;

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
	// String script = "1 vec! [1] vec!";

	// String script = "[32 f 'cx'] len";
	// String script = "[0 0 0] 0 'foo' set";

	/*
	InputStream input = new BufferedInputStream(
	    new FileInputStream("test.vcl")
	);

	
	 
	new Interpreter(input).eval();

	input.close();
	*/


	long minTs = Long.MAX_VALUE;
	int n = 0;
	
	for (n = 0; n < 10000; ++n) {
	    long ts = System.nanoTime();
	    // #BEGIN
	    
	    String input = "[1 2] as x 5 as y 5 5 + x y - - + 2 /\n"
		+ "[foo bar baz quox vert]\n"
		+ "0 quoted-nth [] [] 'string!!!' 'another string'";

	    Interpreter vcl = new Interpreter(input);
	    
	    vcl.eval();
	    
	    // #END
	    ts = System.nanoTime() - ts;

	    if (ts < minTs) {
		minTs = ts;
	    }
	}
	
	System.out.printf("runtime: %s\n", minTs);
	
    }
}


