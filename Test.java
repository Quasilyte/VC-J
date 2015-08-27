import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;

import vc.lang.impl.*;
import vc.lang.runtime.*;

/*
['00' 1 shake] as dup
['10' 2 shake] as swap

[dup 10000000 < if 1 + loop] as loop

0 loop endif

a + b = push((Num) pop.v + (Num) pop.v) => 53
 */

public class Test {
    public static void main(String[] args) throws Exception {	
	InputStream input = new BufferedInputStream(
	    new FileInputStream("test.vcl")
	);

	long ts = System.currentTimeMillis() / 100;
	new Interpreter(input).eval();
	System.out.println((System.currentTimeMillis() / 100) - ts);

	input.close();
    }
}


