package hu.oe.nik.szfmv.automatedcar.model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static hu.oe.nik.szfmv.automatedcar.util.ArrayUtils.toIntArray;
import static java.lang.Float.parseFloat;

public enum PolygonLoader {;

    public static Polygon loadPolygon(String name) {
        InputStream resourceData = PolygonLoader.class.getClassLoader().getResourceAsStream("polygons/" + name);
        if (resourceData == null) {
            throw new NoSuchElementException("Polygon not found by name: " + name);
        }

        ArrayList<Float> xCoordinateList = new ArrayList<>();
        ArrayList<Float> yCoordinateList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceData))) {

            String line;
            int commaIndex;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                ++lineNumber;
                line = line.trim();
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }

                commaIndex = line.indexOf(",");
                if (commaIndex <= 0) {
                    throw new IllegalArgumentException("Illegal syntax for polygon point in line " + lineNumber + ": \"" + line + "\"");
                }

                try {
                    xCoordinateList.add(parseFloat(line.substring(0, commaIndex).trim()));
                    yCoordinateList.add(parseFloat(line.substring(commaIndex + 1).trim()));
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("Illegal value for polygon point coordinate in line " + lineNumber + ": \"" + line + "\"");
                }
            }
        } catch (IOException e) {
            throw new NoSuchElementException("Could not open existing resource for polygon by name: " + name);
        }

        assert xCoordinateList.size() == yCoordinateList.size();

        return new Polygon(
                toIntArray(xCoordinateList, it -> (int)(float)it),
                toIntArray(yCoordinateList, it -> (int)(float)it),
                xCoordinateList.size());
    }

}
