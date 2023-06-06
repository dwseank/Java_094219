/** A function that is the difference of 2 functions. */
public class Difference extends MultiFunction {
    /**
     * Constructs difference of 2 functions.
     *
     * @param firstFunction the first function
     * @param secondFunction the second function
     */
    public Difference(Function firstFunction, Function secondFunction) {
        super('-', firstFunction, secondFunction);
    }

    @Override
    public Difference derivative() {
        return new Difference(functions[0].derivative(), functions[1].derivative());
    }
}
