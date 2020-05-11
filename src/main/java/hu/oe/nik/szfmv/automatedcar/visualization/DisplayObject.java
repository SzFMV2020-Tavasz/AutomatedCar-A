package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.sensors.ObjectTransform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

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
    protected ArrayList<Path2D> debugPolygons;

    protected DisplayImageData displayImageData;

    /**
     * The constructor of the DisplayObject class.
     *
     * @param worldObject  the WorldObject which position should be calculated.
     * @param automatedCar The AutomatedCar (egocar) that the DisplayObject should keep its relative position to.
     */
    public DisplayObject(final WorldObject worldObject, final AutomatedCar automatedCar) {
        super();
        this.x = worldObject.getX();
        this.y = worldObject.getY();
        if (worldObject.getImageFileName() == null) {
            this.imageFileName = worldObject.getType() + ".png";
            worldObject.setImageFileName(this.imageFileName);
        } else {
            this.imageFileName = worldObject.getImageFileName();
        }
        this.image = worldObject.getImage();
        this.id = worldObject.getId();
        this.worldObject = worldObject;
        this.automatedCar = automatedCar;
        if (worldObject.getRotationMatrix() != null) {
            worldObject.setRotation(VisualizationConfig.getAngleFromRotationMatrix(worldObject.getRotationMatrix()));
        }
        this.z = worldObject.getZ();
        calculatePosition();
        loadDebugPolygon();
    }

    /**
     * Gets the rotated and translated debugpolygon
     *
     * @return Path2d object representint the debugpolygon in its intended place
     */
    public ArrayList<Path2D> getDebugPolygons() {
        return debugPolygons;
    }

    /**
     * This method calculates the position and rotation of the DisplayObject's debug polygon.
     * <p>
     * Called by the constructor
     */
    private void loadDebugPolygon() {

        ArrayList<Path2D> origPolys = worldObject.getPolygons();
        ArrayList<Path2D> debugPolys = new ArrayList<>();
        for (Path2D origPoly : origPolys) {
            if (origPoly != null) {
                // this is a just-in-case transformation to make up for polygon and image reference point difference
                // so even if it could be added to the next transformation, it is better to be kept separate
                Point2D refPoint = VisualizationConfig.getReferencePoint(this.imageFileName);
                // if we don't clone the polygon, the fix object's debug polygon will run away.
                Path2D tempPoly = ObjectTransform.translatePath2D(origPoly, -refPoint.getX(), -refPoint.getY());

                Path2D poly = DisplayTransformation.repositionPath2D(worldObject.getX(), worldObject.getY(),
                    (float) worldObject.getRotation(), tempPoly, automatedCar);
                if (poly != null) {
                    debugPolys.add(poly);
                }
            }
        }
        this.debugPolygons = debugPolys;
    }

    /**
     * Gets the DisplayImageData of the rotated and translated DisplayObject in one go
     *
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

}
