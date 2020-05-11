package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingDistancePacketTest {
    ParkingDistancePacket parkingDistancePacket;

    @BeforeEach
    public void init(){
        parkingDistancePacket = new ParkingDistancePacket();
        parkingDistancePacket.setDistance(1.5f);
    }

    /**
     * Check whether the class gets instantiated when new {@link ParkingDistancePacket} is called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(parkingDistancePacket);
    }

    /**
     * Checks whether the setting is passed right
     */
    @Test
    public void settingPassed() {
        assertEquals(1.5f, parkingDistancePacket.getDistance());
    }

}
