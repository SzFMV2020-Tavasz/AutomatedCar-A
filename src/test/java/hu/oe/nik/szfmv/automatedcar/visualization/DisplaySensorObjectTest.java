package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.CarVariant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisplaySensorObjectTest {
    DisplaySensorObject displaySensorObject;
    AutomatedCar automatedCar;

    @BeforeEach
    public void init() {
        automatedCar = new AutomatedCar(292, 230, CarVariant.RED_2);
        automatedCar.setRotation((float)-Math.PI / 4);
        displaySensorObject = new DisplaySensorObject(automatedCar);
    }

    /**
     * Check whether the class gets instatniatied when new DispLayobjct() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(displaySensorObject);
    }

    @Nested
    @DisplayName("Tests for actual values")
    class TestNotNulls {
        @BeforeEach
        public void notNullInit() {

            displaySensorObject.setSensorTriangle(
                new Point2D.Double(10, 10),
                new Point2D.Double(20, 10),
                new Point2D.Double(10, 20));
        }


        /**
         * Checks weather the passed points are stored
         */
        @Test
        public void pointGetsPassed() {
            Polygon sT = displaySensorObject.getSensorTriangle();
            PathIterator it = sT.getPathIterator(null);
            float[] sP = new float[2];
            it.currentSegment(sP);
            assertEquals(10, sP[0]);
            assertEquals(10, sP[1]);
            it.next();
            it.currentSegment(sP);
            assertEquals(20, sP[0]);
            assertEquals(10, sP[1]);
            it.next();
            it.currentSegment(sP);
            assertEquals(10, sP[0]);
            assertEquals(20, sP[1]);
        }

        /**
         * Checks whether the display triangle is returned right
         */
        @Test
        public void displayPointsReturned() {
            Path2D dst = displaySensorObject.getDisplaySensorTriangle();
            PathIterator it = dst.getPathIterator(null);
            float[] sP = new float[2];
            it.currentSegment(sP);
            assertEquals(341, (int)sP[0]);
            assertEquals(-4, (int)sP[1]);
            it.next();
            it.currentSegment(sP);
            assertEquals(348, (int)sP[0]);
            assertEquals(2, (int)sP[1]);
            it.next();
            it.currentSegment(sP);
            assertEquals(333, (int)sP[0]);
            assertEquals(2, (int)sP[1]);
        }

        /**
         * Checks whether the sensor centerline is returned right
         */
        @Test
        public void centerLineReturned() {
            Shape returned = displaySensorObject.getSensorCenterLine();
            PathIterator it = returned.getPathIterator(null);
            float[] sP = new float[2];
            it.currentSegment(sP);
            assertEquals(341, (int)sP[0]);
            assertEquals(-4, (int)sP[1]);
            it.next();
            it.currentSegment(sP);
            assertEquals(341, (int)sP[0]);
            assertEquals(2, (int)sP[1]);
        }

    }

    @Nested
    @DisplayName("Tests for null returns")
    class TestNulls {
        /**
         * Checks wether the passed points are stored
         */
        @Test
        public void noPointPassed() {
            Polygon sT = displaySensorObject.getSensorTriangle();

            assertNull(sT);
        }

        /**
         * Checks whether null is returned when getDisplaySensorTriangle called with no Triangle Polygon
         */
        @Test
        public void noDisplayPoints() {
            Path2D returned = displaySensorObject.getDisplaySensorTriangle();
            assertNull(returned);
        }

        /**
         * Checks whether null is returned when getSensorCenterLine called
         * without previously setting the polygon points.
         */
        @Test
        public void noDisplaySensorLine() {
            Shape returned = displaySensorObject.getSensorCenterLine();
            assertNull(returned);
        }
    }

    /**
     * Checks whether the midpoint is calculated right
     */
    @Test
    public void checkMidPoint() {
        Point2D p1 = new Point2D.Double(10, 10.5);
        Point2D p2 = new Point2D.Double(-11, 20);
        Point2D p3 = displaySensorObject.getMidPoint(p1, p2);
        assertEquals(-0.5, p3.getX());
        assertEquals(15.25, p3.getY());
    }

    /**
     * Checks whether the color is stored and retrieved right
     */
    @Test
    public void colorStored() {
        Color testColor = new Color(10, 20, 255);
        displaySensorObject.setSensorColor(testColor);
        assertEquals(10, displaySensorObject.getSensorColor().getRed());
        assertEquals(20, displaySensorObject.getSensorColor().getGreen());
        assertEquals(255, displaySensorObject.getSensorColor().getBlue());
    }
}
