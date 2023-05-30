/** A polynomial function that consists of coefficients in ascending order. */
public class Polynomial extends Function {
    private final double[] coefficients;

    /**
     * Constructs a polynomial function.
     *
     * @param coefficients polynomial coefficients in ascending order
     */
    public Polynomial(double... coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    public double valueAt(double x) {
        double value = 0;
        for (int i = 0; i < coefficients.length; i++) {
            value += coefficients[i] * Math.pow(x, i);
        }
        return value;
    }

    /**
     * Returns string representation of a polynomial function in a_ix^i format:
     * a_i = coefficient,
     * x = variable,
     * i = exponent.
     *
     * @return polynomial as string
     */
    @Override
    public String toString() {
        String str = "";
        boolean firstTerm = true;
        for (int i = 0; i < coefficients.length; i++) {
            double coefficient = coefficients[i];

            // Skip term if coefficient is 0
            if (coefficient == 0) {
                continue;
            }

            // Operator
            if (!firstTerm) {
                if (coefficient < 0) {
                    str += " - ";
                    coefficient = -coefficient; // Remove sign
                } else {
                    str += " + ";
                }
            }

            // Coefficient
            if (!((coefficient == 1 || coefficient == -1) && i != 0)) { // Skip coefficient if 1 or -1 unless exponent is 0
                // Check if coefficient is an integer
                if (coefficient == (int)coefficient) {
                    str += (int)coefficient;
                } else {
                    str += coefficient;
                }
            }

            // Variable
            if (i != 0) {
                str += "x";
            }

            // Exponent
            if (i > 1) {
                str += "^" + i;
            }

            // Allow operators after first term
            firstTerm = false;
        }

        if (firstTerm) { // All coefficients are 0
            return "(0)";
        }
        return "(" + str + ")";
    }

    @Override
    public Polynomial derivative() {
        if (coefficients.length == 1) {
            return new Polynomial(0);
        }

        double[] derivativeCoefficients = new double[coefficients.length - 1];
        for (int i = 1; i < coefficients.length; i++) {
            derivativeCoefficients[i - 1] = coefficients[i] * i;
        }
        return new Polynomial(derivativeCoefficients);
    }
}
