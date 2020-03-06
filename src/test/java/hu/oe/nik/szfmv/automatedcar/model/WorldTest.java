package hu.oe.nik.szfmv.automatedcar.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldTest {
    List<WorldObject> testList = new ArrayList<>();
    World worldInstance;

    @BeforeEach
    void init() {
        WorldObject instance = new WorldObject("4");
        instance.setIsStatic(true);
        instance.setType("car_1_blue");
        instance.setX(5);
        instance.setY(4);
        instance.setRotationMatrix(new float[][]{{1, 2}, {1, 2}});

        WorldObject instance1 = new WorldObject("3");
        instance1.setIsStatic(true);
        instance1.setType("road_2lane_straight");

        WorldObject instance2 = new WorldObject("2");
        instance2.setIsStatic(true);
        instance2.setType("tree");

        WorldObject instance3 = new WorldObject("1");
        instance3.setIsStatic(false);
        instance3.setType("man");

        testList.add(instance);
        testList.add(instance1);
        testList.add(instance2);
        testList.add(instance3);
        worldInstance = new World(10, 10, testList);
    }

    @Test
    void testGetWorld() {
        int expectedValue = 4;
        int getValue = worldInstance.getWorldObjects().size();
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
        String id = "4";
        int expectedX = 5;
        int expectedY = 4;
        float[][] expectedRotation = new float[][]{{1, 2}, {1, 2}};
        worldInstance.setObject(id, expectedX, expectedY, expectedRotation);
        var edited = worldInstance.getObject(id);
        if (edited.getId().equals(id)) {
            assertEquals(expectedX, edited.getX());
            assertEquals(expectedY, edited.getY());
            assertEquals(expectedRotation, edited.getRotationMatrix());
        }
    }

    @Test
    void testGetObjectById() {
        String expectedId = "4";
        WorldObject instance = worldInstance.getObject(expectedId);
        assertEquals(expectedId, instance.getId());
    }

    @Test
    void testObjectInsideTriangle(){
        String expectedId = "4";
        Point a = new Point(5,8);
        Point b = new Point(0,2);
        Point c = new Point(10,2);
        List<WorldObject> objectInsideTriangle = worldInstance.getObjectsInsideTriangle(a,b,c);
        int size = objectInsideTriangle.size();
        assertEquals(1, size);
        assertEquals(expectedId, objectInsideTriangle.get(0).getId());
    }
}

