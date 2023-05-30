/**
 * A constant function that represents a single value.
 * Constant is a polynomial with only the x^0 term.
 */
public class Constant extends Polynomial {
    /**
     * Constructs a constant function.
     *
     * @param value the value of the constant
     */
    public Constant(double value) {
        super(value);
    }
}
