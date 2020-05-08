package hu.oe.nik.szfmv.automatedcar.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldTest {
    List<WorldObject> testList = new ArrayList<>();
    World worldInstance;

    @BeforeEach
    void init() {
        Path2D polygon = new Path2D.Double();
        polygon.moveTo(0, 50);
        polygon.lineTo(10, 50);
        polygon.lineTo(0, 40);
        polygon.closePath();

        initPolygons1(polygon);
        initPolygons2(polygon);

        worldInstance = new World(10, 10, testList);
    }

    private void initPolygons1(Path2D polygon) {
        WorldObject instance = new WorldObject("4");
        instance.setIsStatic(true);
        instance.setType("car_1_blue");
        instance.setX(5);
        instance.setY(4);
        instance.polygons = new ArrayList<>(Arrays.asList(polygon));
        instance.setImageFileName(instance.getType() + ".png");
        instance.setRotationMatrix(new float[][]{{1, 2}, {1, 2}});

        WorldObject instance1 = new WorldObject("3");
        instance1.setIsStatic(true);
        instance1.setX(100);
        instance1.setY(100);
        instance1.polygons = new ArrayList<>(Arrays.asList(polygon));
        instance1.setImageFileName(instance.getType() + ".png");
        instance1.setType("road_2lane_straight");

        testList.add(instance);
        testList.add(instance1);
    }

    private void initPolygons2(Path2D polygon) {
        WorldObject instance2 = new WorldObject("2");
        instance2.setIsStatic(true);
        instance2.setX(500);
        instance2.setY(100);
        instance2.polygons = new ArrayList<>(Arrays.asList(polygon));
        instance2.setImageFileName(instance2.getType() + ".png");
        instance2.setType("tree");

        WorldObject instance3 = new WorldObject("1");
        instance3.setX(0);
        instance3.setY(400);
        instance3.polygons = new ArrayList<>(Arrays.asList(polygon));
        instance3.setImageFileName(instance3.getType() + ".png");
        instance3.setIsStatic(false);
        instance3.setType("man");

        testList.add(instance2);
        testList.add(instance3);
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
    void testObjectInsideTriangle() {
        String expectedId = "3";
        Point a = new Point(40, 40);
        Point b = new Point(40, 150);
        Point c = new Point(150, 40);
        List<WorldObject> objectInsideTriangle = worldInstance.getObjectsInsideTriangle(a, b, c);
        int size = objectInsideTriangle.size();
        assertEquals(1, size);
        assertEquals(expectedId, objectInsideTriangle.get(0).getId());
    }
}

