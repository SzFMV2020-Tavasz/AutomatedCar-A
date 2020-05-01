package hu.oe.nik.szfmv.automatedcar.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hu.oe.nik.szfmv.automatedcar.math.MathUtils.inPeriodOfPI;
import static hu.oe.nik.szfmv.automatedcar.math.MathUtils.isEqual;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Math Util Tests")
class MathUtilsTest {

    @Test
    @DisplayName("pi period trimming works as expected")
    void test_pi_period_conversion() {
        double inPIPeriod;

        for (double value = 0.0; value < 20.0; value += 0.1) {
            inPIPeriod = inPeriodOfPI(value);
            assertTrue(inPIPeriod <= +PI);
            assertTrue(inPIPeriod >= -PI);
        }
    }

    @Test
    @DisplayName("delta equality check of double's works as expected")
    void test_equality_check() {
        assertTrue(isEqual(0, 0, 0));
        assertFalse(isEqual(0, 0.00001, 0));
        assertTrue(isEqual(1, 2, 1));
        assertTrue(isEqual(1, 2, 1.5));
        assertTrue(isEqual(1, 2, 2));
        assertFalse(isEqual(1, 2, 0.5));
    }

    @Test
    @DisplayName("math utils has no enum values")
    void has_no_values() {
        assertEquals(0, MathUtils.values().length);
    }

}