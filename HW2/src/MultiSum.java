/** A function that is the result of summing multiple functions. */
public class MultiSum extends MultiFunction {
    /**
     * Constructs MultiSum.
     *
     * @param function1 first function
     * @param function2 second function
     * @param functions extra functions
     */
    public MultiSum(Function function1, Function function2, Function... functions) {
        super('+', function1, function2, functions);
    }

    @Override
    public MultiSum derivative() {
        return new MultiSum(function1.derivative(), function2.derivative(), multiDerivative());
    }
}
