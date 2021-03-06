package hu.oe.nik.szfmv.automatedcar.math;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;
import static java.lang.Math.*;
import static java.lang.Math.PI;

/**A mathematical 2-dimensional axis; either {@link Axis#X} or {@link Axis#Y}.
 *
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public enum Axis {

    /**The X axis out of the two 2-dimensional axes.
     * Usually interpreted as the horizontal axis.
     * @see Axis#Y*/
    X,

    /**The Y axis out of the two 2-dimensional axes.
     * Usually interpreted as the vertical axis.
     * @see Axis#X*/
    Y;

    /**Commonly used value - quarter of a full circle in radians.
     * Precomputed in order to fasten calculations a bit.*/
    private static final double RADIAN_QUARTER = PI / 2.0;

    /**The axis of default forward and backward direction.*/
    public static final Axis BASE = X;

    /**The standard positive/neutral direction.
     * <p>Should be used for vectors only representing angle, length or both, but relative to no specific axis.</p>*/
    public static IVector baseDirection() {
        return Axis.BASE.positiveDirection();
    }

    private final AxisVector dirPos = new AxisVector(this, true);
    private final AxisVector dirNeg = new AxisVector(this, false);

    /**Gets the unit vector of the axis towards its positive direction.*/
    public AxisVector positiveDirection() {
        return this.dirPos;
    }

    /**Gets the unit vector of the axis towards its negative direction.*/
    public AxisVector negativeDirection() {
        return this.dirNeg;
    }

    /**A vector implementation specific to the axes.
     * @see Axis#positiveDirection()
     * @see Axis#negativeDirection() */
    public static final class AxisVector implements IVector {

        private final Axis axis;
        private final boolean positive;

        private AxisVector(Axis axis, boolean positive) {
            this.axis = axis;
            this.positive = positive;
        }

        @Override
        public double getXDiff() {
            if (this.axis == Axis.X) {
                return (positive ? +1.0 : -1.0);
            } else {
                return 0;
            }
        }

        @Override
        public double getYDiff() {
            if (this.axis == Axis.Y) {
                return (positive ? +1.0 : -1.0);
            } else {
                return 0;
            }
        }

        @Override
        public double getLength() {
            return 1.0;
        }

        @Override
        public double getRadiansRelativeTo(Axis axis) {
            if (axis == this.axis) {
                return positive ? 0 : PI;
            } else {
                return positive
                        ? (+1 * RADIAN_QUARTER)
                        : (-1 * RADIAN_QUARTER);
            }
        }

        @Override
        public double getRadiansRelativeTo(IVector direction) {
            double towardsPositive = getRadiansRelativeToOwnAxis(direction);
            if (positive) {
                return towardsPositive;
            } else {
                return towardsPositive > 0
                        ? -(PI - towardsPositive)
                        : +(PI + towardsPositive);
            }
        }

        private double getRadiansRelativeToOwnAxis(IVector direction) {
            switch (this.axis) {
                case X:
                    return getRadiansRelativeToAxisX(direction);
                case Y:
                    return getRadiansRelativeToAxisY(direction);
                default:
                    throw new IllegalStateException();
            }
        }

        /**Rotation is mathematical, positive is towards the left.*/
        private static double getRadiansRelativeToAxisX(IVector direction) {
            return atan2(direction.getYDiff(), direction.getXDiff());
        }

        /**Rotation is mathematical, positive is towards the left.*/
        private static double getRadiansRelativeToAxisY(IVector direction) {
            double x = direction.getXDiff();
            double y = direction.getYDiff();
            if (x == 0.0) {
                return y >= 0 ? 0.0 : PI;
            }

            if (x >= 0) {
                if (y >= 0) {
                    //Q1 - #1 ON RIGHT - NEGATIVE
                    return -1 * RADIAN_QUARTER - atan2(y, x);
                } else {
                    //Q2 - #2 ON RIGHT - NEGATIVE
                    return -1 * RADIAN_QUARTER - atan2(y, x);
                }
            } else {
                if (y < 0) {
                    //Q3 - #2 ON LEFT - POSITIVE
                    return RADIAN_QUARTER + (PI + atan2(y, x));
                } else {
                    //Q4 - #1 ON LEFT - POSITIVE
                    return atan2(y,x) - RADIAN_QUARTER;
                }
            }
        }

        /**Gets the axis which this axis vector is associated with.*/
        public Axis getAxis() {
            return axis;
        }

        /**Whether this vector points to the positive direction of its associated axis or not.*/
        public boolean isPositive() {
            return positive;
        }

        @Override
        public IVector rotateByRadians(double radians) {
            double alpha2 = RADIAN_QUARTER - radians;
            switch (this.axis) {
                case X:
                    return vectorFromXY(sin(alpha2), cos(alpha2));
                case Y:
                    return vectorFromXY(-1 * cos(alpha2), sin(alpha2));
                default:
                    throw new IllegalStateException("Unexpected Axis: " + this);
            }
        }

        @Override
        public String toString() {
            return "AxisVector{ " + axis + (positive ? "+": "-") + " }";
        }

    }

}