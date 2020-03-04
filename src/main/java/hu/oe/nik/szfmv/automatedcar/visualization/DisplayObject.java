package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;

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
     * Original worldObject stored for accessing its values
     */
    private WorldObject worldObject;
    /**
     * original automatedCar stored for accessing its values
     */
    private AutomatedCar automatedCar;

    /**
     * The constructor of the DisplayObject class.
     * @param worldObject the WorldObject which position should be calculated.
     * @param automatedCar The AutomatedCar (egocar) that the DisplayObject should keep its relative position to.
     */
    public DisplayObject(final WorldObject worldObject, final AutomatedCar automatedCar) {
        super(worldObject.getX(), worldObject.getY(), worldObject.getImageFileName());
        this.worldObject = worldObject;
        this.automatedCar = automatedCar;
        this.rotation = worldObject.getRotation();
        calculatePosition();
    }

    public int getRotationDisplacementX() {
        return rotationDisplacementX;
    }
    public int getRotationDisplacementY() {
        return rotationDisplacementY;
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
        AffineTransform  translateRefPoints = AffineTransform.getRotateInstance(rotationAngle,
                refPointDistance.getX(), refPointDistance.getY());

        // Calculate the displayObject's reference point's displacement due to the difference between
        // the displayObject's reference point and the displayObject's image's rotation origo.
        AffineTransform fixRefPoint = AffineTransform.getRotateInstance(rotationAngle,
                VisualizationConfig.DISPLAY_RIGHT90_REF_POINT_X,
                VisualizationConfig.DISPLAY_RIGHT90_REF_POINT_Y);

        // set the class values according to the calculations
        this.x += (int)Math.round(translateRefPoints.getTranslateX() + carMovement.getX());
        this.y += (int)Math.round(translateRefPoints.getTranslateY() + carMovement.getY());

        this.rotation = (float)rotationAngle;

        this.rotationDisplacementX = (int)Math.round(fixRefPoint.getTranslateX());
        this.rotationDisplacementY = (int)Math.round(fixRefPoint.getTranslateY());
    }
}
