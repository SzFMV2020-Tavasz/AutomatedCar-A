package hu.oe.nik.szfmv.automatedcar.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DisplayImageDataTest {
    private DisplayImageData displayImageData;

    /**
     * Setting up the test
     */
    @BeforeEach
    public void init() {
        displayImageData = new DisplayImageData(10,11,1.2f,
            20, 21, 30,31);
    }

    @Test
    public void classInstantiated() {
        assertNotNull(displayImageData);
    }

    /**
     * Check whether the values are passed
     */
    @Test
    public void valuesPassed() {
        assertEquals(10, displayImageData.getX());
        assertEquals(11, displayImageData.getY());
        assertEquals(1.2f, displayImageData.getRotation());
        assertEquals(20, displayImageData.getRefDifferenceX());
        assertEquals(21, displayImageData.getRefDifferenceY());
        assertEquals(30, displayImageData.getRotationDisplacementX());
        assertEquals(31, displayImageData.getRotationDisplacementY());
    }

}
