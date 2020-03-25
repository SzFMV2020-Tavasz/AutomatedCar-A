package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.Deserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

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
    void polygon() {
        int idx = 0;
        try {
            worldObject = Deserializer.DeserializeWorldJson("test_world.json").getWorldObjects().get(idx);
        } catch (Exception e) {
            System.out.println("World Object " + worldObject.type + " does not contain any polygon at index " + idx + "!");
        }

        var bounds = worldObject.getPolygon().getBounds2D();
        assertTrue(bounds.contains(new Point(255, 255)));
        assertFalse(bounds.contains(new Point(0, 351)));
    }
}
