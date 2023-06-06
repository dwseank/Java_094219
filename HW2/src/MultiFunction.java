/** A function that contains multiple (at least 2) functions, combined using an arithmetic operator. */
public abstract class MultiFunction extends Function {
    protected final Function[] functions;
    private final char operator;

    /**
     * Constructs a MultiFunction.
     *
     * @param operator the arithmetic operator used to combine the functions: +, -, *, /
     * @param firstFunction the first function
     * @param secondFunction the second function
     * @param moreFunctions more functions
     */
    protected MultiFunction(char operator, Function firstFunction, Function secondFunction, Function... moreFunctions) {
        this.operator = operator;
        functions = new Function[moreFunctions.length + 2];
        functions[0] = firstFunction;
        functions[1] = secondFunction;
        for (int i = 0; i < moreFunctions.length; i++) {
            functions[i + 2] = moreFunctions[i];
        }
    }

    @Override
    public final double valueAt(double x) {
        double value = functions[0].valueAt(x);
        for (int i = 1; i < functions.length; i++) {
            value = operateOn(value, functions[i].valueAt(x));
        }
        return value;
    }

    @Override
    public final String toString() {
        String str = functions[0].toString();
        for (int i = 1; i < functions.length; i++) {
            str += " " + operator + " " + functions[i];
        }
        return "(" + str + ")";
    }

    /**
     * Performs an arithmetic operation on two operands.
     *
     * @param a first operand
     * @param b second operand
     * @return result of operation
     */
    private double operateOn(double a, double b) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                return a;
        }
    }
}
