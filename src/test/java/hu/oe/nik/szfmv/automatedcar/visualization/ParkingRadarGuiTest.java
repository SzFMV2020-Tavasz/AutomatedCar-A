package hu.oe.nik.szfmv.automatedcar.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingRadarGuiTest {
    ParkingRadarGui parkingRadarGui;

    @BeforeEach
    public void init() {
        parkingRadarGui = new ParkingRadarGui(10, 10, 215, 0x0000ff);
    }

    /**
     * Checks whether the class gets instantiated, when new {@link ParkingRadarGui} is called
     */
    @Test
    public void callsInstantiated() {
        assertNotNull(parkingRadarGui);
    }

    @Test
    public void distanceSetRight() {
        parkingRadarGui.setDistanceRight(10.2f);
        parkingRadarGui.setDistanceLeft(0.1f);
        assertEquals(10.2f, parkingRadarGui.distanceRight);
        assertEquals(0.1f, parkingRadarGui.distanceLeft);
    }

    @Test
    public void onOffState() {
        parkingRadarGui.setState(true);
        assertEquals(true, parkingRadarGui.on);
        parkingRadarGui.setState(false);
        assertEquals(false, parkingRadarGui.on);
    }

    @Test
    public void rectanglesDrawn() {
        BufferedImage bi = new BufferedImage(205, 40, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        parkingRadarGui.setDistanceLeft(0.3f);
        parkingRadarGui.setDistanceRight(0.8f);
        parkingRadarGui.setState(true);
        parkingRadarGui.paintComponent(g);
        // left green 1
        assertEquals(Color.green, new Color(bi.getRGB(20, 30)));
        // left green 2
        assertEquals(Color.green, new Color(bi.getRGB(29, 30)));
        // left green 3
        assertEquals(Color.green, new Color(bi.getRGB(38, 30)));
        // left green 4
        assertEquals(Color.green, new Color(bi.getRGB(47, 30)));
        // left red
        assertEquals(Color.red, new Color(bi.getRGB(56, 30)));

        // right green 1
        assertEquals(Color.darkGray, new Color(bi.getRGB(185, 30)));
        // right green 2
        assertEquals(Color.darkGray, new Color(bi.getRGB(176, 30)));
        // right green 3
        assertEquals(Color.darkGray, new Color(bi.getRGB(167, 30)));
        // right green 4
        assertEquals(Color.darkGray, new Color(bi.getRGB(158, 30)));
        // right red
        assertEquals(Color.darkGray, new Color(bi.getRGB(155, 30)));
    }
}
