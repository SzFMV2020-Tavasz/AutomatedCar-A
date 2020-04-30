package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.CarVariant;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DisplayObjectTest {

    private MockDisplayObject displayObject;
    private MockWorldObject worldObject;
    private AutomatedCar automatedCar;
    private int numberOfDecimals = 3;
    private int forPrecision = (int)Math.pow(10, numberOfDecimals);
    DisplayImageData displayImageData;

    /**
     * Extending classes with methods for testing private parameters
     */
    class MockWorldObject extends WorldObject {
        MockWorldObject(int width, int height, String fileName) {
            super (width, height, fileName);
        }

        @Override
        public Polygon getPolygon() {
            return new Polygon(new int[]{10}, new int[]{20}, 1);
        }
    }

    class MockDisplayObject extends DisplayObject {
        MockDisplayObject(WorldObject worldObject, AutomatedCar automatedCar) {
            super (worldObject, automatedCar);
        }

        float getWorldObjectRotation() {
            return worldObject.getRotation();
        }
    }
    /**
     * Setting up the test.
     */
    @BeforeEach
    public void init() {
        VisualizationConfig.loadReferencePoints("reference_points.xml");
        automatedCar = new AutomatedCar(292, 230, CarVariant.RED_2);
        automatedCar.setRotation(-(float) Math.PI / 4) ;  // 45 +
        worldObject = new MockWorldObject(399, 540, "road_2lane_90right.png");
        displayObject = new MockDisplayObject(worldObject, automatedCar);
        displayImageData = displayObject.getDisplayImageData();
    }

    /**
     * Check whether the class gets instatniatied when new DispLayobjct() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(displayObject);
    }

    /**
     * Check whether the position of the reference point is calculated right
     * relative to the automatedCar object (egocar).
     */
    @Test
    public void relativePosition() {
        assertEquals(241, displayImageData .getX());
        assertEquals(645, displayImageData.getY());
    }

    /**
     * Check whether the rotation of the DisplayObject kept
     * the relative rotation to the automatedCar object (egocar)
     */
    @Test
    public void rotation() {
        assertEquals((float) Math.round(Math.PI / 4  * forPrecision) / forPrecision,
                (float)Math.round(displayImageData.getRotation() * forPrecision) / forPrecision);
    }

    /**
     * Check whether the translation values needed for fixing the position difference caused by the difference between
     * the displayObject's reference point and the displayObject's image's rotation origo are actually right.
     */
    @Test
    public void rotationDisplacement() {
        assertEquals(473, displayImageData.getRotationDisplacementX());
        assertEquals(-93, displayImageData.getRotationDisplacementY());
    }

    /**
     * Check whether the displayObject's reference point xy values are right
     */
    @Test
    public void referencePoint() {
        assertEquals(349, displayImageData.getRefDifferenceX());
        assertEquals(525, displayImageData.getRefDifferenceY());
    }

    /**
     * Checks the rotationmatrix -> rotationAngle conversion at the III. and IV. quarter
     * it needs to give an opposite sign angle (because of the difference of the positive rotation
     * between the rotation matrix and the JPanel rotation)
     */
    @Test
    public void checkConversionRotationMatrixToMatrixSinPos() {
        // Setting up different rotation from matrix
        worldObject.setRotationMatrix(new float[][]{{-0.7071f, 0.7071f}, {-0.7071f, -0.7071f}});
        displayObject = new MockDisplayObject(worldObject, automatedCar);
        assertEquals(Math.round(Math.PI / 4 * 5 * forPrecision) / (float) forPrecision,
            Math.round((-displayObject.getWorldObjectRotation()) * forPrecision) / (float) forPrecision);
    }

    /**
     * Checks the rotationmatrix -> rotationAngle conversion at the I. and II. quarter
     * it needs to give an opposite sign angle (because of the difference of the positive rotation
     * between the rotation matrix and the JPanel rotation)
     */
    @Test
    public void checkConversionRotationMatrixToMatrixSinNeg() {
        // Setting up different rotation from matrix
        worldObject.setRotationMatrix (new float[][]{{0.7071f, -0.7071f}, {0.7071f, 0.7071f}});
        displayObject = new MockDisplayObject(worldObject, automatedCar);
        assertEquals(Math.round(Math.PI / 4  * forPrecision) / (float) forPrecision,
            Math.round((-displayObject.getWorldObjectRotation()) * forPrecision) / (float) forPrecision);
    }

    /**
     * Checks the calculated display polygon rotation and orientation
      */
    @Test
    public void checkPolygonRotationResults() {
        assertEquals(358, Math.round(displayObject.getDebugPolygon().getCurrentPoint().getX()
            * forPrecision) / forPrecision);
        assertEquals(48, Math.round(displayObject.getDebugPolygon().getCurrentPoint().getY()
            * forPrecision) / forPrecision);
    }

}

