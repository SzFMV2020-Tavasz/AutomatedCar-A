package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.math.Coordinates;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static hu.oe.nik.szfmv.automatedcar.model.PolygonLoader.values;
import static org.junit.jupiter.api.Assertions.*;

class PolygonLoaderTest {

    @Test
    void load_polygon_coordinates_success() {
        ArrayList<Coordinates> coordinates = PolygonLoader.loadPolygonCoordinates("test-polygon");

        assertNotNull(coordinates);
        assertEquals(48, coordinates.size());
    }

    @Test
    void load_path_success() {
        Path2D.Double path = PolygonLoader.loadPolygonAsPath2D("test-polygon");

        assertNotNull(path);
        assertNotNull(path.getCurrentPoint());
    }

    @Test
    void load_polygon_success() {
        Polygon polygon = PolygonLoader.loadPolygon("test-polygon");

        assertNotNull(polygon);
        assertEquals(48, polygon.npoints);
        assertEquals(48, polygon.xpoints.length);
        assertEquals(48, polygon.ypoints.length);
    }

    @Test
    void load_missing_polygon_fails() {
        assertThrows(NoSuchElementException.class, () -> PolygonLoader.loadPolygon("non-existing"));
    }

    @Test
    void load_coordinates_of_missing_polygon_fails() {
        assertThrows(NoSuchElementException.class, () -> PolygonLoader.loadPolygonCoordinates("non-existing"));
    }

    @Test
    void load_path_of_missing_polygon_fails() {
        assertThrows(NoSuchElementException.class, () -> PolygonLoader.loadPolygonAsPath2D("non-existing"));
    }

    @Test
    void no_values() {
        assertEquals(0, values().length);
    }
}