package hu.oe.nik.szfmv.automatedcar.math;

import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class IVectorTest {

    @Test
    void axes_vectors() {
        assertEquals(+1, Axis.X.positiveDirection().getXDiff());
        assertEquals(-1, Axis.X.negativeDirection().getXDiff());
        assertEquals(+1, Axis.Y.positiveDirection().getYDiff());
        assertEquals(-1, Axis.Y.negativeDirection().getYDiff());
        assertEquals(0, Axis.X.positiveDirection().getYDiff());
        assertEquals(0, Axis.X.negativeDirection().getYDiff());
        assertEquals(0, Axis.Y.positiveDirection().getXDiff());
        assertEquals(0, Axis.Y.negativeDirection().getXDiff());
    }

    @Test
    void axes_vectors_self() {
        for (Axis a : Axis.values()) {
            IVector posDir = a.positiveDirection();
            assertEquals(0, posDir.getRadiansRelativeTo(a));
            assertEquals(0, posDir.getRadiansRelativeTo(posDir), 0.0000001, () -> "Axis: " + a);
        }
    }

    @Test
    void axes_vectors_opposite() {
        for (Axis a : Axis.values()) {
            IVector posDir = a.positiveDirection();
            IVector negDir = a.negativeDirection();
            assertEquals(PI, negDir.getRadiansRelativeTo(posDir));
            assertEquals(PI, posDir.getRadiansRelativeTo(negDir));
        }
    }

    @Test
    void create_with_data() {
        IVector vector = IVector.fromXY(1,2);
        assertEquals(1, vector.getXDiff(), 0.00001);
        assertEquals(2, vector.getYDiff(), 0.00001);
        assertTrue(vector.getDegreesRelativeTo(Axis.Y) < 0.0, () -> vector.getDegreesRelativeTo(Axis.Y) + "° should be < 0°");
        assertTrue(vector.getAbsDegreesRelativeTo(Axis.Y) > -45.0);
    }

    @Test
    void getLength() {
        IVector vector = IVector.fromXY(1,1);
        assertEquals(sqrt(2), vector.getLength(), 0.00001);
    }

    @Test
    void getDegree() {
        final double EIGHTH = PI / 4;
        IVector q1 = IVector.fromXY(+1,+1);
        assertEquals(-45, q1.getDegreesRelativeTo(Axis.Y), 0.00001);
        assertEquals(+360-45, q1.getAbsDegreesRelativeTo(Axis.Y), 0.00001);
        assertEquals(-1 * EIGHTH, q1.getRadiansRelativeTo(Axis.Y), 0.00001);
        assertEquals((8-1) * EIGHTH, q1.getAbsRadiansRelativeTo(Axis.Y), 0.00001);

        IVector q2 = IVector.fromXY(+1,-1);
        assertEquals(-135, q2.getDegreesRelativeTo(Axis.Y), 0.00001);
        assertEquals(+360-135, q2.getAbsDegreesRelativeTo(Axis.Y), 0.00001);
        assertEquals(-3 * EIGHTH, q2.getRadiansRelativeTo(Axis.Y), 0.00001);
        assertEquals((8-3) * EIGHTH, q2.getAbsRadiansRelativeTo(Axis.Y), 0.00001);

        IVector q3 = IVector.fromXY(-1,-1);
        assertEquals(+135, q3.getDegreesRelativeTo(Axis.Y), 0.00001);
        assertEquals(+135, q3.getAbsDegreesRelativeTo(Axis.Y), 0.00001);
        assertEquals(+3 * EIGHTH, q3.getRadiansRelativeTo(Axis.Y), 0.00001);
        assertEquals(+3 * EIGHTH, q3.getAbsRadiansRelativeTo(Axis.Y), 0.00001);

        IVector q4 = IVector.fromXY(-1,+1);
        assertEquals(+45.0, q4.getDegreesRelativeTo(Axis.Y), 0.00001);
        assertEquals(+45.0, q4.getAbsDegreesRelativeTo(Axis.Y), 0.00001);
        assertEquals(+1 * EIGHTH, q4.getRadiansRelativeTo(Axis.Y), 0.00001);
        assertEquals(+1 * EIGHTH, q4.getAbsRadiansRelativeTo(Axis.Y), 0.00001);
    }

}