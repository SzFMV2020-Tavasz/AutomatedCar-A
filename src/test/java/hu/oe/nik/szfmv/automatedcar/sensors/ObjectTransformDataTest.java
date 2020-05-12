package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectTransformDataTest {

    /**
     * Extended class to allow setting test data
     */
    class MockWorldObject extends WorldObject {
        int calledNum = 0;
        MockWorldObject(int x, int y) {
            super(x, y, "car_2_red.png");
        }

        // needs to be overriden because the test needs to run
        // even if the WorldObject's debug polygon gets changed
        @Override
        public Polygon getPolygon() {
            calledNum++;
            if (calledNum < 3) {
                return new Polygon(new int[]{-50, 50, 50, -50}, new int[]{-50, -50, 50, 50}, 4);
            } else {
                return null;
            }
        }
    }

    @Test
    public void checkTransformWithoutRotation() {
        // car refpoint 51, 104
        MockWorldObject worldObject = new MockWorldObject(10, 50);
        worldObject.setRotationMatrix(new float[][]{{1, 0}, {0, 1}}); // 0
        Polygon poly = ObjectTransform.transformPolygon(worldObject);
        assertEquals(-91, poly.xpoints[0]);
        assertEquals(-104, poly.ypoints[0]);
    }

    @Test
    public void checkTransformWithtRotation() {
        // car refpoint 51, 104
        MockWorldObject worldObject = new MockWorldObject(10, 50);
        worldObject.setRotationMatrix(new float[][]{{0, 1}, {-1, 0}}); // 90 CW
        Polygon poly = ObjectTransform.transformPolygon(worldObject);
        assertEquals(164, poly.xpoints[0]);
        assertEquals(-51, poly.ypoints[0]);
    }

    @Test
    public void checkNullPolygon() {
        MockWorldObject worldObject = new MockWorldObject(10, 50);
        Polygon poly = ObjectTransform.transformPolygon(worldObject);
        // second call to get null result
        poly = ObjectTransform.transformPolygon(worldObject);
        assertNull(poly);
    }

}
