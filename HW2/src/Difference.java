/** A function that is the difference of 2 functions. */
public class Difference extends TwoFunction {
    /**
     * Constructs difference of 2 functions.
     *
     * @param leftFunction the left function
     * @param rightFunction the right function
     */
    public Difference(Function leftFunction, Function rightFunction) {
        super('-', leftFunction, rightFunction);
    }

    @Override
    public Difference derivative() {
        return new Difference(function1.derivative(), function2.derivative());
    }
}
