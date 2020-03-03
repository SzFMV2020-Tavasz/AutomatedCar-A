package hu.oe.nik.szfmv.automatedcar.model.deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Deserializer {
    private static  void TestFile(String fileName) throws IllegalArgumentException {
        if (!fileName.contains("json")) {
            throw new IllegalArgumentException("Provided file's extension is not .json! \n File:" + fileName);
        }
    }

    public static List<WorldObjectDes> DeserializeJson(String fileName) throws IllegalArgumentException {
        try {
            TestFile(fileName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        var content = new StringBuilder();
        try {
            var reader = new BufferedReader(new FileReader(ClassLoader.getSystemResource(fileName).getFile()));
            String line;
            while((line = reader.readLine() )!= null) {
                content.append(line);
            }
        } catch (Exception e) {
            return null;
        }

        var json = content.toString();
        var raw = json.substring(json.indexOf("["), json.indexOf("]") + 1);

        Type listType = new TypeToken<ArrayList<WorldObjectDes>>(){}.getType();
        List<WorldObjectDes> unCompleteData = new Gson().fromJson(raw, listType);

        var completeData = new ArrayList<WorldObjectDes>();
        for (WorldObjectDes completeObject:
             unCompleteData) {
            completeObject.rotationMatrix = new float[2][2];
            var currentData = raw.substring(raw.indexOf("\"type\": \"" + completeObject.type)).split("},")[0].split(",");
            completeObject.rotationMatrix[0][0] = Float.parseFloat(currentData[3].split("m11\":")[1]);
            completeObject.rotationMatrix[0][1] = Float.parseFloat(currentData[4].split("m12\":")[1]);
            completeObject.rotationMatrix[1][0] = Float.parseFloat(currentData[5].split("m21\":")[1]);
            completeObject.rotationMatrix[1][1] = Float.parseFloat(currentData[6].split("m22\":")[1]);

            if ((completeObject.type.contains("road") && !completeObject.type.contains("roadsign"))
                    || completeObject.type.contains("garage")
                    || (completeObject.type.contains("parking") && !completeObject.type.contains("roadsign"))) {
                completeObject.isStatic = true;
            }

            if (completeObject.type.contains("man")
                    || completeObject.type.contains("car")
                    || completeObject.type.contains("bicycle")
                    || completeObject.type.contains("bollard")
                    || completeObject.type.contains("boundary")
                    || completeObject.type.contains("tree")
                    || completeObject.type.contains("roadsign")){
                completeObject.z = 1;

                if (!completeObject.type.contains("man")) {
                    completeObject.isStatic = true;
                }
            }

            completeData.add(completeObject);
        }

        return completeData;
    }
}
