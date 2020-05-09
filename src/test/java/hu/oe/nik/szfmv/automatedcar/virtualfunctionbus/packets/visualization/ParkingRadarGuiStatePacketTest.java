package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParkingRadarGuiStatePacketTest {
    ParkingRadarGuiStatePacket parkingRadarGuiStatePacket;

    @BeforeEach
    public void init() {
        parkingRadarGuiStatePacket = new ParkingRadarGuiStatePacket();
        parkingRadarGuiStatePacket.setParkingRadarGuiState(true);
    }

    /**
     * Check whether the class gets instantiated when new ParkingRadarDisplayStatePacket() is called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(parkingRadarGuiStatePacket);
    }

    /**
     * Checks whether the setting is passed right
     */
    @Test
    public void settingPassed() {
        assertEquals(true, parkingRadarGuiStatePacket.getParkingRadarGuiState());
    }




}
