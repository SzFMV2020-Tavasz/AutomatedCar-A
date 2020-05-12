package hu.oe.nik.szfmv.automatedcar.math;

import static java.lang.Math.PI;

/**Contains utility functions for mathematical calculations.
 *
 * @author Shared Code (maintainer: Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public enum MathUtils {;

    /**Puts the given radian value within the bounds of {@code -}{@link Math#PI PI} to {@code +}{@link Math#PI PI}.
     * @return Value between {@code -}{@link Math#PI PI} and {@code +}{@link Math#PI PI} (both inclusive).*/
    public static double inPeriodOfPI(double radians) {
        radians = radians % (2 * PI);

        while (radians < -PI) {
            radians += 2*PI;
        }

        while (radians > PI) {
            radians -= 2*PI;
        }

        return radians;
    }

    /**Returns the average of the two given numbers.*/
    public static float mean(float a, float b) {
        return (a + b) / 2;
    }

}
