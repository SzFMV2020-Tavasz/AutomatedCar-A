package hu.oe.nik.szfmv.automatedcar.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class IVectorTest {

    private static final double DELTA = 0.0000001;

    @Test
    void axes_vectors_points_to_right_directions() {
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
    void axes_vectors_have_zero_angle_relative_to_self() {
        for (Axis a : Axis.values()) {
            IVector posDir = a.positiveDirection();
            assertEquals(0, posDir.getRadiansRelativeTo(a));
            assertEquals(0, posDir.getRadiansRelativeTo(posDir), DELTA, () -> "Axis: " + a);
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
    void create_with_x_and_y() {
        IVector vector = vectorFromXY(1,2);
        assertEquals(1, vector.getXDiff(), DELTA);
        assertEquals(2, vector.getYDiff(), DELTA);
        assertTrue(vector.getDegreesRelativeTo(Axis.Y) < 0.0, () -> vector.getDegreesRelativeTo(Axis.Y) + "° should be < 0°");
        assertTrue(vector.getAbsDegreesRelativeTo(Axis.Y) > -45.0);
    }

    @Test
    void getLength() {
        IVector vector2 = vectorFromXY(1,1);
        assertEquals(sqrt(2), vector2.getLength(), DELTA);

        IVector vector13 = vectorFromXY(2,3);
        assertEquals(sqrt(13), vector13.getLength(), DELTA);
    }

    @Test
    void getDegree() {
        final double EIGHTH = PI / 4;
        IVector q1 = vectorFromXY(+1,+1);
        assertEquals(-45, q1.getDegreesRelativeTo(Axis.Y), DELTA);
        assertEquals(+360-45, q1.getAbsDegreesRelativeTo(Axis.Y), DELTA);
        assertEquals(-1 * EIGHTH, q1.getRadiansRelativeTo(Axis.Y), DELTA);
        assertEquals((8-1) * EIGHTH, q1.getAbsRadiansRelativeTo(Axis.Y), DELTA);

        IVector q2 = vectorFromXY(+1,-1);
        assertEquals(-135, q2.getDegreesRelativeTo(Axis.Y), DELTA);
        assertEquals(+360-135, q2.getAbsDegreesRelativeTo(Axis.Y), DELTA);
        assertEquals(-3 * EIGHTH, q2.getRadiansRelativeTo(Axis.Y), DELTA);
        assertEquals((8-3) * EIGHTH, q2.getAbsRadiansRelativeTo(Axis.Y), DELTA);

        IVector q3 = vectorFromXY(-1,-1);
        assertEquals(+135, q3.getDegreesRelativeTo(Axis.Y), DELTA);
        assertEquals(+135, q3.getAbsDegreesRelativeTo(Axis.Y), DELTA);
        assertEquals(+3 * EIGHTH, q3.getRadiansRelativeTo(Axis.Y), DELTA);
        assertEquals(+3 * EIGHTH, q3.getAbsRadiansRelativeTo(Axis.Y), DELTA);

        IVector q4 = vectorFromXY(-1,+1);
        assertEquals(+45.0, q4.getDegreesRelativeTo(Axis.Y), DELTA);
        assertEquals(+45.0, q4.getAbsDegreesRelativeTo(Axis.Y), DELTA);
        assertEquals(+1 * EIGHTH, q4.getRadiansRelativeTo(Axis.Y), DELTA);
        assertEquals(+1 * EIGHTH, q4.getAbsRadiansRelativeTo(Axis.Y), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.0, -1.0, 2.5, -2.5, 100, -100 })
    void rotation_from_axis_X_keeps_length(double rotationDegrees) {
        IVector vector = Axis.X.positiveDirection();
        for (int i = 0; i < 200; ++i) {
            IVector rotatedVector = vector.rotateByDegrees(rotationDegrees);
            assertNotEquals(vector.getXDiff(), rotatedVector.getXDiff());
            assertNotEquals(vector.getYDiff(), rotatedVector.getYDiff());
            assertEquals(1, rotatedVector.getLength(), DELTA);
            vector = rotatedVector;
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.0, -1.0, 2.5, -2.5, 100, -100 })
    void rotation_from_axis_Y_keeps_length(double rotationDegrees) {
        IVector vector = Axis.Y.positiveDirection();
        for (int i = 0; i < 200; ++i) {
            IVector rotatedVector  = vector.rotateByDegrees(rotationDegrees);
            assertFalse(vector.getXDiff() == rotatedVector.getXDiff() && vector.getYDiff() == rotatedVector.getYDiff());
            assertEquals(1, vector.getLength(), DELTA);
            vector = rotatedVector;
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.0, -1.0, 2.5, -2.5, 100, -100 })
    void rotation_from_axis_Y_increments_angle(double rotationDegrees) {
        IVector currentVector = Axis.Y.positiveDirection();
        for (int i = 0; i < 20; ++i) {
            IVector rotatedVector = currentVector.rotateByDegrees(rotationDegrees);
            double diffDeg = rotatedVector.getDegreesRelativeTo(currentVector);
            assertEquals(rotationDegrees, diffDeg, DELTA);
            currentVector = rotatedVector;
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.0, -1.0, 2.5, -2.5, 100, -100 })
    void rotation_from_axis_X_increments_angle(double rotationDegrees) {
        IVector currentVector = Axis.X.positiveDirection();
        for (int i = 0; i < 20; ++i) {
            IVector rotatedVector = currentVector.rotateByDegrees(rotationDegrees);
            double diffDeg = rotatedVector.getDegreesRelativeTo(currentVector);
            assertEquals(rotationDegrees, diffDeg, DELTA);
            currentVector = rotatedVector;
        }
    }

}