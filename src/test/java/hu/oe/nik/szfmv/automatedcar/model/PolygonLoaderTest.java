package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.math.Coordinates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static hu.oe.nik.szfmv.automatedcar.model.PolygonLoader.values;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Polygon Loader Tests")
class PolygonLoaderTest {

    @Test
    @DisplayName("polygon coordinates load successfully")
    void load_polygon_coordinates_success() {
        ArrayList<Coordinates> coordinates = PolygonLoader.loadPolygonCoordinates("test-polygon");

        assertNotNull(coordinates);
        assertEquals(48, coordinates.size());
    }

    @Test
    @DisplayName("polygon loads as path successfully")
    void load_path_success() {
        Path2D.Double path = PolygonLoader.loadPolygonAsPath2D("test-polygon");

        assertNotNull(path);
        assertNotNull(path.getCurrentPoint());
    }

    @Test
    @DisplayName("polygon loads successfully")
    void load_polygon_success() {
        Polygon polygon = PolygonLoader.loadPolygon("test-polygon");

        assertNotNull(polygon);
        assertEquals(48, polygon.npoints);
        assertEquals(48, polygon.xpoints.length);
        assertEquals(48, polygon.ypoints.length);
    }

    @Nested
    @DisplayName("missing polygon fails")
    class TestsKeepLength {

        @Test
        @DisplayName("loading missing polygon fails")
        void load_missing_polygon_fails() {
            assertThrows(NoSuchElementException.class, () -> PolygonLoader.loadPolygon("non-existing"));
        }

        @Test
        @DisplayName("loading coordinates of missing polygon fails")
        void load_coordinates_of_missing_polygon_fails() {
            assertThrows(NoSuchElementException.class, () -> PolygonLoader.loadPolygonCoordinates("non-existing"));
        }

        @Test
        @DisplayName("loading missing polygon as path fails")
        void load_path_of_missing_polygon_fails() {
            assertThrows(NoSuchElementException.class, () -> PolygonLoader.loadPolygonAsPath2D("non-existing"));
        }

    }

    @Test
    @DisplayName("enum has no values")
    void no_values_of_enum() {
        assertEquals(0, values().length);
    }

}