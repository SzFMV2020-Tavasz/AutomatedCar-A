package hu.oe.nik.szfmv.automatedcar.math;

import static java.lang.Math.*;

/**Represents a mathematical 2-dimensional vector with X and Y difference, which also define its length and angle.
 * Can be {@link IVector#rotateByRadians(double) rotated}, {@link IVector#multiplyBy(double) resized},
 * made into {@link IVector#unit() unit vector}, etc...
 *
 * @author Dávid Magyar - davidson996@gmail.com*/
public interface IVector {

    /**Returns the X difference represented by this vector.*/
    double getXDiff();

    /**Returns the Y difference represented by this vector.*/
    double getYDiff();

    /**Gets the length of the vector.*/
    double getLength();

    /**Gets the angle of the vector in radians relative to the given direction.
     * @return Value from -PI rad to +PI rad. Rotation is mathematical, positive is towards the left.*/
    double getRadiansRelativeTo(IVector direction);

    /**Clones this vector, rotates it by the given radians and returns it.
     * Rotation is mathematical, positive is towards the left.
     * @param rad Radians to rotate the vector with. A whole circle is 2{@link Math#PI PI} radians.
     * @see #rotateByDegrees(double)
     * @see #rotateByGradians(double)
     * @return A new vector rotated by the given radians.*/
    default IVector rotateByRadians(double rad) {
        double _yRad = getRadiansRelativeTo(Axis.Y);
        return Axis.Y.positiveDirection().rotateByRadians(_yRad + rad).multiplyBy(getLength());
    }

    /**Gets the angle of the vector in radians relative to given axis.
     * Rotation is mathematical, positive is towards the left.*/
    default double getRadiansRelativeTo(Axis axis) {
        return getRadiansRelativeTo(axis.positiveDirection());
    }

    /**Gets the angle of the vector in degrees relative to given axis.
     * Rotation is mathematical, positive is towards the left.*/
    default double getDegreesRelativeTo(Axis axis) {
        return getRadiansRelativeTo(axis.positiveDirection()) / PI * 180;
    }

    /**Gets the angle of the vector in degrees relative to the given direction.
     * Rotation is mathematical, positive is towards the left.
     * @return Value from -180° to +180°.*/
    default double getDegreesRelativeTo(IVector direction) {
        return getRadiansRelativeTo(direction) / PI * 180;
    }

    /**Gets angle of vector in absolute radians relative to the given axis.
     * Rotation is mathematical, positive is towards the left.
     * @return Value between 0 rad and 2*PI rad.*/
    default double getAbsRadiansRelativeTo(Axis axis) {
        double signedRads = getRadiansRelativeTo(axis);
        return (signedRads < 0)
                ? (2*PI + signedRads)
                : signedRads;
    }

    /**Gets angle of vector in absolute radians relative to the given direction.
     * Rotation is mathematical, positive is towards the left.
     * @return Value between 0 rad and 2*PI rad.*/
    default double getAbsRadiansRelativeTo(IVector direction) {
        double signedRads = getRadiansRelativeTo(direction);
        return (signedRads < 0)
                ? (2*PI + signedRads)
                : signedRads;
    }

    /**Gets angle of vector in absolute degrees relative to the given axis.
     * Rotation is mathematical, positive is towards the left.
     * @return Value between 0° and 360°.*/
    default double getAbsDegreesRelativeTo(Axis axis) {
        return getAbsRadiansRelativeTo(axis.positiveDirection()) / PI * 180;
    }

    /**Gets angle of vector in absolute degrees relative to the given direction.
     * Rotation is mathematical, positive is towards the left.
     * @return Value between 0° and 360°.*/
    default double getAbsDegreesRelativeTo(IVector direction) {
        return getAbsRadiansRelativeTo(direction) / PI * 180;
    }

    /**Creates a new vector with the given X and Y differences, which also define the length and angle of the vector.
     * @see Axis#positiveDirection()
     * @see Axis#negativeDirection() */
    static IVector vectorFromXY(double x, double y) {
        return new IVector() {

            @Override
            public double getXDiff() {
                return x;
            }

            @Override
            public double getYDiff() {
                return y;
            }

            @Override public double getLength() {
                return sqrt(x*x + y*y);
            }

            @Override public double getRadiansRelativeTo(IVector direction) {
                double xRads = direction.getRadiansRelativeTo(Axis.X);
                return inPeriodOfPI(getRadiansRelativeToAxisX() - xRads);
            }

            @Override public double getRadiansRelativeTo(Axis axis) {
                return axis == Axis.X
                        ? getRadiansRelativeToAxisX()
                        : IVector.super.getRadiansRelativeTo(axis);
            }

            private double getRadiansRelativeToAxisX() {
                double x = getXDiff();
                double y = getYDiff();
                return atan2(y, x);
            }
        };
    }

    /**Flips the vector around, keeping its length, but changing its direction to its opposite.
     * @return A new vector with the same length but opposite direction.*/
    default IVector reverse() {
        return vectorFromXY(-getXDiff(), -getYDiff());
    }

    /**Creates the unit vector of this vector.
     * @return A new vector with the same direction, but 1 as length.*/
    default IVector unit() {
        double length = getLength();
        return vectorFromXY(getXDiff() / length, getYDiff() / length);
    }

    /**Multiplies the length of the vector with the given scalar number.
     * @return A new vector with the same direction, but length multiplied by the given scalar.*/
    default IVector multiplyBy(double scalar) {
        return vectorFromXY(getXDiff() * scalar, getYDiff() * scalar);
    }

    /**Divides the length of the vector with the given scalar number.
     * @return A new vector with the same direction, but length divided by the given scalar.*/
    default IVector divideBy(double scalar) {
        return this.multiplyBy(1 / scalar);
    }

    /**Moves the destination of the vector, possibly changing its length and angle in the process
     * @param x A value to add to the X difference of this vector.
     * @param y A value to add to the Y difference of this vector.
     * @return A new vector moved with the given values.*/
    default IVector moveBy(double x, double y) {
        return vectorFromXY(getXDiff() + x, getYDiff() + y);
    }

    /**Clones this vector, rotates it by the given degrees and returns it.
     * Rotation is mathematical, positive is towards the left.
     * @param degrees Degrees to rotate the vector with. A whole circle is 360 degrees.
     * @see #rotateByRadians(double)
     * @see #rotateByGradians(double)
     * @return A new vector rotated by the given degrees.*/
    default IVector rotateByDegrees(double degrees) {
        return rotateByRadians(toRadians(degrees));
    }

    /**Clones this vector, rotates it by the given gradians and returns it.
     * Rotation is mathematical, positive is towards the left.
     * @param grads Gradians to rotate the vector with. A whole circle is 400 gradians.
     * @see #rotateByRadians(double)
     * @see #rotateByDegrees(double)
     * @return A new vector rotated by the given gradians.*/
    default IVector rotateByGradians(double grads) {
        return rotateByRadians(grads / 200 * PI);
    }

    /**Puts the given radian value within the bounds of {@code -}{@link Math#PI PI} to {@code +}{@link Math#PI PI}.
     * @return Value between {@code -}{@link Math#PI PI} and {@code +}{@link Math#PI PI} (both inclusive).*/
    private static double inPeriodOfPI(double radians) {
        radians = radians % (2 * PI);

        while (radians < -PI) {
            radians += 2*PI;
        }

        while (radians > PI) {
            radians -= 2*PI;
        }

        return radians;
    }

}
