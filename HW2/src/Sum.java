/** A function that is the sum of 2 functions. */
public class Sum extends MultiSum {
    /**
     * Constructs sum of 2 functions.
     *
     * @param firstFunction the first function
     * @param secondFunction the second function
     */
    public Sum(Function firstFunction, Function secondFunction) {
        super(firstFunction, secondFunction);
    }

    @Override
    public Sum derivative() {
        return new Sum(functions[0].derivative(), functions[1].derivative());
    }
}
