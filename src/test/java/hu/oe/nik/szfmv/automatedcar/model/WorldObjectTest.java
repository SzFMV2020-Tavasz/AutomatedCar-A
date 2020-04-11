package hu.oe.nik.szfmv.automatedcar.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WorldObjectTest {

    WorldObject worldObject;

    @BeforeEach
    void init() {
        worldObject = new WorldObject(100, 100, "car_2_white.png");
    }

    @Test
    void addition() {
        System.out.println(worldObject.getX());
        assertEquals(100, worldObject.getX());
    }

    @Test
    void setRadarHighlighted() {
        assertFalse(worldObject.isHighlightedWhenRadarIsOn());
        worldObject.setHighlightedWhenRadarIsOn(true);
        assertTrue(worldObject.isHighlightedWhenRadarIsOn());
    }

    @Test
    void setCameraHighlighted() {
        assertFalse(worldObject.isHighlightedWhenCameraIsOn());
        worldObject.setHighlightedWhenCameraIsOn(true);
        assertTrue(worldObject.isHighlightedWhenCameraIsOn());
    }

    @Test
    void setUltrasoundHighlighted() {
        assertFalse(worldObject.isHighlightedWhenUltrasoundIsOn());
        worldObject.setHighlightedWhenUltrasoundIsOn(true);
        assertTrue(worldObject.isHighlightedWhenUltrasoundIsOn());
    }

}
