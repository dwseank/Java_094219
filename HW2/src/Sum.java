/** A function that is the sum of 2 functions. */
public class Sum extends TwoFunction {
    /**
     * Constructs sum of 2 functions.
     *
     * @param leftFunction the left function
     * @param rightFunction the right function
     */
    public Sum(Function leftFunction, Function rightFunction) {
        super('+', leftFunction, rightFunction);
    }

    @Override
    public Sum derivative() {
        return new Sum(function1.derivative(), function2.derivative());
    }
}
