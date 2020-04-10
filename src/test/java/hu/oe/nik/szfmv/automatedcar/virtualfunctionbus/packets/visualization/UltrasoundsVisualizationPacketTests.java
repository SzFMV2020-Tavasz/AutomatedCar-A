package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import hu.oe.nik.szfmv.automatedcar.visualization.DisplayObject;
import hu.oe.nik.szfmv.automatedcar.visualization.DisplaySensorObjectTest;
import hu.oe.nik.szfmv.automatedcar.visualization.UltrasoundPositions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UltrasoundsVisualizationPacketTests {
    UltrasoundsVisualizationPacket ultrasoundsVisualizationPacket;

    @BeforeEach
    public void init() {
        Point2D source = new Point2D.Double(10, 11);
        Point2D corner1 = new Point2D.Double(12, 13);
        Point2D corner2 = new Point2D.Double(14, 15);

        ultrasoundsVisualizationPacket = new UltrasoundsVisualizationPacket();
        ultrasoundsVisualizationPacket.setSensorTriangle(UltrasoundPositions.FRONT_LEFT, source, corner1, corner2);
    }

    /**
     * Check whether the class gets instantiated when new UltrasoudnVisualizationPacket() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(ultrasoundsVisualizationPacket);
    }

    /**
     * Checks whether black color returned if no color is given.
     */
    @Test
    public void nullColorBlack() {
        assertEquals(new Color(0, 0, 0), ultrasoundsVisualizationPacket.getColor());
    }

    /**
     * Checks whether the right color is returned when a color is set.
     */
    @Test
    public void colorReturned() {
        Color color = new Color(50, 51, 57);
        ultrasoundsVisualizationPacket.setSensorColor(color);
        assertEquals(color, ultrasoundsVisualizationPacket.getColor());
    }

    @Test
    public void dataReturned() {
        Point2D[] sources = ultrasoundsVisualizationPacket.getSources();
        Point2D[] corner1s = ultrasoundsVisualizationPacket.getCorner1s();
        Point2D[] corner2s = ultrasoundsVisualizationPacket.getCorner2s();
        assertEquals(10, sources[1].getX());
        assertEquals(11, sources[1].getY());
        assertEquals(12, corner1s[1].getX());
        assertEquals(13, corner1s[1].getY());
        assertEquals(14, corner2s[1].getX());
        assertEquals(15, corner2s[1].getY());
    }

    @Test
    public void nullReturned() {
        Point2D[] sources = ultrasoundsVisualizationPacket.getSources();
        Point2D[] corner1s = ultrasoundsVisualizationPacket.getCorner1s();
        Point2D[] corner2s = ultrasoundsVisualizationPacket.getCorner2s();
        assertNull(sources[2]);
        assertNull(corner1s[2]);
        assertNull(corner2s[2]);
    }

}


