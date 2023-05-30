/** A function that is the result of multiplying multiple functions. */
public class MultiProduct extends MultiFunction {
    /**
     * Constructs MultiProduct.
     *
     * @param function1 first function
     * @param function2 second function
     * @param functions extra functions
     */
    public MultiProduct(Function function1, Function function2, Function... functions) {
        super('*', function1, function2, functions);
    }

    @Override
    public MultiSum derivative() {
        MultiProduct functionToSum1 = new MultiProduct(function1.derivative(), function2 , functions); // Derivative on first function
        MultiProduct functionToSum2 = new MultiProduct(function2.derivative(), function1 , functions); // Derivative on second function

        Function[] functionsToSum = new Function[functions.length]; // Derivative on extra functions
        for (int i = 0; i < functions.length; i++) {
            Function[] otherFunctions = excludeFunction(i); // Get extra functions without the derivative function
            functionsToSum[i] = new MultiProduct(functions[i].derivative(), function1, otherFunctions); // Add another MultiProduct for the MultiSum
        }
        return new MultiSum(functionToSum1, functionToSum2, functionsToSum); // MultiSum all MultiProducts
    }

    /**
     * Returns all extra functions excluding the function at the specified index.
     * Reserves index 0 for function2.
     *
     * @param index the index of the function to exclude
     * @return array of all other functions
     */
    private Function[] excludeFunction(int index) {
        Function[] otherFunctions = new Function[functions.length];
        otherFunctions[0] = function2;
        for (int i = 0; i < functions.length - 1; i++) {
            if (i < index) {
                otherFunctions[i + 1] = functions[i];
            } else {
                otherFunctions[i + 1] = functions[i + 1];
            }
        }
        return otherFunctions;
    }
}
