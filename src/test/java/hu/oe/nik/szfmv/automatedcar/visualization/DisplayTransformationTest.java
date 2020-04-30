package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.CarVariant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisplayTransformationTest {

    AutomatedCar automatedCar;
    private int numberOfDecimals = 3;
    private int forPrecision = (int)Math.pow(10, numberOfDecimals);

    /**
     * Setting up the test
     */
    @BeforeEach
    public void init() {

        VisualizationConfig.loadReferencePoints("reference_points.xml");
        automatedCar = new AutomatedCar(292, 230, CarVariant.RED_2);
        automatedCar.setRotation((float) -Math.PI / 4) ;  // 45 +
    }

    /**
     * Check whether the image rotation, position and displacements due to the different rotation origos are correct.
     */
    @Test
    public void returnedDisplayImageData() {
        DisplayImageData did =  DisplayTransformation.repositionImage(399, 540, 0,
            "road_2lane_90right.png", automatedCar);
        assertEquals(241, did.getX());
        assertEquals(645, did.getY());
        assertEquals((float) Math.round(Math.PI / 4  * forPrecision) / forPrecision,
            (float)Math.round(did.getRotation() * forPrecision) / forPrecision);
        assertEquals(473, did.getRotationDisplacementX());
        assertEquals(-93, did.getRotationDisplacementY());
        assertEquals(349, did.getRefDifferenceX());
        assertEquals(525, did.getRefDifferenceY());
    }

    /**
     * Checks whether the rotation and translation of the polygons are correct
     * and keeps its relative position to the egocar.
     */
    @Test
    public void returnedPathData() {
        Polygon polygon = new Polygon(new int[]{0, 10}, new int[]{0, 20}, 2);
        Path2D rotatedPolygon = DisplayTransformation.repositionPolygon(399, 540,
             0, polygon, automatedCar);
        PathIterator it = rotatedPolygon.getPathIterator(null);
        float[] p = new float[2];
        it.currentSegment(p);
        assertEquals(241, Math.round(p[0]));
        assertEquals(645, Math.round(p[1]));
        it.next();
        it.currentSegment(p);
        assertEquals(241 - 7, Math.round(p[0]));
        assertEquals(645 + 21, Math.round(p[1]));
    }

    /**
     * Check whether the rotated and translated movement vector is at the right display position
     */
    @Test
    public void returnedMovementVector() {
        Point2D p = DisplayTransformation.repositionMovementVector(10, 20, automatedCar);
        assertEquals(-7, Math.round(p.getX()));
        assertEquals(21, Math.round(p.getY()));
    }
}
