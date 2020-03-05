package hu.oe.nik.szfmv.automatedcar.model.deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

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

    private static WorldObjectDes ReadRotationMatrixFromFileToObject(WorldObjectDes object, String file) {
        object.rotationMatrix = new float[2][2];
        var currentData = file.substring(file.indexOf("\"type\": \"" + object.type)).split("},")[0].split(",");
        object.rotationMatrix[0][0] = Float.parseFloat(currentData[3].split("m11\":")[1]);
        object.rotationMatrix[0][1] = Float.parseFloat(currentData[4].split("m12\":")[1]);
        object.rotationMatrix[1][0] = Float.parseFloat(currentData[5].split("m21\":")[1]);
        object.rotationMatrix[1][1] = Float.parseFloat(currentData[6].split("m22\":")[1]);

        return object;
    }

    private static WorldObjectDes AddLayeringWithStaticInfo(WorldObjectDes object) {
        if ((object.type.contains("road") && !object.type.contains("roadsign"))
                || object.type.contains("garage")
                || (object.type.contains("parking") && !object.type.contains("roadsign"))) {
            object.isStatic = true;
        }

        if (object.type.contains("man")
                || object.type.contains("car")
                || object.type.contains("bicycle")
                || object.type.contains("bollard")
                || object.type.contains("boundary")
                || object.type.contains("tree")
                || object.type.contains("roadsign")) {
            object.z = 1;

            if (!object.type.contains("man")) {
                object.isStatic = true;
            }
        }
        return object;
    }

    public static List<WorldObjectDes> DeserializeJson(String fileName) throws IllegalArgumentException {
        try {
            TestFile(fileName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        var file = ReadFile(fileName);
        if (file == null)
            return null;

        var raw = file.substring(file.indexOf("["), file.indexOf("]") + 1);

        Type listType = new TypeToken<ArrayList<WorldObjectDes>>() {
        }.getType();
        List<WorldObjectDes> unCompleteData = new Gson().fromJson(raw, listType);

        var completeData = new ArrayList<WorldObjectDes>();
        for (WorldObjectDes completeObject :
                unCompleteData) {

            completeData.add(AddLayeringWithStaticInfo(ReadRotationMatrixFromFileToObject(completeObject, raw)));
        }

        return completeData;
    }
}
