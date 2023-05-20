/** Abstract class meant to provide generic helping methods. */
public abstract class Helper {
    /**
     * Returns the absolute value of a given number.
     *
     * @param x input integer
     * @return x if positive, -x if negative
     */
    public static int absolute(int x) {
        if (x < 0) {
            x *= -1;
        }
        return x;
    }
}