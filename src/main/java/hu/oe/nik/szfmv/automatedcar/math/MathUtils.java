package hu.oe.nik.szfmv.automatedcar.math;

import static java.lang.Math.PI;

/**Contains utility functions for mathematical calculations.
 *
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public enum MathUtils {;

    public static final double GRADIAN_PERIOD = 400;
    public static final double DEGREE_PERIOD = 360;
    public static final double RADIAN_PERIOD = PI * 2;

    /**Puts the given radian value within the bounds of {@code -}{@link Math#PI PI} to {@code +}{@link Math#PI PI}.
     * @return Value between {@code -}{@link Math#PI PI} and {@code +}{@link Math#PI PI} (both inclusive).*/
    public static double inPeriodOfPI(double radians) {
        radians = radians % RADIAN_PERIOD;

        while (radians < -PI) {
            radians += RADIAN_PERIOD;
        }

        while (radians > PI) {
            radians -= RADIAN_PERIOD;
        }

        return radians;
    }

}
