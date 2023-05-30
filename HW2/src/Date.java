/** Represents date using day, month and year. */
public class Date {
    private int day, month, year;

    /**
     * Constructs Date.
     * If given values are out of specified range, default values are set.
     *
     * @param year range: [-3999 - 3999], default: 0
     * @param month range: [1 - 12], default: 1
     * @param day range: [1 - 31], default: 1
     */
    public Date(int year, int month, int day) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    /**
     * Checks if two Date objects are equal.
     * Date objects are equal if they represent the same date.
     *
     * @param other Date to compare to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Date)) {
            return false;
        }
        Date otherDate = (Date)other;
        if (this.hashCode() != otherDate.hashCode()) {
            return false;
        }
        return year == otherDate.year && month == otherDate.month && day == otherDate.day;
    }

    /**
     * Returns a unique hash code based on the values stored in Date.
     * <p>Hash is sum of days passed since 01/01/0000 to absolute date.</p>
     * Hash code is negative for negative years.
     *
     * @return unique hash code
     */
    @Override
    public int hashCode() {
        final int daysInMonth = 31;
        final int daysInYear = daysInMonth * 12;

        int hash = day - 1;
        hash += daysInMonth * (month - 1);
        hash += daysInYear * Math.abs(year);

        if (year < 0) {
            return -hash;
        }
        return hash;
    }

    /**
     * Returns date in DD/MM/YYYY format.
     *
     * @return string representation of Date
     */
    @Override
    public String toString() {
        if (year >= 0) {
            return String.format("%02d/%02d/%04d", day, month, year);
        } else {
            return String.format("%02d/%02d/%05d", day, month, year);
        }
    }

    /**
     * Sets the day value. If given value is out of range, defaults to 1.
     *
     * @param day range: [1 - 31], default: 1
     */
    public void setDay(int day) {
        if (day >= 1 && day <= 31) {
            this.day = day;
        } else {
            this.day = 1;
        }
    }

    /**
     * Sets the month value. If given value is out of range, defaults to 1.
     *
     * @param month range: [1 - 12], default: 1
     */
    public void setMonth(int month) {
        if (month >= 1 && month <= 12) {
            this.month = month;
        } else {
            this.month = 1;
        }
    }

    /**
     * Sets the year value. If given value is out of range, defaults to 0.
     *
     * @param year range: [-3999 - 3999], default: 0
     */
    public void setYear(int year) {
        if (year >= -3999 && year <= 3999) {
            this.year = year;
        } else {
            this.year = 0;
        }
    }

    /**
     * Gets the day value.
     *
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets the month value.
     *
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets the year value.
     *
     * @return year
     */
    public int getYear() {
        return year;
    }
}
