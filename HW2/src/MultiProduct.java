/** A function that is the result of multiplying multiple functions. */
public class MultiProduct extends MultiFunction {
    /**
     * Constructs MultiProduct.
     *
     * @param firstFunction the first function
     * @param secondFunction the second function
     * @param moreFunctions more functions
     */
    public MultiProduct(Function firstFunction, Function secondFunction, Function... moreFunctions) {
        super('*', firstFunction, secondFunction, moreFunctions);
    }

    @Override
    public MultiSum derivative() {
        Function[] otherFunctions = excludeFunction(1); // Excludes functions[0] and functions[1]

        MultiProduct firstFunctionToSum = new MultiProduct(functions[0].derivative(), functions[1], otherFunctions); // Derivative on first function
        MultiProduct secondFunctionToSum = new MultiProduct(functions[1].derivative(), functions[0], otherFunctions); // Derivative on second function

        MultiProduct[] moreFunctionsToSum = new MultiProduct[functions.length - 2];
        for (int i = 2; i < functions.length; i++) {
            otherFunctions = excludeFunction(i); // Excludes functions[0] and functions[i]
            moreFunctionsToSum[i - 2] = new MultiProduct(functions[i].derivative(), functions[0], otherFunctions); // Derivative on more functions
        }
        return new MultiSum(firstFunctionToSum, secondFunctionToSum, moreFunctionsToSum); // MultiSum all MultiProducts
    }

    /**
     * Returns an array of functions without the function at the specified index.
     * Also excludes functions[0].
     * Used as a helper method for derivative().
     *
     * @param index the index of the function to exclude
     * @return array of all other functions
     */
    private Function[] excludeFunction(int index) {
        Function[] otherFunctions = new Function[functions.length - 2];
        for (int i = 0; i < otherFunctions.length; i++) {
            // Checks if current function is before or after the function at the specified index
            if (i < index - 1) {
                otherFunctions[i] = functions[i + 1]; // Before skipping the function
            } else {
                otherFunctions[i] = functions[i + 2]; // After skipping the function
            }
        }
        return otherFunctions;
    }
}
