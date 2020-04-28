package hu.oe.nik.szfmv.automatedcar.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Camera {
    World world;

    public Camera(World world) {
        this.world = world;
    }

    public List<WorldObject> getRelevantCameraObjects(int viewAngleDegree, int viewDistance, boolean areSignObjectsNeeded) {

        float x1, x2, y1, y2;

        var carObject = world.getObject("car");
        Point a = new Point(carObject.x, carObject.y);
        float carHeadingAngle = carObject.rotation;
        x1 = viewDistance * (float) Math.cos(Math.toRadians(carHeadingAngle - (viewAngleDegree / 2)));
        y1 = viewDistance * (float) Math.sin(Math.toRadians(carHeadingAngle - (viewAngleDegree / 2)));
        x2 = viewDistance * (float) Math.cos(Math.toRadians(carHeadingAngle + (viewAngleDegree / 2)));
        y2 = viewDistance * (float) Math.sin(Math.toRadians(carHeadingAngle + (viewAngleDegree / 2)));
        Point b = new Point((int) x1, (int) y1);
        Point c = new Point((int) x2, (int) y2);

        List<WorldObject> instanceL = world.getObjectsInsideTriangle(a, b, c);

        if (areSignObjectsNeeded) {
            List<WorldObject> signObjectsL = new ArrayList<WorldObject>();

            for (var item : instanceL) {
                if (item.type.contains("roadsign"))
                    signObjectsL.add(item);

            }
            return signObjectsL;
        } else {
            List<WorldObject> trackObjectsL = new ArrayList<WorldObject>();

            for (var item : instanceL) {
                if (!item.type.contains("roadsign") && item.type.contains("road"))
                    trackObjectsL.add(item);

            }
            return trackObjectsL;
        }


    }
}
