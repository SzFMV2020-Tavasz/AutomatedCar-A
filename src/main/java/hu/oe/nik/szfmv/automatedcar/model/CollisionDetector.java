package hu.oe.nik.szfmv.automatedcar.model;

import java.awt.*;
import java.awt.geom.Area;
import java.util.function.BiFunction;

public class CollisionDetector implements BiFunction<Polygon, Polygon, Boolean> {

    @Override
    public Boolean apply(Polygon p1, Polygon p2) {
        return p1.getBounds2D().intersects(p2.getBounds2D());
    }
}
