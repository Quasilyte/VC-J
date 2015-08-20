package vc.lang.runtime;

import java.util.ArrayList;

import vc.lang.types.Box;

public class DataStack {
    private ArrayList<Box> boxes;

    public DataStack() {
        boxes = new ArrayList<Box>(10);
    }

    public void push(Box box) {
	boxes.add(box);
    }

    public String toString() {
	StringBuilder dump = new StringBuilder("DataStack dump: {\n");
	
	for (Box box : boxes) {
	    dump.append("\t").append(box.toString()).append("\n");
	}

	dump.append("}");

	return dump.toString();
    }
}
