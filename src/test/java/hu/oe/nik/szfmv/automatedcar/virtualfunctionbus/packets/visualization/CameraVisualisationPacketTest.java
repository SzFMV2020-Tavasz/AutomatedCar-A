package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CameraVisualisationPacketTest {

    CameraVisualizationPacket cameraVisualizationPacket;

    @BeforeEach
    public void init() {
        Point2D source = new Point2D.Double(10, 11);
        Point2D corner1 = new Point2D.Double(12, 13);
        Point2D corner2 = new Point2D.Double(14, 15);
        Color color = new Color(50, 51, 57);
        cameraVisualizationPacket = new CameraVisualizationPacket();
        cameraVisualizationPacket.setSensorTriangle(source, corner1, corner2, color);
    }

    /**
     * Check whether the class gets instatniatied when new DispLayobjct() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(cameraVisualizationPacket);
    }

    /**
     * Checks whether all data is passed through the packet class
     */
    @Test
    public void dataPassed() {
        assertEquals(10, cameraVisualizationPacket.getSensorSource().getX());
        assertEquals(11, cameraVisualizationPacket.getSensorSource().getY());
        assertEquals(12, cameraVisualizationPacket.getSensorCorner1().getX());
        assertEquals(13, cameraVisualizationPacket.getSensorCorner1().getY());
        assertEquals(14, cameraVisualizationPacket.getSensorCorner2().getX());
        assertEquals(15, cameraVisualizationPacket.getSensorCorner2().getY());
        assertEquals(50, cameraVisualizationPacket.getColor().getRed());
        assertEquals(51, cameraVisualizationPacket.getColor().getGreen());
        assertEquals(57, cameraVisualizationPacket.getColor().getBlue());

    }
}
