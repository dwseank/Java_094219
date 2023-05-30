/** A function that is the product of multiplying 2 functions. */
public class Product extends TwoFunction {
    /**
     * Constructs product of 2 functions.
     *
     * @param leftFunction the left function
     * @param rightFunction the right function
     */
    public Product(Function leftFunction, Function rightFunction) {
        super('*', leftFunction, rightFunction);
    }

    @Override
    public Sum derivative() {
        return new Sum(new Product(function1.derivative(), function2), new Product(function1, function2.derivative()));
    }
}
