package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public final class DisplayTransformation {

    /**
     * Constructor for static class
     * <p>
     * it does not need to be instantiated.
     * </p>
     */
    private DisplayTransformation() {

    }

    /**
     * Method calculating the position and rotation of the DisplayObject.
     * <p>
     * Position and rotation are defined by the egocar's real world position;
     * the image must keep its relative position to the egocar translated and rotated to its display position.
     * </p>
     *
     * @param x             the x component of the position if the image's reference point
     * @param y             the y component of the position if the image's reference point
     * @param rotation      The rotation of the image
     * @param imageFileName The name of the image to rotate and translate
     * @param automatedCar  The egocar whose position and rotation defines the return data
     * @return {@link DisplayImageData} object containing the calculated transformation results
     */
    public static DisplayImageData repositionImage(int x, int y, float rotation,
                                                   String imageFileName, AutomatedCar automatedCar) {

        // Calculate the rotation needed to change the automatedCar's orientation
        // to the desired orientation
        double rotationAngle = VisualizationConfig.DISPLAY_EGOCAR_ROTATION - automatedCar.getRotation() ;

        // Calculate the translation needed to move the automatedCar's reference point
        // to it's desired place
        Point2D carMovement =
            new Point2D.Double(VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_X - automatedCar.getX(),
                VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_Y - automatedCar.getY());

        // Calculate the distance between the automatedCar's and the displayObject's reference points
        // (a.k.a.the position of the outer rotation point relative to the displayObject's reference point)
        Point2D refPointDistance = new Point2D.Double(automatedCar.getX() - x,
            automatedCar.getY() - y);

        // Calculate the displayObject's reference point's rotation around the automatedCar's reference point
        AffineTransform translateRefPoints = AffineTransform.getRotateInstance(rotationAngle,
            refPointDistance.getX(), refPointDistance.getY());

        // Calculate the displayObject's reference point's displacement due to the difference between
        // the displayObject's reference point and the displayObject's image's rotation origo.
        Point2D refPoint = VisualizationConfig.getReferencePoint(imageFileName);
        AffineTransform fixRefPoint = AffineTransform.getRotateInstance(+rotation
            + rotationAngle, refPoint.getX(), refPoint.getY());

        // set temp values according to the calculations
        int didX = x + (int) (Math.round(translateRefPoints.getTranslateX() + carMovement.getX()));
        int didY = y + (int) (Math.round(translateRefPoints.getTranslateY() + carMovement.getY()));

        int rotDisplacementX = (int) Math.round(fixRefPoint.getTranslateX());
        int rotDisplacementY = (int) Math.round(fixRefPoint.getTranslateY());

        // create the DisplayImageData to hold the calculated data
        DisplayImageData did = new DisplayImageData(didX, didY, (float) rotationAngle + rotation,
            (int) refPoint.getX(), (int) refPoint.getY(), rotDisplacementX, rotDisplacementY);
        return did;
    }

    /**
     * Rotates and repositions a polygon.
     * <p>
     * Position and rotation are defined by the egocar's real world position;
     * the polygon must keep its relative position to the egocar translated and rotated to its display position.
     * </p>
     *
     * @param x        the x component of the reference point (0,0) of the polygon
     * @param y        the y component of the reference point (0,0) of the polygon
     * @param rotation     the rotation of the polygon (sum of inherited and calculated)
     * @param automatedCar  The egocar whose position and rotation defines the return data
     * @param polygon      the polygon to rotate
     * @return A {@link Path2D} object containing the rotated polygon data
     */
    public static Path2D repositionPolygon(int x, int y,  float rotation,
                                           Polygon polygon, AutomatedCar automatedCar) {

        // Calculate the rotation needed to change the automatedCar's orientation
        // to the desired orientation
        double rotationAngle = VisualizationConfig.DISPLAY_EGOCAR_ROTATION - automatedCar.getRotation();

        // Calculate the translation needed to move the automatedCar's reference point
        // to it's desired place
        Point2D carMovement =
            new Point2D.Double(VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_X - automatedCar.getX(),
                VisualizationConfig.DISPLAY_EGOCAR_CENTER_POSITION_Y - automatedCar.getY());

        // Calculate the distance between the automatedCar's and the displayObject's reference points
        // (a.k.a.the position of the outer rotation point relative to the displayObject's reference point)
        Point2D refPointDistance = new Point2D.Double(automatedCar.getX() - x,
            automatedCar.getY() - y);

        // Calculate the displayObject's reference point's rotation around the automatedCar's reference point
        AffineTransform translateRefPoints = AffineTransform.getRotateInstance(rotationAngle,
            refPointDistance.getX(), refPointDistance.getY());

        // Calculate the new reference point
        int refX = x + (int) (Math.round(translateRefPoints.getTranslateX() + carMovement.getX()));
        int refY = y + (int) (Math.round(translateRefPoints.getTranslateY() + carMovement.getY()));

        // rotate and translate the the polygon with the calculated rotation
        AffineTransform t = new AffineTransform();
        t.translate(refX, refY);
        t.rotate(rotationAngle - rotation);
        Path2D rotatedPolygon = (Path2D.Double) t.createTransformedShape(polygon);

        return rotatedPolygon;
    }

    /**
     * Rotates the movement vector.
     * <p>
     * The calculated movement vector must keep its relatíve position
     * to the egocar translated and rotated to its display position.
     *
     * @param x            The x component of the movement vector
     * @param y            The y component of the movement vector
     * @param automatedCar The egocar whose position and rotation defines the return data
     * @return The {@link Point2D} object containing the rotated movement vector
     */
    public static Point2D repositionMovementVector(int x, int y, AutomatedCar automatedCar) {

        // Calculate the rotation needed to change the automatedCar's orientation
        // to the desired orientation
        double rotationAngle = VisualizationConfig.DISPLAY_EGOCAR_ROTATION - automatedCar.getRotation();

        // rotate and translate the the polygon with the calculated rotation
        // Calculate the displayObject's reference point's rotation around the automatedCar's reference point
        AffineTransform translatedVector = AffineTransform.getRotateInstance(rotationAngle,
            -x, -y);

        return new Point2D.Double(x + translatedVector.getTranslateX(), y + translatedVector.getTranslateY());
    }
}
