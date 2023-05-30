/** A quotient function made up of a numerator function and a denominator function. */
public class Quotient extends TwoFunction {
    /**
     * Constructs a quotient function.
     *
     * @param numerator the function in the numerator
     * @param denominator the function in the denominator
     */
    public Quotient(Function numerator, Function denominator) {
        super('/', numerator, denominator);
    }

    @Override
    public Quotient derivative() {
        return new Quotient(new Difference(new Product(function1.derivative(), function2), new Product(function2.derivative(), function1)), new Power(function2, 2));
    }
}
