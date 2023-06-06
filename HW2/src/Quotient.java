/** A quotient function made up of a numerator function and a denominator function. */
public class Quotient extends MultiFunction {
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
        Product leftProduct = new Product(functions[0].derivative(), functions[1]);
        Product rightProduct = new Product(functions[1].derivative(), functions[0]);
        Difference numerator = new Difference(leftProduct, rightProduct);
        Power denominator = new Power(functions[1], 2);
        return new Quotient(numerator, denominator);
    }
}
