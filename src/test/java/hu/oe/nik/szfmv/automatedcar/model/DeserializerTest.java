package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.Deserializer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeserializerTest {
    @Test
    public void DeserializeWorldJsonTestSuccess() {
        World world = null;
        try {
            world = Deserializer.DeserializeWorldJson("test_world.json");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assertNotNull(world.getWorldObjects());
        assertNotEquals(0, world.getWorldObjects().size());
        assertEquals(3000, world.getHeight());
        assertEquals(5120, world.getWidth());

        var entry = world.getWorldObjects().get(0);
        assertEquals("road_2lane_straight", entry.getType());
        assertEquals(1700, entry.getX());
        assertEquals(144, entry.getY());
        assertEquals(0, entry.getZ());
        assertEquals(0.0f, entry.getRotationMatrix()[0][0]);
        assertEquals(true, entry.getIsStatic());

        var entry2 = world.getWorldObjects().get(6);
        assertEquals("road_2lane_45right_53", entry2.getId());
        assertEquals(0.7071068286895752f, entry2.getRotationMatrix()[0][0]);
    }

    @Test
    public void DeserializeWorldJsonTestNoFile() {
        World world = null;
        try {
            world = Deserializer.DeserializeWorldJson("test_worlds.json");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assertNull(world);
    }

    @Test
    public void DeserializeWorldJsonTestWrongFileExtension() {
        assertThrows(IllegalArgumentException.class, () -> Deserializer.DeserializeWorldJson("test_world.xml"));
    }

    @Test
    public void DeserializeReferencePointsJsonTestSuccess() {
        List<ReferencePoint> content = null;
        try {
            content = Deserializer.DeserializeReferencePointJson("reference_points.json");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assertNotNull(content);
        assertNotEquals(0, content.size());

        var entry = content.get(0);
        assertEquals("road_2lane_rotary.png", entry.getName());
        assertEquals(0, entry.getX());
        assertEquals(875, entry.getY());

        var entry2 = content.get(6);
        assertEquals("road_2lane_45left.png", entry2.getName());
        assertEquals(51, entry2.getX());
        assertEquals(371, entry2.getY());
    }
}
