package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;

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
        public ArrayList<Path2D> getPolygons() {
            calledNum++;
            ArrayList<Path2D> returnlist = new ArrayList<>();
            if (calledNum < 2) {
                Path2D polygon = new Path2D.Float();
                polygon.moveTo(-50, -50);
                polygon.lineTo(50,-50);
                polygon.lineTo(50,50);
                polygon.lineTo(-50,50);
                polygon.closePath();
                polygon.trimToSize();
                returnlist.add( polygon);
            }
            return returnlist;
        }
    }

    @Test
    public void checkTransformWithoutRotation() {
        // car refpoint 51, 104
        MockWorldObject worldObject = new MockWorldObject(10, 50);
        worldObject.setRotationMatrix(new float[][]{{1, 0}, {0, 1}}); // 0
        Path2D poly = ObjectTransform.transformPath2DPolygon(worldObject).get(0);
        float[] firstpoint = new float[2];
        if (poly != null) {
            PathIterator it = poly.getPathIterator(null);
            it.currentSegment(firstpoint);
        }
        assertEquals(-91, firstpoint[0]);
        assertEquals(-104, firstpoint[1]);
    }

    @Test
    public void checkTransformWithtRotation() {
        // car refpoint 51, 104
        MockWorldObject worldObject = new MockWorldObject(10, 50);
        worldObject.setRotationMatrix(new float[][]{{0, 1}, {-1, 0}}); // 90 CW
        Path2D poly = ObjectTransform.transformPath2DPolygon(worldObject).get(0);
        float[] firstpoint = new float[2];
        if (poly != null) {
            PathIterator it = poly.getPathIterator(null);
            it.currentSegment(firstpoint);
        }
        assertEquals(164, firstpoint[0]);
        assertEquals(-51, firstpoint[1]);
    }

    @Test
    public void checkEmptyPolygonList() {
        MockWorldObject worldObject = new MockWorldObject(10, 50);
        ArrayList<Path2D> polys = ObjectTransform.transformPath2DPolygon(worldObject);
        polys = ObjectTransform.transformPath2DPolygon(worldObject);
        assertEquals(0, polys.size());
    }

}
