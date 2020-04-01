package hu.oe.nik.szfmv.automatedcar.visualization;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UltrasoundPositionsTest {
    @Test
    public void testElements() {
        assertEquals("FRONT_RIGHT", UltrasoundPositions.FRONT_RIGHT.name());
        assertEquals(0, UltrasoundPositions.FRONT_RIGHT.getNumVal());

    }

}
