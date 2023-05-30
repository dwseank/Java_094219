/** A function that contains two other functions, combined using an arithmetic operator. */
public abstract class TwoFunction extends Function {
    protected final char operator;
    protected final Function function1, function2;

    /**
     * Constructs a TwoFunction.
     *
     * @param operator the operator used to combine the functions: +, -, *, /
     * @param function1 the first function
     * @param function2 the second function
     */
    protected TwoFunction(char operator, Function function1, Function function2) {
        this.operator = operator;
        this.function1 = function1;
        this.function2 = function2;
    }

    @Override
    public double valueAt(double x){
        return operateOn(function1.valueAt(x), function2.valueAt(x));
    }

    @Override
    public String toString() {
        return "(" + function1 + " " + operator + " " + function2 + ")";
    }

    /**
     * Performs an operation on two numbers.
     *
     * @param a first number
     * @param b second number
     * @return result of operation
     */
    protected final double operateOn(double a, double b) {
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
                return 0;
        }
    }
}
