package vc.lang.types.num;

public class IntNum extends Num<Integer> {
    public IntNum(Integer value) {
	this.value = value;
    }

    public String toString() {
	return String.format("Num<Integer>: `%d'", value);
    }

    @Override
    protected void wrap(double value) {
	this.value = (int) value;
    }

    @Override
    public double unwrap() {
	return value.doubleValue();
    }
}
