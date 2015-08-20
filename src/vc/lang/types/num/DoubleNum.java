package vc.lang.types.num;

public class DoubleNum extends Num<Double> {
    public DoubleNum(Double value) {
	this.value = value;
    }

    public String toString() {
	return String.format("Num<Double>: `%f'", value);
    }
}
