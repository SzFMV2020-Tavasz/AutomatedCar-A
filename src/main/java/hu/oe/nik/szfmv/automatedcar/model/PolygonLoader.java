package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.math.Coordinates;

import java.awt.*;
import java.awt.geom.Path2D;
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
 * Empty lines and those starting with symbol {@code #} are considered comment lines.</p>
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public enum PolygonLoader {;

    /**Attempts to find the polygon data by name and return its coordinates.
     * @throws NoSuchElementException in case there is no polygon by the given name.
     * @throws IllegalStateException in case some error occurred during reading.*/
    public static ArrayList<Coordinates> loadPolygonCoordinates(String name) {
        InputStream resourceData = PolygonLoader.class.getClassLoader().getResourceAsStream("polygons/" + name);
        if (resourceData == null) {
            throw new NoSuchElementException("Polygon not found by name: " + name);
        }

        try {
            return loadPolygonCoordinates(resourceData);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read data of polygon by name: " + name);
        }
    }

    /**Loads the coordinates from the given input stream and returns them on a list.
     * @throws IOException in case there was error during reading the data.*/
    public static ArrayList<Coordinates> loadPolygonCoordinates(InputStream resourceData) throws IOException {
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        CoordinateConsumer addToList = (x,y) -> coordinates.add(new Coordinates(x, y));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceData))) {

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                ++lineNumber;
                processPolygonFileLine(line, lineNumber, addToList);
            }
        }
        return coordinates;
    }

    /**Returned {@link Path2D.Double} is not closed. Call {@link Path2D.Double#closePath()} to close it if want.*/
    public static Path2D.Double loadPolygonAsPath2D(InputStream resourceData) throws IOException {
        Path2D.Double path = new Path2D.Double();

        CoordinateConsumer[] addToPath = { null };
        addToPath[0] = (x,y) -> {
            //first coordinate must be added with "move to", others with "line to"
            path.moveTo(x, y);
            addToPath[0] = path::lineTo;
        };

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceData))) {

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                ++lineNumber;
                processPolygonFileLine(line, lineNumber, addToPath[0]);
            }
        }
        return path;
    }

    /**Returned {@link Path2D.Double} is not closed. Call {@link Path2D.Double#closePath()} to close it if want.*/
    public static Path2D.Double loadPolygonAsPath2D(String name) {
        InputStream resourceData = PolygonLoader.class.getClassLoader().getResourceAsStream("polygons/" + name);
        if (resourceData == null) {
            throw new NoSuchElementException("Polygon not found by name: " + name);
        }

        try {
            return loadPolygonAsPath2D(resourceData);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read data of polygon by name: " + name);
        }
    }

    /**Attempts to find the polygon data by name and return it as a {@link Polygon java.awt.Polygon} object.
     * @throws NoSuchElementException in case there is no polygon by the given name.
     * @throws IllegalStateException in case some error occurred during reading.*/
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

    /**Loads the polygon from the given input stream and returns it as a {@link Polygon java.awt.Polygon} object.
     * @throws IOException in case there was error during reading the data.*/
    public static Polygon loadPolygon(InputStream resourceData) throws IOException {
        ArrayList<Coordinates> coordinates = loadPolygonCoordinates(resourceData);
        return new Polygon(
                toIntArray(coordinates, it -> (int)(float)it.x),
                toIntArray(coordinates, it -> (int)(float)it.y),
                coordinates.size());
    }

    private static void processPolygonFileLine(String line, int lineNumber, CoordinateConsumer use) {
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
            use.consume(x, y);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Illegal value for polygon point coordinate in line " + lineNumber + ": \"" + line + "\"");
        }
    }

    @FunctionalInterface
    private interface CoordinateConsumer {
        void consume(double x, double y);
    }

}
