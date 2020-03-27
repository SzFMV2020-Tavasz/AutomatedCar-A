package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

class DisplaySensorObject {

    protected AutomatedCar automatedCar;
    protected Point2D source;
    protected Point2D corner1;
    protected Point2D corner2;
    protected Color sensorColor;

    /**
     * Class for holding the sensor triangle data for display.
     *
     * @param automatedCar
     */
    DisplaySensorObject(AutomatedCar automatedCar) {
        this.automatedCar = automatedCar;
    }

    /**
     * @param source
     * @param corner1
     * @param corner2
     */
    void setSensorTriangle(Point2D source, Point2D corner1, Point2D corner2) {
        this.source = source;
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    /**
     * gets the pre-set color of the sensor triangle fill
     *
     * @return the color that was pre-set
     */
    Color getSensorColor() {
        return sensorColor;
    }

    /**
     * Sets the desired color of the sensor triangle
     *
     * @param sensorColor the color to use filling the sensor triangle
     */
    void setSensorColor(Color sensorColor) {
        this.sensorColor = sensorColor;
    }

    Polygon getSensorTriangle() {
        // nullcheck
        if (source != null) {
            Polygon sensorTriangle = new Polygon();
            sensorTriangle.addPoint((int) source.getX(), (int) source.getY());
            sensorTriangle.addPoint((int) corner1.getX(), (int) corner1.getY());
            sensorTriangle.addPoint((int) corner2.getX(), (int) corner2.getY());
            return sensorTriangle;
        } else {
            return null;
        }
    }

    /**
     * Creates the center line of the sensor triangle
     *
     * @return the rotated and translated shape of the sensor triangle's center line
     */
    Shape getSensorCenterLine() {
        // nullcheck
        if (source != null) {
            Polygon centerLine = new Polygon();
            Point2D midPoint = getMidPoint(corner1, corner2);
            centerLine.addPoint((int) source.getX(), (int) source.getY());
            centerLine.addPoint((int) midPoint.getX(), (int) midPoint.getY());
            return DisplayTransformation.repositionPolygon(0, 0, 0, centerLine, automatedCar);
        } else {
            return null;
        }
    }

    /**
     * Creates the display Path of the sensor triangle
     *
     * @return the rotated and translated Path2D of the sensor triangle
     */
    Path2D getDisplaySensorTriangle() {
        if (source != null) {
            Polygon sensorTriangle = getSensorTriangle();
            return DisplayTransformation.repositionPolygon(0, 0, 0, sensorTriangle, automatedCar);
        } else {
            return null;
        }
    }

    /**
     * Calculates the midpoint between two points
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the midpoint
     */
    protected Point2D getMidPoint(Point2D p1, Point2D p2) {
        return new Point2D.Double((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
    }
}
