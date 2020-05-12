package hu.oe.nik.szfmv.automatedcar.math;

import static hu.oe.nik.szfmv.automatedcar.math.Axis.baseDirection;
import static hu.oe.nik.szfmv.automatedcar.math.MathUtils.*;
import static java.lang.Double.*;
import static java.lang.Math.*;

/**Represents a mathematical 2-dimensional vector with X and Y difference, which also define its length and angle.
 * Can be {@link IVector#rotateByRadians(double) rotated}, {@link IVector#multiplyBy(double) resized},
 * made into {@link IVector#unit() unit vector}, etc...
 *
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public interface IVector {

    /**Returns the X difference represented by this vector.*/
    double getXDiff();

    /**Returns the Y difference represented by this vector.*/
    double getYDiff();

    /**Gets the length of the vector.*/
    double getLength();

    /**Gets the angle of the vector in radians relative to the given direction.
     * @return Value from {@link Math#PI -PI} rad to {@link Math#PI +PI} rad. Rotation is mathematical, positive is towards the left.*/
    double getRadiansRelativeTo(IVector direction);

    default boolean hasLength() {
        double x = getXDiff();
        double y = getYDiff();
        return !isNaN(x) && !isNaN(y);
    }

    default boolean isDirectional() {
        double x = getXDiff();
        double y = getYDiff();
        return (x != 0 || y != 0) && !isNaN(x) && !isNaN(y);
    }

    /**Clones this vector, rotates it by the given radians and returns it.
     * Rotation is mathematical, positive is towards the left.
     * @param rad Radians to rotate the vector with.
     *            <p>A whole circle is 2{@link Math#PI PI} radians (see {@link MathUtils#RADIAN_PERIOD radian period}).</p>
     * @see #rotateByDegrees(double)
     * @see #rotateByGradians(double)
     * @return A new vector rotated by the given radians.*/
    default IVector rotateByRadians(double rad) {
        double _yRad = getRadiansRelativeTo(Axis.Y);
        if (isFinite(_yRad)) {
            return Axis.Y.positiveDirection().rotateByRadians(_yRad + rad).multiplyBy(getLength());
        } else {
            return this;
        }
    }

    /**Gets the angle of the vector in radians relative to the {@link Axis#BASE default axis}.
     * <p>Rotation is mathematical, positive is towards the left.</p>*/
    default double getRadians() {
        return getRadiansRelativeTo(Axis.BASE);
    }

    /**Gets the angle of the vector in degrees relative to the {@link Axis#BASE default axis}.
     * <p>Rotation is mathematical, positive is towards the left.</p>*/
    default double getDegrees() {
        return toDegrees(getRadians());
    }

    /**Gets the angle of the vector in radians relative to given axis.
     * <p>Rotation is mathematical, positive is towards the left.</p>*/
    default double getRadiansRelativeTo(Axis axis) {
        return getRadiansRelativeTo(axis.positiveDirection());
    }

    /**Gets the angle of the vector in degrees relative to given axis.
     * <p>Rotation is mathematical, positive is towards the left.</p>*/
    default double getDegreesRelativeTo(Axis axis) {
        return getRadiansRelativeTo(axis.positiveDirection()) / RADIAN_PERIOD * DEGREE_PERIOD;
    }

    /**Gets the angle of the vector in degrees relative to the given direction.
     * <p>Rotation is mathematical, positive is towards the left.</p>
     * @return Value from {@code -180°} to {@code +180°} (signed half of {@link MathUtils#DEGREE_PERIOD full period}).*/
    default double getDegreesRelativeTo(IVector direction) {
        return getRadiansRelativeTo(direction) / RADIAN_PERIOD * DEGREE_PERIOD;
    }

    /**Gets angle of vector in absolute radians relative to the given axis.
     * <p>Rotation is mathematical, positive is towards the left.</p>
     * @return Value between {@link Math#PI -PI} radians and {@link Math#PI +PI} radians (signed half of {@link MathUtils#RADIAN_PERIOD full period}).*/
    default double getAbsRadiansRelativeTo(Axis axis) {
        double signedRads = getRadiansRelativeTo(axis);
        return (signedRads < 0)
                ? (RADIAN_PERIOD + signedRads)
                : signedRads;
    }

    /**Gets angle of vector in absolute radians relative to the given direction.
     * <p>Rotation is mathematical, positive is towards the left.</p>
     * @return Value between {@code 0} rad and {@link MathUtils#RADIAN_PERIOD 2*PI} radians.*/
    default double getAbsRadiansRelativeTo(IVector direction) {
        double signedRads = getRadiansRelativeTo(direction);
        return (signedRads < 0)
                ? (RADIAN_PERIOD + signedRads)
                : signedRads;
    }

    /**Gets angle of vector in absolute degrees relative to the given axis.
     * <p>Rotation is mathematical, positive is towards the left.</p>
     * @return Value between {@code 0°} and {@link MathUtils#DEGREE_PERIOD 360°}.*/
    default double getAbsDegreesRelativeTo(Axis axis) {
        return getAbsRadiansRelativeTo(axis.positiveDirection()) / RADIAN_PERIOD * DEGREE_PERIOD;
    }

    /**Gets angle of vector in absolute degrees relative to the given direction.
     * <p>Rotation is mathematical, positive is towards the left.</p>
     * @return Value between {@code 0°} and {@link MathUtils#DEGREE_PERIOD 360°}.*/
    default double getAbsDegreesRelativeTo(IVector direction) {
        return getAbsRadiansRelativeTo(direction) / RADIAN_PERIOD * DEGREE_PERIOD;
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
        if (length == 0) {
            throw new UnsupportedOperationException("Unit vector cannot be made out of vector with zero length!");
        } else if (isNaN(length)) {
            throw new UnsupportedOperationException("Vector is invalid, unit vector cannot be made out of it!");
        }
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
        return this.multiplyBy(1.0 / scalar);
    }

    /**Moves the destination of the vector, possibly changing its length and angle in the process
     * @param x A value to add to the X difference of this vector.
     * @param y A value to add to the Y difference of this vector.
     * @return A new vector moved with the given values.*/
    default IVector moveBy(double x, double y) {
        return vectorFromXY(getXDiff() + x, getYDiff() + y);
    }

    /**Clones this vector, rotates it by the given degrees and returns it.
     * <p>Rotation is mathematical, positive is towards the left.</p>
     * @param degrees Degrees to rotate the vector with.
     *                <p>A whole circle is {@link MathUtils#DEGREE_PERIOD 360} degrees.</p>
     * @see #rotateByRadians(double)
     * @see #rotateByGradians(double)
     * @return A new vector rotated by the given degrees.*/
    default IVector rotateByDegrees(double degrees) {
        return rotateByRadians(toRadians(degrees));
    }

    /**Clones this vector, rotates it by the given gradians and returns it.
     * <p>Rotation is mathematical, positive is towards the left.</p>
     * @param grads Gradians to rotate the vector with.
     *              <p>A whole circle is {@link MathUtils#GRADIAN_PERIOD 400} gradians.</p>
     * @see #rotateByRadians(double)
     * @see #rotateByDegrees(double)
     * @return A new vector rotated by the given gradians.*/
    default IVector rotateByGradians(double grads) {
        return rotateByRadians(grads / GRADIAN_PERIOD * RADIAN_PERIOD);
    }

    /**Returns a clone of the vector incremented its {@code x} and {@code y} coordinates with the given values.*/
    default IVector add(double x, double y) {
        return vectorFromXY(getXDiff() + x, getYDiff() + y);
    }

    /**Returns a clone of the vector incremented its {@code x} and {@code y} coordinates with those of the given vector.*/
    default IVector add(IVector another) {
        return this.add(another.getXDiff(), another.getYDiff());
    }

    /**Returns a clone of the vector incremented its {@code x} and {@code y} coordinates with the vector of the given direction and length.*/
    default IVector add(IVector direction, double length) {
        double dirLength = direction.getLength();
        if (dirLength == 0) {
            if (length != 0) {
                throw new UnsupportedOperationException("Vector (0;0) cannot be added with length other than 0.");
            } else {
                return this;
            }
        }
        return this.add(direction.getXDiff() / dirLength * length, direction.getYDiff() / dirLength * length);
    }

    /**Returns a clone of the vector decremented its {@code x} and {@code y} coordinates with the given values.*/
    default IVector subtract(double x, double y) {
        return vectorFromXY(getXDiff() - x, getYDiff() - y);
    }

    /**Returns a clone of the vector decremented its {@code x} and {@code y} coordinates with those of the given vector.*/
    default IVector subtract(IVector another) {
        return this.subtract(another.getXDiff(), another.getYDiff());
    }

    /**Returns a clone of the vector decremented its {@code x} and {@code y} coordinates with the vector of the given direction and length.*/
    default IVector subtract(IVector direction, double length) {
        double dirLength = direction.getLength();
        if (dirLength == 0) {
            if (length != 0) {
                throw new UnsupportedOperationException("Vector (0;0) cannot be subtracted with length other than 0.");
            } else {
                return this;
            }
        }
        return this.subtract(direction.getXDiff() / dirLength * length, direction.getYDiff() / dirLength * length);
    }

    /**Returns a clone of the vector but with the given {@code x} value.*/
    default IVector withX(double dx) {
        return vectorFromXY(dx, this.getYDiff());
    }

    /**Returns a clone of the vector but with the given {@code y} value.*/
    default IVector withY(double dy) {
        return vectorFromXY(this.getXDiff(), dy);
    }

    /**Returns a clone of the vector but with the given direction.
     * <p>Length of the vector remains unchanged (with epsilon error of floating point numbers).</p>*/
    default IVector withDirection(IVector newDirection) {
        return this.rotateByRadians(newDirection.getRadians() - this.getRadians());
    }

    /**Returns a clone of the vector, but with the given length.
     * <p>Direction/angle of the vector remains unchanged (with epsilon error of floating point numbers)
     * unless passing negative value -- which reverts the direction of the newly created vector.</p>*/
    default IVector withLength(double scalarLength) {
        if (!isDirectional()) {
            if (scalarLength == 0) {
                return nullVector();
            } else {
                throw new UnsupportedOperationException("The vector does not define a direction to apply new length to.");
            }
        }
        return this.multiplyBy(scalarLength / getLength());
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

            @Override
            public double getLength() {
                return sqrt(x*x + y*y);
            }

            @Override
            public double getRadiansRelativeTo(IVector direction) {
                if (isDirectional()) {
                    double xRads = direction.getRadiansRelativeTo(Axis.X);
                    return inPeriodOfPI(getRadiansRelativeToAxisX() - xRads);
                } else {
                    return NaN;
                }
            }

            @Override
            public double getRadiansRelativeTo(Axis axis) {
                return axis == Axis.X
                        ? getRadiansRelativeToAxisX()
                        : IVector.super.getRadiansRelativeTo(axis);
            }

            private double getRadiansRelativeToAxisX() {
                double x = getXDiff();
                double y = getYDiff();
                return atan2(y, x);
            }

            @Override
            public String toString() {
                return "XYVector{ x:" + x + ", y:" + y + " }";
            }

        };
    }

    /**Returns the average vector of the given two, having the average {@code x} and {@code y} coordinates of them.
     * <p>Length is the average of the two lengths,</p>*/
    static IVector average(IVector a, IVector b) {
        double xMean = (a.getXDiff() + b.getXDiff()) / 2.0;
        double yMean = (a.getYDiff() + b.getYDiff()) / 2.0;
        return vectorFromXY(xMean, yMean);
    }

    /**Returns a new vector with the given angle relative the {@link Axis#BASE base direction}.
     * <p>Length of the vector is one unit (with epsilon error of floating point numbers).</p>*/
    static IVector vectorWithAngle(double radians) {
        return baseDirection().rotateByRadians(radians);
    }

    static IVector nullVector() {
        return vectorFromXY(0, 0);
    }

    default String printXY(String format) {
        return String.format(format, this.getXDiff(), this.getYDiff());
    }

}
