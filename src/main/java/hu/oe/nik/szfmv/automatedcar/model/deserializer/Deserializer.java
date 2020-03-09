package hu.oe.nik.szfmv.automatedcar.model.deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import hu.oe.nik.szfmv.automatedcar.model.ReferencePoint;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

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
        if ((object.getType().contains("road") && !object.getType().contains("roadsign"))
                || object.getType().contains("garage")
                || (object.getType().contains("parking") && !object.getType().contains("roadsign"))) {
            object.setIsStatic(true);
            object.setZ(0);
        }

        if (object.getType().contains("man")
                || object.getType().contains("car")
                || object.getType().contains("bicycle")
                || object.getType().contains("bollard")
                || object.getType().contains("boundary")
                || object.getType().contains("tree")
                || object.getType().contains("roadsign")) {
            object.setZ(1);

            if (!object.getType().contains("man")) {
                object.setIsStatic(true);
            }
        }
        return object;
    }

    public static List<WorldObject> DeserializeWorldJson(String fileName) throws IllegalArgumentException {
        try {
            TestFile(fileName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        var file = ReadFile(fileName);
        if (file == null)
            return null;

        var raw = file.substring(file.indexOf("["), file.indexOf("]") + 1);

        Type listType = new TypeToken<ArrayList<WorldObjectHelper>>() {
        }.getType();
        List<WorldObjectHelper> unCompleteData = new Gson().fromJson(raw, listType);

        var completeData = new ArrayList<WorldObject>();
        for (WorldObjectHelper unCompleteObject :
                unCompleteData) {
            completeData.add(AddLayeringWithStaticInfo(unCompleteObject.ParseToWorldObject()));
        }

        return completeData;
    }

    public static List<ReferencePoint> DeserializeReferenccePointJson(String fileName) throws IllegalArgumentException {
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
        List<ReferencePoint> completeData = new Gson().fromJson(raw, listType);

        return completeData;
    }
}
