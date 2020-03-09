package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.Deserializer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeserializerTest {
    @Test
    public void DeserializeJsonTestSuccess() {
        List<WorldObject> content = null;
        try {
            content = Deserializer.DeserializeJson("test_world.json");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assertNotNull(content);
        assertNotEquals(0, content.size());

        var entry = content.get(0);

        assertEquals("road_2lane_straight", entry.getType());
        assertEquals(1700, entry.getX());
        assertEquals(144, entry.getY());
        assertEquals(0, entry.getZ());
        assertEquals("road_2lane_straight_1", entry.getId());
        assertEquals(0.0f, entry.getRotationMatrix()[0][0]);


        var entry2 = content.get(6);

        assertEquals("road_2lane_45right_7", entry2.getId());
        assertEquals(0.7071068286895752f, entry2.getRotationMatrix()[0][0]);
    }

    @Test
    public void DeserializeJsonTestNoFile() {
        List<WorldObject> content = null;
        try {
            content = Deserializer.DeserializeJson("test_worlds.json");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assertNull(content);
    }

    @Test
    public void DeserializeJsonTestWrongFileExtension() {
        assertThrows(IllegalArgumentException.class, () -> Deserializer.DeserializeJson("test_world.xml"));
    }
}
