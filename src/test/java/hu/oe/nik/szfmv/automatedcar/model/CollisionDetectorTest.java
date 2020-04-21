package hu.oe.nik.szfmv.automatedcar.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

public class CollisionDetectorTest {
    @Test
    public void twoPolygonCollide() {
        Polygon p1 = new PolygonCreating("parking_90").getPolygon();
        Polygon p2 = new PolygonCreating("bicycle").getPolygon();

        assertTrue(new CollisionDetector().apply(p1, p2));
    }

    @Test
    public void twoPolygonDontCollide() {
        Polygon p1 = new Polygon();
        p1.addPoint(0,0);
        p1.addPoint(1,1);
        p1.addPoint(2,0);

        Polygon p2 = new PolygonCreating("bicycle").getPolygon();

        assertFalse(new CollisionDetector().apply(p1, p2));
    }
}
