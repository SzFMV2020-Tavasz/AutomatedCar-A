package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.visualization.VisualizationConfig;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for providing methods for transforming (a.k.a. translating and rotating) debug polygon elements
 * defined relative to the x,y coordinates of their containing {@link WorldObject} to their actual place
 * in {@link World}
 */
public final class ObjectTransform {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor for static class
     * <p>
     * It does not need to be instantiated.
     * </p>
     */
    private ObjectTransform() {
    }

    /**
     * Transforms the debug polygon elements defined relative to the x,y coordinates
     * of their containing {@link WorldObject} to their actual place
     * in {@link World}.
     * Writing the transformed polygon back to the related WorldObject is forbidden
     * as it would break the visualization.
     *
     * @param object The {@link WorldObject} containing the polygons to be transformed
     * @return The ArrayList of the transformed debug polygons.
     * Empty ArrayList if there are no polygons in the object.
     */
    public static ArrayList<Path2D> transformPath2DPolygon(WorldObject object) {
        ArrayList<Path2D> polygons = object.getPolygons();
        ArrayList<Path2D> returnPolys = new ArrayList<>();
        for (Path2D polygon : polygons) {
            if (polygon.getCurrentPoint() != null) {
                Path2D path = getTransformedShape(polygon, object.getX(), object.getY(),
                    object.getRotationMatrix(), object.getImageFileName());

                returnPolys.add(path);
            }
        }
        return returnPolys;
    }

    /**
     * Helper method to do the actual transformation of the Path2D polygon
     *
     * @param path           The {@link Path2D} polygon to be transformed
     * @param refPosX        The x position of the reference point in the world coordinate system
     * @param refPosY        The y position of the reference point in the world coordinate system
     * @param rotationMatrix The rotation matrix of the {@link WorldObject} containing the Path2D polygon
     * @param imageFilename  The image file name of the {@link WorldObject}
     * @return The {@link Path2D} object that resulted from the AffineTransformation
     */
    private static Path2D getTransformedShape(Path2D path, int refPosX, int refPosY,
                                              float[][] rotationMatrix, String imageFilename) {
        // can be removed if WorldObject will have reference point data
        // loadReferencePoints can be called as many times as we like
        // as it only loads reference point data if it had not been loaded earlier.
        VisualizationConfig.loadReferencePoints("reference_points.xml");
        Point2D refPoint = VisualizationConfig.getReferencePoint(imageFilename);

        // move polygon to its right place relative to the reference point of the object
        // needs to be done this way to rotate around the right point
        Path2D refPointPolygon = translatePath2D(path, -refPoint.getX(), -refPoint.getY());
        AffineTransform at = new AffineTransform();

        at.translate(refPosX, refPosY);
        if (rotationMatrix != null) {
            at.rotate(VisualizationConfig.getAngleFromRotationMatrix(rotationMatrix));
        }
        Path2D resultshape = (Path2D) at.createTransformedShape(refPointPolygon);
        if (resultshape.getCurrentPoint() == null) {
            return null;
        } else {
            return resultshape;
        }

    }


    /**
     * Translates the {@link Path2D} object with the given values and gives back the translated copy of the path
     *
     * @param path       the {@link Path2D} object to be translated
     * @param translateX the x component of the desired translation
     * @param translateY the y component of the desired translation
     * @return the translated {@link Path2D} clone of the original path
     */
    public static Path2D translatePath2D(Path2D path, double translateX, double translateY) {
        boolean firstElement = true;
        Path2D newPoly = new Path2D.Float();
        PathIterator iter = path.getPathIterator(null);
        while (!iter.isDone()) {
            float[] currentPoint = new float[2];
            iter.currentSegment(currentPoint);
            if (firstElement) {
                newPoly.moveTo(currentPoint[0] + translateX, currentPoint[1] + translateY);
                firstElement = false;
            } else {
                newPoly.lineTo(currentPoint[0] + translateX, currentPoint[1] + translateY);
            }
            iter.next();
        }
        newPoly.trimToSize();
        return newPoly;
    }
}
