package hu.oe.nik.szfmv.automatedcar.model.deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import hu.oe.nik.szfmv.automatedcar.model.ReferencePoint;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Stream;

public class Deserializer {
    private class WorldObjectHelper {
        public String type;
        public int x;
        public int y;
        public float m11;
        public float m12;
        public float m21;
        public float m22;

        public WorldObject ParseToWorldObject() {
            var object = new WorldObject();
            object.setType(type);
            object.setX(x);
            object.setY(y);
            var rotMatrix = new float[2][2];
            rotMatrix[0][0] = m11;
            rotMatrix[0][1] = m12;
            rotMatrix[1][0] = m21;
            rotMatrix[1][1] = m22;
            object.setRotationMatrix(rotMatrix);
            object.initObject();

            return object;
        }
    }

    private static String[] layerZeroElements = new String[]{"road", "roadsign", "garage", "parking"};
    private static String[] layerOneElements = new String[]{"man", "car", "bicycle", "roadsign", "bollard", "boundary", "tree"};

    private static void TestFile(String fileName) throws IllegalArgumentException {
        if (!fileName.contains("json")) {
            throw new IllegalArgumentException("Provided file's extension is not .json! \n File:" + fileName);
        }
    }

    private static String ReadFile(String fileName) {
        var content = new StringBuilder();
        try {
            var reader = new BufferedReader(new FileReader(ClassLoader.getSystemResource(fileName).getFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            return content.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static WorldObject AddLayeringWithStaticInfo(WorldObject object) {
        var objectType = object.getType();

        if (Stream.of(layerZeroElements).anyMatch(objectType::contains) && Stream.of(layerOneElements).noneMatch(objectType::contains)) {
            object.setIsStatic(true);
            object.setZ(0);
        } else {
            if (!objectType.contains("man")) {
                object.setIsStatic(true);
            }
        }

        return object;
    }

    public static World DeserializeWorldJson(String fileName) throws IllegalArgumentException {
        Type worldType = new TypeToken<World>() {
        }.getType();

        try {
            TestFile(fileName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        var file = ReadFile(fileName);
        if (file == null)
            return null;

        World worldComplete = new Gson().fromJson(file,worldType);
        worldComplete.initList();
        var raw = file.substring(file.indexOf("["), file.indexOf("]") + 1);
        Type listType = new TypeToken<ArrayList<WorldObjectHelper>>() {
        }.getType();
        List<WorldObjectHelper> unCompleteData = new Gson().fromJson(raw, listType);
        var completeData = new ArrayList<WorldObject>();
        for (WorldObjectHelper unCompleteObject :
                unCompleteData) {
            worldComplete.addObjectToWorld(AddLayeringWithStaticInfo(unCompleteObject.ParseToWorldObject()));
        }

        return worldComplete;
    }

    public static List<ReferencePoint> DeserializeReferencePointJson(String fileName) throws IllegalArgumentException {
        try {
            TestFile(fileName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        var file = ReadFile(fileName);
        if (file == null)
            return null;

        var raw = file.substring(file.indexOf("["), file.indexOf("]") + 1);
        Type listType = new TypeToken<ArrayList<ReferencePoint>>() {
        }.getType();
        List<ReferencePoint> completeData = new Gson().fromJson(file, listType);

        return completeData;
    }
}
