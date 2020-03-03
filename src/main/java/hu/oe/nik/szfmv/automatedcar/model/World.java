package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.WorldObjectDes;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.Deserializer;
import hu.oe.nik.szfmv.automatedcar.model.deserializer.WorldObjectDes;

import java.util.ArrayList;
import java.util.List;

public class World {
    private int width;
    private int height;
    private List<WorldObjectDes> worldObjects;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.worldObjects = Deserializer.DeserializeJson("test_world.json");
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

    public List<WorldObjectDes> getWorld() {
        return worldObjects;
    }

    public List<WorldObjectDes> getDynamics() {
        List<WorldObjectDes> dynamicObjectList = new ArrayList<>();
        for (WorldObjectDes item : worldObjects) {
            if (!item.isStatic)
                dynamicObjectList.add(item);
        }
        return dynamicObjectList;
    }

    public WorldObjectDes getObject(String id) {
        for (WorldObjectDes item : worldObjects) {
            if (item.id.equals(id))
                return item;
        }
        return null;
    }

    public void setObject(int x, int y, float[][] rotationMatrix) {
        for (WorldObjectDes item : worldObjects
        ) {
            if (item.type.contains("car")) {
                item.x = x;
                item.y = y;
                item.rotationMatrix = rotationMatrix;
            }
        }
    }
}
