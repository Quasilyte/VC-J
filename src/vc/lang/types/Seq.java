package vc.lang.types;

public interface Seq {
    public Num len();
    public Box nth(int index);
}