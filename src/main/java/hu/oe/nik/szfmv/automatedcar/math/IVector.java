package hu.oe.nik.szfmv.automatedcar.math;

import static java.lang.Math.*;

public interface IVector {

    double getLength();

    double getDegree();

    double getXDiff();

    double getYDiff();

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
                double xDiff = getXDiff();
                double yDiff = getXDiff();
                return sqrt(xDiff * xDiff + yDiff * yDiff);
            }

            @Override public double getDegree() {
                double cosA = getYDiff() / getXDiff();
                return cosh(cosA);
            }
        };
    }

    static IVector fromDegreeAndLength(double degree, double length) {
        double x = length * cos(degree);
        double y = length * sin(degree);

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
                return length;
            }

            @Override
            public double getDegree() {
                return degree;
            }
        };
    }

}
