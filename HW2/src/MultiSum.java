/** A function that is the result of summing multiple functions. */
public class MultiSum extends MultiFunction {
    /**
     * Constructs MultiSum.
     *
     * @param firstFunction the first function
     * @param secondFunction the second function
     * @param moreFunctions more functions
     */
    public MultiSum(Function firstFunction, Function secondFunction, Function... moreFunctions) {
        super('+', firstFunction, secondFunction, moreFunctions);
    }

    @Override
    public MultiSum derivative() {
        Function[] moreDerivativeFunctions = new Function[functions.length - 2];
        for (int i = 0; i < moreDerivativeFunctions.length; i++) {
            moreDerivativeFunctions[i] = functions[i + 2].derivative();
        }
        return new MultiSum(functions[0].derivative(), functions[1].derivative(), moreDerivativeFunctions);
    }
}
