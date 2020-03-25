package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Method;

/**
 * Class for handling the objects to be displayed.
 * The class calculates the position and rotation of the original word object
 * so it could keep its relative position to the egocar
 * regardless of the egocar's rotation and position.
 */
public class DisplayObject extends WorldObject {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    /**
     * The translation values needed for fixing the position difference caused by the difference between
     * the displayObject's reference point and the displayObject's image's rotation origo.
     */
    protected int rotationDisplacementX;
    protected int rotationDisplacementY;

    /**
     * The translation needed to move from the image upper left corner to the image reference point
     */
    protected int refDifferenceX;
    protected int refDifferenceY;

    /**
     * Original worldObject stored for accessing its values
     */
    protected WorldObject worldObject;
    /**
     * original automatedCar stored for accessing its values
     */
    protected AutomatedCar automatedCar;

    /**
     * temporary data for testing polylines
     */

    protected Path2D debugPolygon;

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

    public int getRotationDisplacementX() {
        return this.rotationDisplacementX;
    }

    public int getRotationDisplacementY() {
        return this.rotationDisplacementY;
    }

    /**
     * Gets the x component of the segment line connecting the image's upper left corner
     * and the refrence point
     *
     * @return the distance X
     */
    public int getRefDifferenceX() {
        return this.refDifferenceX;
    }

    /**
     * Gets the y component of the segment line connecting the image's upper left corner
     * and the refrence point
     *
     * @return the distance Y
     */
    public int getRefDifferenceY() {
        return this.refDifferenceY;
    }

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
     * This method calculates the position and rotation of the DisplayObject.
     * <p>
     * Called by the constructor
     */
    private void calculatePosition() {
        // Move the displayObject to the point where it would be
        // if the automatedCar would be at the center of the screen

        // Calculate the rotation needed to change the automatedCar's orientation
        // to the desired orientation
        double rotationAngle = VisualizationConfig.DISPLAY_EGOCAR_ROTATION - automatedCar.getRotation();

        // Calculate the translation needed to move the automatedCar's reference point
        // to it's desired place
        Point2D carMovement =
            new Point2D.Double(VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_X - automatedCar.getX(),
                VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_Y - automatedCar.getY());

        // Calculate the distance between the automatedCar's and the displayObject's reference points
        // (a.k.a.the position of the outer rotation point relative to the displayeObject's reference point)
        Point2D refPointDistance = new Point2D.Double(automatedCar.getX() - worldObject.getX(),
            automatedCar.getY() - worldObject.getY());

        // Calculate the displayObject's referenrence point's rotation around the automatedCar's reference point
        AffineTransform translateRefPoints = AffineTransform.getRotateInstance(rotationAngle,
            refPointDistance.getX(), refPointDistance.getY());

        // Calculate the displayObject's reference point's displacement due to the difference between
        // the displayObject's reference point and the displayObject's image's rotation origo.
        Point2D refPoint = VisualizationConfig.getReferencePoint(this.imageFileName);
        AffineTransform fixRefPoint = AffineTransform.getRotateInstance(+worldObject.getRotation()
            + rotationAngle, refPoint.getX(), refPoint.getY());

        // set the class values according to the calculations
        this.x += (int) (Math.round(translateRefPoints.getTranslateX() + carMovement.getX()));
        this.y += (int) (Math.round(translateRefPoints.getTranslateY() + carMovement.getY()));

        this.rotation = (float) rotationAngle + worldObject.getRotation();

        this.rotationDisplacementX = (int) Math.round(fixRefPoint.getTranslateX());
        this.rotationDisplacementY = (int) Math.round(fixRefPoint.getTranslateY());

        this.refDifferenceX = (int) refPoint.getX();
        this.refDifferenceY = (int) refPoint.getY();
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
