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

    public Box[] npop(int n) {
	Box[] popped = new Box[n];

	for (int i = n - 1; i > -1; --i) {
	    popped[i] = boxes.pop();
	}

	return popped;
    }

    public Box top() {
	return boxes.peek();
    }

    public String toString() {
	if (boxes.isEmpty()) {
	    return "Stack: <empty>";
	} 
	
	StringBuilder dump = new StringBuilder();
	
	dump.append(String.format("Stack[len=%d]: {\n<top>", boxes.size()));
	
	while (boxes.size() > 0) {
	    dump.append("\t").append(boxes.pop().toString()).append("\n");
	}

	return dump.append("}").toString();
    }
}
