package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingDataDisplayStatePacketTest {
    ParkingRadarDisplayStatePacket parkingRadarDisplayStatePacket;

    @BeforeEach
    public void  init() {
        parkingRadarDisplayStatePacket = new ParkingRadarDisplayStatePacket();
        parkingRadarDisplayStatePacket.setRadarDisplayState(true);
    }

    /**
     * Check whether the class gets instantiated when new {@link ParkingRadarDisplayStatePacket} is called
     */
    @Test
    public void classInstantiated(){
        assertNotNull(parkingRadarDisplayStatePacket);
    }

    /**
     * Check whether the setting is passed right
     */
    @Test
    public void SettingPassed(){
        assertEquals(true, parkingRadarDisplayStatePacket.getRadarDisplayState());
    }
}
