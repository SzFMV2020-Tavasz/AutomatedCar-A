package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RadarVisualisationPacketTest {
    RadarVisualizationPacket radarVisualizationPacket;

    @BeforeEach
    public void init() {
        Point2D source = new Point2D.Double(10, 11);
        Point2D corner1 = new Point2D.Double(12, 13);
        Point2D corner2 = new Point2D.Double(14, 15);
        Color color = new Color(50, 51, 57);
        radarVisualizationPacket = new RadarVisualizationPacket();
        radarVisualizationPacket.setSensorTriangle(source, corner1, corner2, color);
    }

    /**
     * Check whether the class gets instatniatied when new DispLayobjct() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(radarVisualizationPacket);
    }

    /**
     * Checks whether all data is passed through the packet class
     */
    @Test
    public void dataPassed() {
        assertEquals(10, radarVisualizationPacket.getSensorSource().getX());
        assertEquals(11, radarVisualizationPacket.getSensorSource().getY());
        assertEquals(12, radarVisualizationPacket.getSensorCorner1().getX());
        assertEquals(13, radarVisualizationPacket.getSensorCorner1().getY());
        assertEquals(14, radarVisualizationPacket.getSensorCorner2().getX());
        assertEquals(15, radarVisualizationPacket.getSensorCorner2().getY());
        assertEquals(50, radarVisualizationPacket.getColor().getRed());
        assertEquals(51, radarVisualizationPacket.getColor().getGreen());
        assertEquals(57, radarVisualizationPacket.getColor().getBlue());

    }
}
