package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.Deserializer;
import hu.oe.nik.szfmv.automatedcar.sensors.ObjectTransform;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class World {
    protected List<WorldObject> worldObjects;
    private int width;
    private int height;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.worldObjects = Deserializer.DeserializeJson("test_world.json");
        initPolygons();
    }

    public World(int width, int height, List<WorldObject> instanceList) {
        this.width = width;
        this.height = height;
        this.worldObjects = instanceList;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public List<WorldObject> getDynamics() {
        List<WorldObject> dynamicObjectList = new ArrayList<>();
        for (WorldObject item : worldObjects) {
            if (!item.isStatic) {
                dynamicObjectList.add(item);
            }
        }
        return dynamicObjectList;
    }

    public List<WorldObject> getObjectsInsideTriangle(Point a, Point b, Point c) {
        List<WorldObject> objectInsideTriangle = new ArrayList<>();
        for (WorldObject item : worldObjects) {
            if (isInside(a, b, c, item)) {
                objectInsideTriangle.add(item);
            }
        }
        return objectInsideTriangle;
    }

    private boolean isInside(Point a, Point b, Point c, WorldObject item) {
        Polygon sensor = new Polygon();
        sensor.addPoint(a.x, a.y);
        sensor.addPoint(b.x, b.y);
        sensor.addPoint(c.x, c.y);

        ArrayList<Path2D> polys = item.getPolygons();
        if (polys.size() != 0) {
            for (Path2D poly : ObjectTransform.transformPath2DPolygon(item)) {
                if (poly != null) {
                    if (sensor.intersects(poly.getBounds2D())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static double round(double value, int places) {
        final int POW_BASE = 10;
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(POW_BASE, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public WorldObject getObject(String id) {
        for (WorldObject item : worldObjects) {
            if (item.id.equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void setObject(String id, int x, int y, float[][] rotationMatrix) {
        for (WorldObject item : worldObjects
        ) {
            if (item.getId().equals(id)) {
                item.setX(x);
                item.setY(y);
                item.setRotationMatrix(rotationMatrix);
            }
        }
    }

    /**
     * Add an object to the virtual world.
     *
     * @param o {@link WorldObject} to be added to the virtual world
     */
    public void addObjectToWorld(WorldObject o) {
        worldObjects.add(o);
    }

    /**
     * Initializes the ReadPolygon class: makes it load the debug json
     * and add the debug polygons to the objects
     */
    protected void initPolygons() {
        for (WorldObject obj : worldObjects) {
            if (obj != null) {
                obj.initPolygons();
            }
        }
    }
}
