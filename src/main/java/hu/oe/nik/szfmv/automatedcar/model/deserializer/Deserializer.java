package hu.oe.nik.szfmv.automatedcar.model.deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Deserializer {
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

    private static WorldObject ReadRotationMatrixFromFileToObject(WorldObject object, String file) {
        var currentData = file.substring(file.indexOf("\"type\": \"" + object.getType())).split("},")[0].split(",");

        var matrix = new float[2][2];
        matrix[0][0] = Float.parseFloat(currentData[3].split("m11\":")[1]);
        matrix[0][1] = Float.parseFloat(currentData[4].split("m12\":")[1]);
        matrix[1][0] = Float.parseFloat(currentData[5].split("m21\":")[1]);
        matrix[1][1] = Float.parseFloat(currentData[6].split("m22\":")[1]);

        object.setRotationMatrix(matrix);

        return object;
    }

    private static WorldObject AddLayeringWithStaticInfo(WorldObject object) {
        if ((object.getType().contains("road") && !object.getType().contains("roadsign"))
                || object.getType().contains("garage")
                || (object.getType().contains("parking") && !object.getType().contains("roadsign"))) {
            object.setIsStatic(true);
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

    public static List<WorldObject> DeserializeJson(String fileName) throws IllegalArgumentException {
        try {
            TestFile(fileName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        var file = ReadFile(fileName);
        if (file == null)
            return null;

        var raw = file.substring(file.indexOf("["), file.indexOf("]") + 1);

        Type listType = new TypeToken<ArrayList<WorldObject>>() {
        }.getType();
        List<WorldObject> unCompleteData = new Gson().fromJson(raw, listType);

        var completeData = new ArrayList<WorldObject>();
        for (WorldObject completeObject :
                unCompleteData) {
            completeObject.initObject();
            completeData.add(AddLayeringWithStaticInfo(ReadRotationMatrixFromFileToObject(completeObject, raw)));
        }

        return completeData;
    }
}
