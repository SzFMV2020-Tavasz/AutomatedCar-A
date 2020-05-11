package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import hu.oe.nik.szfmv.automatedcar.visualization.ParkingRadarPositions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParkingRadarVisualizationPacketTest {
    ParkingRadarVisualizationPacket parkingRadarVisualizationPacket;

    @BeforeEach
    public void init() {
        Point2D source = new Point2D.Double(10, 11);
        Point2D corner1 = new Point2D.Double(12, 13);
        Point2D corner2 = new Point2D.Double(14, 15);
        Color color = new Color(50, 51, 57);
        parkingRadarVisualizationPacket = new ParkingRadarVisualizationPacket();
        parkingRadarVisualizationPacket.setSensorTriangle(ParkingRadarPositions.LEFT, source, corner1, corner2);
        parkingRadarVisualizationPacket.setSensorTriangle(ParkingRadarPositions.RIGHT, corner1, source, corner2);
        parkingRadarVisualizationPacket.setSensorColor(color);
    }

    /**
     * Check whether the class gets instatniatied when new DispLayobjct() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(parkingRadarVisualizationPacket);
    }

    /**
     * Checks whether all data is passed through the packet class
     */
    @Test
    public void dataPassed() {
        // check first triangle random
        assertEquals(10, parkingRadarVisualizationPacket.getSources()[1].getX());
        assertEquals(11, parkingRadarVisualizationPacket.getSources()[1].getY());
        assertEquals(10, parkingRadarVisualizationPacket.getCorner1s()[0].getX());
        assertEquals(11, parkingRadarVisualizationPacket.getCorner1s()[0].getY());

        assertEquals(50, parkingRadarVisualizationPacket.getColor().getRed());
        assertEquals(51, parkingRadarVisualizationPacket.getColor().getGreen());
        assertEquals(57, parkingRadarVisualizationPacket.getColor().getBlue());

    }
}
