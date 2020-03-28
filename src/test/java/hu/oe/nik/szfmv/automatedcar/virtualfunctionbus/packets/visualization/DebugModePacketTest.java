package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebugModePacketTest {
    AutomatedCar automatedCar;
    DebugModePacket debugModePacket;

    @BeforeEach
    public void init(){
        //AutomatedCar automatedCar = new AutomatedCar(200, 200, "car_2_red.png");
        debugModePacket = new DebugModePacket();
        debugModePacket.setDebuggingState(true);
    }

    /**
     * Check whether the class gets instatniatied when new DispLayobjct() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(debugModePacket);
    }

    /**
     * Checks whether the setting is passed right
     */
    @Test
    public void settingPassed() {
        assertEquals(true, debugModePacket.getDebuggingState());
    }
}
