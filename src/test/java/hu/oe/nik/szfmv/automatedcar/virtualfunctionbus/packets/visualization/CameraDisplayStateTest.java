package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraDisplayStateTest {
    CameraDisplayStatePacket cameraDisplayStatePacket;

    @BeforeEach
    public void init() {
        cameraDisplayStatePacket = new CameraDisplayStatePacket();
        cameraDisplayStatePacket.setCameraDisplayState(true);
    }

    /**
     * Check whether the class gets instantiated when new CameraDisplayStatePacket() is called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(cameraDisplayStatePacket);
    }

    /**
     * Checks whether the setting is passed right
     */
    @Test
    public void settingPassed() {
        assertEquals(true, cameraDisplayStatePacket.getCameraDisplayState());
    }
}
