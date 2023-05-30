/** A function that contains multiple functions, combined using an arithmetic operator. */
public abstract class MultiFunction extends TwoFunction {
    protected final Function[] functions;

    /**
     * Constructs a MultiFunction.
     *
     * @param operator the arithmetic operator used to combine the functions: +, -, *, /
     * @param function1 the first function
     * @param function2 the second function
     * @param functions ...more functions
     */
    protected MultiFunction(char operator, Function function1, Function function2, Function... functions) {
        super(operator, function1, function2);
        this.functions = functions;
    }

    @Override
    public final double valueAt(double x) {
        double value = super.valueAt(x); // valueAt for 2 functions
        for (Function function : functions) {
            value = operateOn(value, function.valueAt(x)); // Add valueAt for extra functions
        }
        return value;
    }

    @Override
    public final String toString() {
        String str = super.toString(); // toString for 2 functions
        str = str.substring(1, str.length() - 1); // Remove brackets at ends
        for (Function function : functions) {
            str += " " + operator + " " + function; // Add more functions
        }
        return "(" + str + ")";
    }

    /**
     * Calculates the derivative for each extra function in the multi function.
     * Extra functions are all functions after the first 2 functions.
     *
     * @return derivative of each extra function
     */
    protected final Function[] multiDerivative() {
        Function[] derivativeFunctions = new Function[functions.length];
        for (int i = 0; i < functions.length; i++) {
            derivativeFunctions[i] = functions[i].derivative();
        }
        return derivativeFunctions;
    }
}
