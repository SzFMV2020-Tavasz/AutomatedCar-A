package hu.oe.nik.szfmv.automatedcar.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DisplayWorldTest {

    private DisplayWorld displayWorld;

    /**
     * Setting up the test
     */
    @BeforeEach
    public void init() {
        displayWorld = new DisplayWorld();
    }


    /**
     * Check whether the class gets instatniatied when new DispLayWorld() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(displayWorld);
    }


}
