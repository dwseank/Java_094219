/** A mathematical function with mathematical methods. */
public abstract class Function {
    /**
     * Calculates the function value at given x coordinate.
     *
     * @param x parameter to plug into variable
     * @return function value at given x coordinate
     */
    public abstract double valueAt(double x);

    /**
     * Creates a string representation of the function.
     *
     * @return function as string
     */
    @Override
    public abstract String toString();

    /**
     * Calculates the derivative of the function.
     *
     * @return derivative of function
     */
    public abstract Function derivative();

    /**
     * Finds the root of the function in a given interval [a, b] using the bisection method within the given error term.
     * Assumptions:
     * The function is continuous on the interval [a, b].
     * f(a)*f(b) is negative.
     * The interval [a, b] contains exactly 1 root.
     *
     * @param a left endpoint
     * @param b right endpoint
     * @param epsilon error term
     * @return root of the function in interval
     */
    public final double bisectionMethod(double a, double b, double epsilon) {
        double left = a;
        double right = b;
        while (right - left > epsilon) {
            double mid = (left + right) / 2;
            if (valueAt(left) * valueAt(mid) > 0) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return (left + right) / 2;
    }

    /**
     * Finds the root of the function in a given interval [a, b] using the Bisection method within a 10^-5 error term.
     * Assumptions:
     * The function is continuous on the interval [a, b].
     * f(a)*f(b) is negative.
     * The interval [a, b] contains exactly 1 root.
     *
     * @param a left endpoint
     * @param b right endpoint
     * @return root of the function in interval
     */
    public final double bisectionMethod(double a, double b) {
        return bisectionMethod(a, b, Math.pow(10, -5));
    }

    /**
     * Finds the root of the function in the neighborhood of a given point using the Newton-Raphson method within the given error term.
     * The Newton-Raphson method defines a sequence of points that converges to the root of the function.
     *
     * @param a point to find root around
     * @param epsilon error term
     * @return root of the function in neighborhood of point
     */
    public final double newtonRaphsonMethod(double a, double epsilon) {
        double x = a;
        Function derivative = derivative();
        while (Math.abs(valueAt(x)) >= epsilon) {
            x -= valueAt(x) / derivative.valueAt(x);
        }
        return x;
    }

    /**
     * Finds the root of the function in the neighborhood of a given point using the Newton-Raphson method within a 10^-5 error term.
     * The Newton-Raphson method defines a sequence of points that converges to the root of the function.
     *
     * @param a point to find root around
     * @return root of the function in neighborhood of point
     */
    public final double newtonRaphsonMethod(double a) {
        return newtonRaphsonMethod(a, Math.pow(10, -5));
    }

    /**
     * Calculates the Taylor (Maclaurin) polynomial of a given order for the function.
     *
     * @param n order of Taylor polynomial
     * @return Taylor polynomial of the function
     */
    public final Polynomial taylorPolynomial(int n) {
        double[] coefficients = new double[n + 1];
        double kFactorial = 1;
        Function kDerivative = this;

        coefficients[0] = kDerivative.valueAt(0) / kFactorial; // k = 0
        for (int k = 1; k <= n; k++) {
            kDerivative = kDerivative.derivative();
            kFactorial *= k;
            coefficients[k] = kDerivative.valueAt(0) / kFactorial;
        }
        return new Polynomial(coefficients);
    }
}
