package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisplayObjectTest {

    private DisplayObject displayObject;
    private WorldObject worldObject;
    private AutomatedCar automatedCar;

    /**
     * Setting up the test.
     */
    @Before
    public void init() {
        automatedCar = new AutomatedCar(300, 200, "car_2_red.png");
        automatedCar.setRotation((float) Math.PI / 4);  // 45
        worldObject = new WorldObject(441, 200, "road_2lane_90right.png");
        displayObject = new DisplayObject(worldObject, automatedCar);
    }

    /**
     * Check whether the position of the reference point is calculated right
     * relative to the automatedCar object (egocar).
     */
    @Test
    public void relativePosition() {
        assertEquals(400, displayObject.getX());
        assertEquals(300, displayObject.getY());
    }

    /**
     * Check whether the rotation of the DisplayObject kept
     * the relative rotation to the automatedCar object (egocar)
     */
    @Test
    public void rotation() {
        assertEquals((float) -Math.PI / 4, displayObject.getRotation());
    }
}

