package hu.oe.nik.szfmv.automatedcar.math;

import static java.lang.Math.*;

public interface IVector {

    double getXDiff();

    double getYDiff();

    /**Length in units.*/
    double getLength();

    double getRadiansRelativeTo(IVector direction);

    default IVector rotateByRadians(double rad) {
        double _yRad = getRadiansRelativeTo(Axis.Y);
        return Axis.Y.positiveDirection().rotateByRadians(_yRad + rad).multiplyBy(getLength());
    }

    /**Gets the angle of the vector left/right to given axis.*/
    default double getRadiansRelativeTo(Axis axis) {
        return getRadiansRelativeTo(axis.positiveDirection());
    }

    default double getDegreesRelativeTo(Axis axis) {
        return getRadiansRelativeTo(axis.positiveDirection()) / PI * 180;
    }

    default double getDegreesRelativeTo(IVector direction) {
        return getRadiansRelativeTo(direction) / PI * 180;
    }

    /**Gets angle of vector */
    default double getAbsRadiansRelativeTo(Axis axis) {
        double signedRads = getRadiansRelativeTo(axis);
        return (signedRads < 0)
                ? (2*PI + signedRads)
                : signedRads;
    }

    default double getAbsRadiansRelativeTo(IVector direction) {
        double signedRads = getRadiansRelativeTo(direction);
        return (signedRads < 0)
                ? (2*PI + signedRads)
                : signedRads;
    }

    default double getAbsDegreesRelativeTo(Axis axis) {
        return getAbsRadiansRelativeTo(axis.positiveDirection()) / PI * 180;
    }

    default double getAbsDegreesRelativeTo(IVector direction) {
        return getAbsRadiansRelativeTo(direction) / PI * 180;
    }

    static IVector fromXY(double x, double y) {
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
                double relativeRads  = (getRadiansRelativeToAxisX() - xRads) % (2 * PI);
                while (relativeRads < -PI) relativeRads += 2*PI;
                while (relativeRads > PI) relativeRads -= 2*PI;
                return relativeRads;
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

    default IVector reverse() {
        return IVector.fromXY(-getXDiff(), -getYDiff());
    }

    default IVector unit() {
        double length = getLength();
        return IVector.fromXY(getXDiff() / length, getYDiff() / length);
    }

    default IVector multiplyBy(double scalar) {
        return IVector.fromXY(getXDiff() * scalar, getYDiff() * scalar);
    }

    default IVector divideBy(double scalar) {
        return this.multiplyBy(1 / scalar);
    }

    default IVector moveBy(double x, double y) {
        return IVector.fromXY(getXDiff() + x, getYDiff() + y);
    }

    default IVector rotateByDegrees(double degrees) {
        return rotateByRadians(toRadians(degrees));
    }

    default IVector rotateByGradians(double grads) {
        return rotateByRadians(grads / 200 * PI);
    }

    static double radianRelative(double baseRadian, double increment) {
        return (baseRadian + increment) % PI;
    }

}
