package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.visualization.VisualizationConfig;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

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
     * @param object The {@link WorldObject} containing the polygon to be transformed
     * @return The transformed debug polygon; null if there was no debug polygon given to begin with.
     */
    public static Polygon transformPolygon(WorldObject object) {
        Polygon polygon = object.getPolygon();
        if (polygon != null) {
            int[] xpoints = new int[polygon.npoints];
            int[] ypoints = new int[polygon.npoints];
            Shape shape = getTransformedShape(object);
            // Shape created by AffineTransform needs to be converted back to polygon
            PathIterator it = shape.getPathIterator(null);
            for (int i = 0; i < polygon.npoints; i++) {
                float[] p = new float[2];
                it.currentSegment(p);
                xpoints[i] = (int) p[0];
                ypoints[i] = (int) p[1];
                it.next();
            }
            return new Polygon(xpoints, ypoints, polygon.npoints);
        } else {
            return null;
        }

    }

    /**
     * Helper method to do the actual transformation of the polygon
     * @param object The {@link WorldObject} containing the polygon to be transformed
     * @return the Shape object that resulted from the AffineTransformation
     */
    private static Shape getTransformedShape(WorldObject object) {
        // can be removed if WorldObject will have reference point data
        // loadReferencePoints can be called as many times as we like
        // as it only loads reference point data if it had not been loaded earlier.
        VisualizationConfig.loadReferencePoints("reference_points.xml");
        Point2D refPoint = VisualizationConfig.getReferencePoint(object.getImageFileName());

        // move polygon to its right place relative to the reference point of the object
        // needs to be done this way to rotate around the right point
        Polygon origPolygon = object.getPolygon();
        Polygon refPointPolygon = new Polygon();
        for (int i = 0; i < origPolygon.npoints; i++) {
            refPointPolygon.addPoint((int)(origPolygon.xpoints[i] - refPoint.getX()),
                (int)(origPolygon.ypoints[i] - refPoint.getY()));
        }

        AffineTransform at = new AffineTransform();

        at.translate(object.getX() , object.getY());
        if (object.getRotationMatrix() != null) {
            at.rotate(VisualizationConfig.getAngleFromRotationMatrix(object.getRotationMatrix()));
        }

        return at.createTransformedShape((Shape) refPointPolygon);
    }

}
