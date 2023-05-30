/** Represents date and time using day, month, year, hour and minute. */
public class DateTime extends Date {
    private int hour, minute;

    /**
     * Constructs DateTime.
     * If given values are out of specified range, default values are set.
     *
     * @param year range: [-3999 - 3999], default: 0
     * @param month range: [1 - 12], default: 1
     * @param day range: [1 - 31], default: 1
     * @param hour range: [0 - 23], default: 0
     * @param minute range: [0 - 59], default: 0
     */
    public DateTime(int year, int month, int day, int hour, int minute) {
        super(year, month, day);
        setHour(hour);
        setMinute(minute);
    }

    /**
     * Checks if two DateTime objects are equal.
     * DateTime objects are equal if they represent the same date and time.
     *
     * @param other DateTime to compare to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DateTime)) {
            return false;
        }
        DateTime otherDateTime = (DateTime)other;
        if (this.hashCode() != otherDateTime.hashCode()) {
            return false;
        }
        return super.equals(other) && hour == otherDateTime.hour && minute == otherDateTime.minute;
    }

    /**
     * Returns a unique hash code based on the values stored in DateTime.
     * Hash is sum of minutes passed since 01/01/0000 00:00 to absolute date and time
     * + 1.5 million shift to avoid conflicts with superclass.
     * Hash code is negative for negative years.
     *
     * @return unique hash code
     */
    @Override
    public int hashCode() {
        final int minutesInHour = 60;
        final int minutesInDay = minutesInHour * 24;
        final int shift = 1_500_000; // > 31*12*4000

        int superHash = super.hashCode();
        int hash = Math.abs(superHash) * minutesInDay; // Convert days to minutes
        hash += minute;
        hash += minutesInHour * hour;
        hash += shift;

        if (superHash < 0) {
            return -hash;
        }
        return hash;
    }

    /**
     * Returns date and time in DD/MM/YYYY hh:mm format.
     *
     * @return string representation of DateTime
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" %02d:%02d", hour, minute);
    }

    /**
     * Sets the hour value. If given value is out of range, defaults to 0.
     *
     * @param hour range: [0 - 23], default: 0
     */
    public void setHour(int hour) {
        if (hour >= 0 && hour <= 23) {
            this.hour = hour;
        } else {
            this.hour = 0;
        }
    }

    /**
     * Sets the minute value. If given value is out of range, defaults to 0.
     *
     * @param minute range: [0 - 59], default: 0
     */
    public void setMinute(int minute) {
        if (minute >= 0 && minute <= 59) {
            this.minute = minute;
        } else {
            this.minute = 0;
        }
    }

    /**
     * Gets the hour value.
     *
     * @return hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets the minute value.
     *
     * @return minute
     */
    public int getMinute() {
        return minute;
    }
}
