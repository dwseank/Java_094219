/** A function raised to the power of an integer. */
public class Power extends Function {
    private final Function function;
    private final int exponent;

    /**
     * Constructs a power function.
     *
     * @param function the function to raise
     * @param exponent the exponent to raise to
     */
    public Power(Function function, int exponent) {
        this.function = function;
        this.exponent = exponent;
    }

    @Override
    public double valueAt(double x) {
        return Math.pow(function.valueAt(x), exponent);
    }

    @Override
    public String toString() {
        return "(" + function + "^" + exponent + ")";
    }

    @Override
    public Function derivative() {
        if (exponent == 1) {
            return function.derivative();
        }
        return new MultiProduct(new Constant(exponent), new Power(function, exponent - 1), function.derivative());
    }
}
