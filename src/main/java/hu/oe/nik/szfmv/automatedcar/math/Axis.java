package hu.oe.nik.szfmv.automatedcar.math;

import static java.lang.Math.*;
import static java.lang.Math.PI;

public enum Axis { X, Y;

    private final AxisVector dirPos = new AxisVector(this, true);
    private final AxisVector dirNeg = new AxisVector(this, false);

    public AxisVector positiveDirection() {
        return this.dirPos;
    }

    public AxisVector negativeDirection() {
        return this.dirNeg;
    }

    public static final class AxisVector implements IVector {
        private final Axis axis;
        private final boolean positive;

        public AxisVector(Axis axis, boolean positive) {
            this.axis = axis;
            this.positive = positive;
        }

        @Override public double getXDiff() {
            return axis == Axis.X ? (positive ? +1 : -1) : 0;
        }

        @Override public double getYDiff() {
            return axis == Axis.Y ? (positive ? +1 : -1) : 0;
        }

        @Override public double getLength() {
            return 1.0;
        }

        @Override public double getRadiansRelativeTo(Axis axis) {
            if (axis == this.axis) {
                return positive ? 0 : PI;
            } else {
                return positive ? +(PI / 2) : -(PI / 2);
            }
        }

        @Override public double getRadiansRelativeTo(IVector direction) {
            double toPositive; switch (this.axis) {
                case X:
                    toPositive = getRadiansRelativeToAxisX(direction);
                    break;
                case Y:
                    toPositive = getRadiansRelativeToAxisY(direction);
                    break;
                default:
                    throw new IllegalStateException();
            }

            return positive ? toPositive : toPositive > 0
                    ? -(PI - toPositive)
                    : +(PI + toPositive);
        }

        private static double getRadiansRelativeToAxisX(IVector direction) {
            return atan2(direction.getYDiff(), direction.getXDiff());
        }

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

        public Axis getAxis() {
            return axis;
        }

        public boolean isPositive() {
            return positive;
        }

        @Override public String toString() {
            return "AxisVector{ " + axis + (positive ? " (+)": " (-)") + " }";
        }
    }
}
