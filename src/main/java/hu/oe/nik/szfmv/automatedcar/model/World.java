package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    private int width;
    private int height;
    private List<WorldObject> worldObjects;
    private List<WorldObjectDes> worldObjectsDes;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.worldObjectsDes = Deserializer.DeserializeJson("test_world.json");
        this.worldObjects = new ArrayList<>();

        ParseToWorldObject();
    }

    public World(int width, int height, List<WorldObjectDes> instanceList) {
        this.width = width;
        this.height = height;
        this.worldObjectsDes = instanceList;

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

    public List<WorldObjectDes> getWorld() {
        return worldObjectsDes;
    }

    public List<WorldObjectDes> getDynamics() {
        List<WorldObjectDes> dynamicObjectList = new ArrayList<>();
        for (WorldObjectDes item : worldObjectsDes) {
            if (!item.isStatic)
                dynamicObjectList.add(item);
        }
        return dynamicObjectList;
    }

    public WorldObjectDes getObject(String id) {
        for (WorldObjectDes item : worldObjectsDes) {
            if (item.id.equals(id))
                return item;
        }
        return null;
    }

    public void setObject(int x, int y, float[][] rotationMatrix) {
        for (WorldObjectDes item : worldObjectsDes
        ) {
            if (item.type.contains("car")) {
                item.x = x;
                item.y = y;
                item.rotationMatrix = rotationMatrix;
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

    private void ParseToWorldObject() {
        for (WorldObjectDes objectDes : this.worldObjectsDes) {
            var obj = new WorldObject(objectDes.x, objectDes.y, objectDes.type + ".png");
            obj.rotation = 0f;
            this.worldObjects.add(obj);
        }
    }
}
