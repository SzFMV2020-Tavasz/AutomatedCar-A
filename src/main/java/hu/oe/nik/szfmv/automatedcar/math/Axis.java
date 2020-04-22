package hu.oe.nik.szfmv.automatedcar.math;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;
import static java.lang.Math.*;
import static java.lang.Math.PI;

/**A mathematical 2-dimensional axis; either {@link Axis#X} or {@link Axis#Y}.
 *
 * @author Dávid Magyar - davidson996@gmail.com*/
public enum Axis {

    /**The X axis out of the two 2-dimensional axes.
     * Usually interpreted as the horizontal axis.
     * @see Axis#Y*/
    X,

    /**The Y axis out of the two 2-dimensional axes.
     * Usually interpreted as the vertical axis.
     * @see Axis#X*/
    Y;

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
            return axis == Axis.X ? (positive ? +1 : -1) : 0;
        }

        @Override
        public double getYDiff() {
            return axis == Axis.Y ? (positive ? +1 : -1) : 0;
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
                return positive ? +(PI / 2) : -(PI / 2);
            }
        }

        @Override
        public double getRadiansRelativeTo(IVector direction) {
            double toPositive; switch (this.axis) {
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
                    return - (PI/2) - atan2(y, x);
                } else {
                    //Q2 - #2 ON RIGHT - NEGATIVE
                    return -(PI/2) - atan2(y, x);
                }
            } else {
                if (y < 0) {
                    //Q3 - #2 ON LEFT - POSITIVE
                    return (PI/2) + (PI + atan2(y, x));
                } else {
                    //Q4 - #1 ON LEFT - POSITIVE
                    return atan2(y,x) - (PI/2);
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
        public IVector rotateByRadians(double rad) {
            double alpha2 = (PI / 2) - rad;
            switch (this.axis) {
                case X:
                    return vectorFromXY(sin(alpha2), cos(alpha2));
                case Y:
                    return vectorFromXY(-cos(alpha2), sin(alpha2));
                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public String toString() {
            return "AxisVector{ " + axis + (positive ? "+": "-") + " }";
        }

    }

    /**The axis of default forward and backward direction.*/
    public static Axis BASE = X;

    /**The standard positive/neutral direction.
     * <p>Should be used for vectors only representing angle, length or both, but relative to no specific axis.</p>*/
    public static IVector baseDirection() {
        return Axis.BASE.positiveDirection();
    }

}
