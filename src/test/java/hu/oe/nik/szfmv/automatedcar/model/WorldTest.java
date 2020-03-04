package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.WorldObjectDes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldTest {
    List<WorldObjectDes> testList = new ArrayList<>();
    World worldInstance;

    @BeforeEach
    void init() {
        WorldObjectDes instance = new WorldObjectDes();
        instance.isStatic = true;
        instance.type = "car";
        instance.x = 5;
        instance.y = 4;
        instance.rotationMatrix = new float[][]{{1, 2}, {1, 2}};
        instance.id = "4";

        WorldObjectDes instance1 = new WorldObjectDes();
        instance1.isStatic = true;
        instance1.type = "road";

        WorldObjectDes instance2 = new WorldObjectDes();
        instance2.isStatic = true;
        instance2.type = "tree";

        WorldObjectDes instance3 = new WorldObjectDes();
        instance3.isStatic = false;
        instance3.type = "pedestrian";

        testList.add(instance);
        testList.add(instance1);
        testList.add(instance2);
        testList.add(instance3);
        worldInstance = new World(10, 10, testList);


    }

    @Test
    void testGetWorld() {
        int expectedValue = 4;
        int getValue = worldInstance.getWorld().size();
        assertEquals(expectedValue, getValue);
    }

    @Test
    void testGetDynamics() {
        int expectedValue = 1;
        int getValue = worldInstance.getDynamics().size();
        assertEquals(expectedValue, getValue);
    }

    @Test
    void testSetObject() {
        int expectedX = 5;
        int expectedY = 4;
        float[][] expectedRotation = new float[][]{{1, 2}, {1, 2}};
        worldInstance.setObject(expectedX, expectedY, expectedRotation);
        for (WorldObjectDes item : worldInstance.getWorld()
        ) {
            if (item.type.contains("car")) {
                assertEquals(expectedX, item.x);
                assertEquals(expectedY, item.y);
                assertEquals(expectedRotation, item.rotationMatrix);
            }

        }

    }

    @Test
    void testGetObjectById() {
        String expectedId = "4";
        WorldObjectDes instance = worldInstance.getObject(expectedId);
        assertEquals(expectedId, instance.id);


    }


}
