package hu.oe.nik.szfmv.automatedcar.model.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hu.oe.nik.szfmv.automatedcar.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.Path2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Helper class to load the debug polygons for the WorldObjects
 */
public final class ReadPolygons {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static Object obj;
    private static String filename;

    private ReadPolygons() {
    }



    private static void readPolys() {
        if (obj == null) {
            try {
                obj = new JsonParser().parse(new FileReader(
                    ClassLoader.getSystemResource("debug_polygons.json").getFile()));
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            }
        }
    }

    /**
     * Creates the debug polygons for the requested type from the json data file
     * containing the debug poligons for each WorldObject types.
     * @param type The type of WorldObject to create the polygons for
     * @return A list of {@link Path2D} type "polygons"
     */
    public static ArrayList<Path2D> getPolygonsForObject(String type) {
        readPolys();
        ArrayList<Path2D> returnList = new ArrayList<>();
        JsonArray elementsJsonArray = (JsonArray) obj;
        for (int i = 0; i < elementsJsonArray.size(); i++) {
            JsonObject oneElementJsonObj = (JsonObject) elementsJsonArray.get(i);
            String typename = oneElementJsonObj.get("typename").getAsString();
            if (typename.equals(type)) {
                returnList = getPolygonsFromJsonElement(oneElementJsonObj);
            }
        }
        return returnList;
    }

    /**
     * Gets the WorldObject's polygon data from the json object of one element
     *
     * @return A list of Path2D typed polygons
     */
    private static ArrayList<Path2D> getPolygonsFromJsonElement(JsonObject elementJsonObject) {
        ArrayList<Path2D> returnList = new ArrayList<>();
        // get the part with the polygons
        JsonArray polygonsJsonArray = (JsonArray) elementJsonObject.get("polys");
        for (int n = 0; n < polygonsJsonArray.size(); n++) {
            JsonObject polygonJsonObject = (JsonObject) polygonsJsonArray.get(n);
            JsonArray polygonPointsJsonArray = (JsonArray) polygonJsonObject.get("points");
            Path2D tempPath = new Path2D.Double(Path2D.WIND_NON_ZERO, polygonPointsJsonArray.size());
            for (int k = 0; k < polygonPointsJsonArray.size(); k++) {
                JsonArray tempPoint = (JsonArray) polygonPointsJsonArray.get(k);
                if (k == 0) {
                    tempPath.moveTo(tempPoint.get(0).getAsInt(), tempPoint.get(1).getAsInt());
                } else {
                    tempPath.lineTo(tempPoint.get(0).getAsInt(), tempPoint.get(1).getAsInt());
                }
            }
            returnList.add(tempPath);
        }
        return returnList;
    }

}

