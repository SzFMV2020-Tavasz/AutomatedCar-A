package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.math.Coordinates;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static hu.oe.nik.szfmv.automatedcar.util.ArrayUtils.toIntArray;
import static java.lang.Double.parseDouble;

/**Provides the functionality to load polygons from files.
 * <p>Input data is expected to contain lines, each containing one {@code x} and one {@code y} coordinate
 * separated by a comma and optional whitespaces.
 * Empty lines and those starting with symbol {@code #} are considered comments lines.</p>
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public enum PolygonLoader {;

    public static Polygon loadPolygon(String name) {
        InputStream resourceData = PolygonLoader.class.getClassLoader().getResourceAsStream("polygons/" + name);
        if (resourceData == null) {
            throw new NoSuchElementException("Polygon not found by name: " + name);
        }

        try {
            return loadPolygon(resourceData);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read data of polygon by name: " + name);
        }
    }

    public static Polygon loadPolygon(InputStream resourceData) throws IOException {
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceData))) {

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                ++lineNumber;
                processPolygonFileLine(coordinates, line, lineNumber);
            }
        }

        return new Polygon(
                toIntArray(coordinates, it -> (int)(float)it.x),
                toIntArray(coordinates, it -> (int)(float)it.y),
                coordinates.size());
    }

    private static void processPolygonFileLine(ArrayList<Coordinates> coordinates, String line, int lineNumber) {
        line = line.trim();
        if (line.isBlank() || line.startsWith("#")) {
            return;
        }

        int commaIndex = line.indexOf(",");
        if (commaIndex <= 0) {
            throw new IllegalArgumentException("Illegal syntax for polygon point in line " + lineNumber + ": \"" + line + "\"");
        }

        try {
            double x = parseDouble(line.substring(0, commaIndex).trim());
            double y = parseDouble(line.substring(commaIndex + 1).trim());
            coordinates.add(new Coordinates(x, y));
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Illegal value for polygon point coordinate in line " + lineNumber + ": \"" + line + "\"");
        }
    }

}
