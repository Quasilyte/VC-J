package vc.lang.runtime;

import java.util.ArrayDeque;

import vc.lang.types.Box;

public class DataStack {
    private ArrayDeque<Box> boxes;

    public DataStack() {
        boxes = new ArrayDeque<Box>(10);
    }

    public void push(Box box) {
	boxes.push(box);
    }

    public Box pop() {
	return boxes.pop();
    }

    public Box top() {
	return boxes.peek();
    }

    public String toString() {
	StringBuilder dump = new StringBuilder("DataStack dump: {\n");

	dump.append("<top>");
	while (boxes.size() > 0) {
	    dump.append("\t").append(boxes.pop().toString()).append("\n");
	}

	dump.append("}");

	return dump.toString();
    }
}
