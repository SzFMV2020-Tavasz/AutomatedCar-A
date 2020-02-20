package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.Deserializer;
import hu.oe.nik.szfmv.automatedcar.model.deserializer.WorldObjectDes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeserializerTest {
    @Test
    public void DeserializeJsonTestSuccess() {
        List<WorldObjectDes> content = null;
        try {
            content = Deserializer.DeserializeJson("test_world.json");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assertNotNull(content);
    }

    @Test
    public void DeserializeJsonTestNoFile() {
        List<WorldObjectDes> content = null;
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
