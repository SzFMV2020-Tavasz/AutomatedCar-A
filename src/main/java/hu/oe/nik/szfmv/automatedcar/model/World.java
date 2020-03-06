package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    private int width;
    private int height;
    private List<WorldObject> worldObjects;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.worldObjects = Deserializer.DeserializeJson("test_world.json");
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
            if (!item.isStatic)
                dynamicObjectList.add(item);
        }
        return dynamicObjectList;
    }

    public List<WorldObject> getObjectsInsideTriangle(Point a, Point b, Point c) {
        List<WorldObject> objectInsideTriangle = new ArrayList<>();
        for (WorldObject item : worldObjects) {
            if (isInside(a,b,c,item))
                objectInsideTriangle.add(item);
        }
        return objectInsideTriangle;
    }

    private boolean isInside(Point a, Point b, Point c, WorldObject item){
        Point itemsPosition = new Point(item.getX(),item.getY());

        double A = area (a, b, c);
        double A1 = area (itemsPosition, b, c);
        double A2 = area (a, itemsPosition, c);
        double A3 = area (a, b, itemsPosition);
        return (A == A1 + A2 + A3);
    }

    private double area(Point a, Point b, Point c){
        return round((Math.abs((a.getX()*(b.getY()-c.getY()) + b.getX()*(c.getY()-a.getY())+ c.getX()*(a.getY()-b.getY()))/2.0)), 2);
    }

    private static double round(double value, int places) {
        if (places < 0){
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public WorldObject getObject(String id) {
        for (WorldObject item : worldObjects) {
            if (item.id.equals(id))
                return item;
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
}
