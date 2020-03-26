package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Class for handling the objects to be displayed.
 * The class calculates the position and rotation of the original word object
 * so it could keep its relative position to the egocar
 * regardless of the egocar's rotation and position.
 */
public class DisplayObject extends WorldObject {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    /**
     * Original worldObject stored for accessing its values
     */
    protected WorldObject worldObject;
    /**
     * original automatedCar stored for accessing its values
     */
    protected AutomatedCar automatedCar;

    /**
     * Debug polygon of the DisplayObject
     */
    protected Path2D debugPolygon;

    protected DisplayImageData displayImageData;

    /**
     * The constructor of the DisplayObject class.
     *
     * @param worldObject  the WorldObject which position should be calculated.
     * @param automatedCar The AutomatedCar (egocar) that the DisplayObject should keep its relative position to.
     */
    public DisplayObject(final WorldObject worldObject, final AutomatedCar automatedCar) {
        super(worldObject.getX(), worldObject.getY(),
            worldObject.getImageFileName() == null ? worldObject.getType() + ".png" : worldObject.getImageFileName());
        this.id = worldObject.getId();
        this.worldObject = worldObject;
        this.automatedCar = automatedCar;
        if (worldObject.getRotationMatrix() != null) {
            worldObject.setRotation(getAngleFromRotationMatrix(worldObject.getRotationMatrix()));
        }
        this.z = worldObject.getZ();
        calculatePosition();
        loadDebugPolygon();
    }

    /**
     * Gets the rotated and translated debugpolygon
     * @return Path2d object representint the debugpolygon in its intended place
     */
    public Path2D getDebugPolygon() {
        return debugPolygon;
    }

    /**
     * This method calculates the position and rotation of the DisplayObject's debug polygon.
     * <p>
     * Called by the constructor
     */
    private void loadDebugPolygon() {

        Polygon origPoly = worldObject.getPolygon();
        if (origPoly != null) {
            // translation of polygon may not be needed later.
            // this is a just-in-case transformation to make up for polygon and image reference point difference
            // so even if it could be added to the next transformation, it is better to be kept separate
            Point2D refPoint = VisualizationConfig.getReferencePoint(this.imageFileName);
            // if we don't clone the polygon, the fix object's debug polygon will run away.
            Polygon tempPoly = new Polygon(origPoly.xpoints, origPoly.ypoints, origPoly.npoints);
            tempPoly.translate((int) -refPoint.getX(), (int) -refPoint.getY());

            Path2D poly = DisplayTransformation.repositionPolygon(worldObject.getX(), worldObject.getY(),
                (float) worldObject.getRotation(), tempPoly, automatedCar);
            if (poly != null) {
                debugPolygon = poly;
            }
        }
    }

    /**
     * Gets the DisplayImageData of the rotated and translated DisplayObject in one go
     * @return the {@link DisplayImageData} object holding the display information
     */
    DisplayImageData getDisplayImageData() {
        return displayImageData;
    }

    /**
     * This method calculates the position and rotation of the DisplayObject.
     * <p>
     * Called by the constructor
     */
    private void calculatePosition() {
        displayImageData = DisplayTransformation.repositionImage(worldObject.getX(), worldObject.getY(),
            worldObject.getRotation(), worldObject.getImageFileName(), automatedCar);
    }

    private float getAngleFromRotationMatrix(float[][] matrix) {
        double angle1 = Math.acos(matrix[0][0]);
        float sinValue = matrix[1][0];
        double angleFinal;

        if (sinValue >= 0) {
            angleFinal = angle1;
        } else {
            angleFinal = Math.PI * 2 - angle1;
        }
        return (float) -angleFinal;
    }
}
