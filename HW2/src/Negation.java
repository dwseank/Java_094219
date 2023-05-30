/** A function that negates the function it contains. */
public class Negation extends Function {
    private final Function function;

    /**
     * Constructs Negation.
     *
     * @param function the function to negate
     */
    public Negation(Function function) {
        this.function = function;
    }

    @Override
    public double valueAt(double x) {
        return -function.valueAt(x);
    }

    @Override
    public String toString() {
        return "(-" + function + ")";
    }

    @Override
    public Negation derivative() {
        return new Negation(function.derivative());
    }
}
