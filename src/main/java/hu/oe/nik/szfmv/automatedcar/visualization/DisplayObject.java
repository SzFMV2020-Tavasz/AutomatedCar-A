package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Class for handling the objects to be displayed.
 * The class calculates the position and rotation of the original word object
 * so it could keep its relative position to the egocar
 * regardless of the egocar's rotation and position.
 */
public class DisplayObject extends WorldObject {

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
    protected int[] tempRoad2Lane90RightPolygonXs =
        {176, 159, 143, 127, 111, 96, 81, 67, 54, 42, 30, 20, 11, 4, -3, -8, -11, -13, -14,
            -13, -11, -8, -3, 4, 11, 20, 30, 42, 54, 67, 81, 96, 111, 127, 143, 159};
    protected int[] tempRoad2Lane90RightPolygonYs =
        {-190, -189, -187, -184, -179, -172, -165, -156, -146, -134, -122, -109, -95, -80, -65, -49, -33, -17, 0,
            -17, -33, -49, -65, -80, -95, -109, -122, -134, -146, -156, -165, -172, -179, -184, -187, -189};

    protected Polygon tempRoad2Lane90RightPolygonInner =
        new Polygon(tempRoad2Lane90RightPolygonXs, tempRoad2Lane90RightPolygonYs, tempRoad2Lane90RightPolygonXs.length);
    protected ArrayList<Path2D> debugPolygons;

    /**
     * The constructor of the DisplayObject class.
     * @param worldObject the WorldObject which position should be calculated.
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
        createDebugPolygonList();

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
     * @return the distance X
     */
    public int getRefDifferenceX() {
        return this.refDifferenceX;
    }

    /**
     * Gets the y component of the segment line connecting the image's upper left corner
     * and the refrence point
     * @return the distance Y
     */
    public int getRefDifferenceY() {
        return this.refDifferenceY;
    }

    public ArrayList<Path2D> getDebugPolygons() {
        return debugPolygons;
    }

    /**
     * Initializing the polygon list
     *
     * It needs to be a list, because usually there are more then one polygons to draw for a WorldObject.
     */
    private void createDebugPolygonList() {
        debugPolygons = new ArrayList<>();
        Path2D poly = getTestPolygon();
        if (poly != null) {
            debugPolygons.add(poly);
        }
    }

    /**
     * Only works for the road_2lane_90right.png
     * @return the rotated and translated Path2D element
     */
    private Path2D getTestPolygon() {
        // only draw for the element it has polygons for
        if (this.getImageFileName().equals("road_2lane_90right.png")) {

            // Calculate the translation needed to move the automatedCar's reference point
            // to it's desired place
            Point2D carMovement =
                new Point2D.Double(VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_X - automatedCar.getX() ,
                    VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_Y - automatedCar.getY());

            // Calculate the translation needed to move the automatedCar's reference point
            // to it's desired place
            Point2D translation =
                new Point2D.Double(this.getX() - worldObject.getX(), this.getY() - worldObject.getY());

            // calculate the rotation between the image's original and target orientation
            double rotationAngle = VisualizationConfig.DISPLAY_EGOCAR_ROTATION -
                automatedCar.getRotation() + worldObject.getRotation();

            // rotate and translate the the polygon with the calculated rotation
            AffineTransform t = new AffineTransform();
            t.translate(this.getX(), this.getY());
            t.rotate(rotationAngle);
            Path2D rotated = (Path2D.Double)t.createTransformedShape(tempRoad2Lane90RightPolygonInner);

            return rotated;
        } else {
            return null;
        }
    }

    /**
     * This method calculates the position and rotation of the DisplayObject.
     *
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
                new Point2D.Double(VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_X - automatedCar.getX() ,
                         VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_Y - automatedCar.getY());

        // Calculate the distance between the automatedCar's and the displayObject's reference points
        // (a.k.a.the position of the outer rotation point relative to the displayeObject's reference point)
        Point2D refPointDistance = new Point2D.Double(automatedCar.getX() - worldObject.getX(),
                automatedCar.getY() - worldObject.getY());

        // Calculate the displayObject's referenrence point's rotation around the automatedCar's reference point
        AffineTransform  translateRefPoints = AffineTransform.getRotateInstance(rotationAngle ,
                refPointDistance.getX(), refPointDistance.getY());

        // Calculate the displayObject's reference point's displacement due to the difference between
        // the displayObject's reference point and the displayObject's image's rotation origo.
        Point2D refPoint = VisualizationConfig.getReferencePoint (this.imageFileName);
        AffineTransform fixRefPoint = AffineTransform.getRotateInstance(+ worldObject.getRotation()
                       + rotationAngle, refPoint.getX(), refPoint.getY());

        // set the class values according to the calculations
        this.x += (int)(Math.round(translateRefPoints.getTranslateX() + carMovement.getX()));
        this.y += (int)(Math.round(translateRefPoints.getTranslateY() + carMovement.getY()));

        this.rotation = (float)rotationAngle + worldObject.getRotation();

        this.rotationDisplacementX = (int)Math.round(fixRefPoint.getTranslateX());
        this.rotationDisplacementY = (int)Math.round(fixRefPoint.getTranslateY());

        this.refDifferenceX = (int)refPoint.getX();
        this.refDifferenceY = (int)refPoint.getY();
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
