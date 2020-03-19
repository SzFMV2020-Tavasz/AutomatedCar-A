package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Path2D;

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
        automatedCar = new AutomatedCar(292, 230, "car_2_red.png");
        automatedCar.setRotation((float) -Math.PI / 4) ;  // 45 +
    }

    /**
     * Check whether the image rotation, position and displacements due to the different rotation origos are correct.
     */

    @Test
    public void returnedDisplayImageData() {
        DisplayImageData did =  DisplayTransformation.repositionImage(399, 540, 0,
            "road_2lane_90right.png", automatedCar );
        assertEquals(241, did.getX());
        assertEquals(645, did.getY());
        assertEquals((float) Math.round(Math.PI / 4  * forPrecision)/forPrecision,
            (float)Math.round(did.getRotation() * forPrecision)/forPrecision);
        assertEquals(473, did.getRotationDisplacementX());
        assertEquals(-93, did.getRotationDisplacementY());
        assertEquals(349, did.getRefDifferenceX());
        assertEquals(525, did.getRefDifferenceY());
    }

    @Test
    public void returnedPathData() {
         Polygon polygon = new Polygon( new int[]{10}, new int[]{20}, 1 );
         Path2D rotatedPolygon = DisplayTransformation.repositionPolygon(399, 540, 241,645,
             0, polygon, automatedCar);
         assertEquals(251, Math.round(rotatedPolygon.getCurrentPoint().getX()));
         assertEquals(665, Math.round(rotatedPolygon.getCurrentPoint().getY()));
    }
}
