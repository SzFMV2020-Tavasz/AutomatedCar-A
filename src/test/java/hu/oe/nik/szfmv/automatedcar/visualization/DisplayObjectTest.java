package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisplayObjectTest {

    private DisplayObject displayObject;
    private WorldObject worldObject;
    private AutomatedCar automatedCar;
    private int numberOfDecimals = 5;
    private int forPrecision = (int)Math.pow(10, numberOfDecimals);

    /**
     * Setting up the test.
     */
    @BeforeEach
    public void init() {
        automatedCar = new AutomatedCar(292, 230, "car_2_red.png");
        automatedCar.setRotation((float) Math.PI / 4);  // 45
        worldObject = new WorldObject(399, 540, "road_2lane_90right.png");
        displayObject = new DisplayObject(worldObject, automatedCar);
    }

    /**
     * Check whether the position of the reference point is calculated right
     * relative to the automatedCar object (egocar).
     */
    @Test
    public void relativePosition() {
        assertEquals(241, displayObject.getX());
        assertEquals(645, displayObject.getY());
    }

    /**
     * Check whether the rotation of the DisplayObject kept
     * the relative rotation to the automatedCar object (egocar)
     */
    @Test
    public void rotation() {
        assertEquals((float) Math.round(Math.PI / 4  * forPrecision)/forPrecision,
                (float)Math.round(displayObject.getRotation() * forPrecision)/forPrecision);
    }

    /**
     * Check whether the translation values needed for fixing the position difference caused by the difference between
     * the displayObject's reference point and the displayObject's image's rotation origo are actually right.
     */
    @Test
    public void rotationDisplacement() {
        assertEquals(473, displayObject.getRotationDisplacementX());
        assertEquals(-93, displayObject.getRotationDisplacementY());
    }
}

