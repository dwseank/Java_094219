/** A function that is the product of multiplying 2 functions. */
public class Product extends MultiProduct {
    /**
     * Constructs product of 2 functions.
     *
     * @param firstFunction the first function
     * @param secondFunction the second function
     */
    public Product(Function firstFunction, Function secondFunction) {
        super(firstFunction, secondFunction);
    }

    @Override
    public Sum derivative() {
        Product firstProduct = new Product(functions[0].derivative(), functions[1]);
        Product secondProduct = new Product(functions[1].derivative(), functions[0]);
        return new Sum(firstProduct, secondProduct);
    }
}
