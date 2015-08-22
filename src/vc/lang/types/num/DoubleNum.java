package vc.lang.types.num;

public class DoubleNum extends Num<Double> {
    public DoubleNum(Double value) {
	this.value = value;
    }

    public String toString() {
	return String.format("Num<Double>: `%f'", value);
    }

    @Override
    protected void wrap(double value) {
	this.value = value;
    }

    @Override
    public double unwrap() {
	return value;
    }
}
