package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    private int width;
    private int height;
    private List<WorldObject> worldObjects;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.worldObjects = Deserializer.DeserializeWorldJson("test_world.json");
        addPolygons();
    }

    public World(int width, int height, List<WorldObject> instanceList) {
        this.width = width;
        this.height = height;
        this.worldObjects = instanceList;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public List<WorldObject> getDynamics() {
        List<WorldObject> dynamicObjectList = new ArrayList<>();
        for (WorldObject item : worldObjects) {
            if (!item.isStatic)
                dynamicObjectList.add(item);
        }
        return dynamicObjectList;
    }

    public List<WorldObject> getObjectsInsideTriangle(Point a, Point b, Point c) {
        List<WorldObject> objectInsideTriangle = new ArrayList<>();
        for (WorldObject item : worldObjects) {
            if (isInside(a, b, c, item))
                objectInsideTriangle.add(item);
        }
        return objectInsideTriangle;
    }

    private boolean isInside(Point a, Point b, Point c, WorldObject item) {
        Point itemsPosition = new Point(item.getX(), item.getY());

        double A = area(a, b, c);
        double A1 = area(itemsPosition, b, c);
        double A2 = area(a, itemsPosition, c);
        double A3 = area(a, b, itemsPosition);
        return (A == A1 + A2 + A3);
    }

    private double area(Point a, Point b, Point c) {
        return round((Math.abs((a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX() * (a.getY() - b.getY())) / 2.0)), 2);
    }

    private static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public WorldObject getObject(String id) {
        for (WorldObject item : worldObjects) {
            if (item.id.equals(id))
                return item;
        }
        return null;
    }

    public void setObject(String id, int x, int y, float[][] rotationMatrix) {
        for (WorldObject item : worldObjects
        ) {
            if (item.getId().equals(id)) {
                item.setX(x);
                item.setY(y);
                item.setRotationMatrix(rotationMatrix);
            }
        }
    }

    /**
     * Add an object to the virtual world.
     *
     * @param o {@link WorldObject} to be added to the virtual world
     */
    public void addObjectToWorld(WorldObject o) {
        worldObjects.add(o);
    }

    public void addPolygons() {
        Polygon crossroad1 = new Polygon();
        crossroad1.addPoint(0, 520);
        crossroad1.addPoint(345, 525);
        crossroad1.addPoint(530, 345);
        crossroad1.addPoint(525, 0);
        crossroad1.addPoint(870, 0);
        crossroad1.addPoint(865, 360);
        crossroad1.addPoint(1050, 535);
        crossroad1.addPoint(1400, 535);
        crossroad1.addPoint(1400, 870);
        crossroad1.addPoint(1055, 860);
        crossroad1.addPoint(870, 1055);
        crossroad1.addPoint(870, 1390);
        crossroad1.addPoint(525, 1390);
        crossroad1.addPoint(525, 1035);
        crossroad1.addPoint(350, 870);
        crossroad1.addPoint(0, 875);

        Polygon road2lanetjunctionleft = new Polygon();
        road2lanetjunctionleft.addPoint(0, 530);
        road2lanetjunctionleft.addPoint(350, 530);
        road2lanetjunctionleft.addPoint(530, 350);
        road2lanetjunctionleft.addPoint(530, 0);
        road2lanetjunctionleft.addPoint(865, 0);
        road2lanetjunctionleft.addPoint(875, 1400);
        road2lanetjunctionleft.addPoint(525, 1400);
        road2lanetjunctionleft.addPoint(520, 1050);
        road2lanetjunctionleft.addPoint(350, 875);
        road2lanetjunctionleft.addPoint(0, 875);
        Polygon road2lanetjuctionright = new Polygon();
        road2lanetjuctionright.addPoint(0, 0);
        road2lanetjuctionright.addPoint(350, 0);
        road2lanetjuctionright.addPoint(350, 355);
        road2lanetjuctionright.addPoint(525, 525);
        road2lanetjuctionright.addPoint(875, 530);
        road2lanetjuctionright.addPoint(870, 870);
        road2lanetjuctionright.addPoint(520, 875);
        road2lanetjuctionright.addPoint(345, 1055);
        road2lanetjuctionright.addPoint(350, 1095);
        road2lanetjuctionright.addPoint(0, 1095);

        Polygon bicycle = new Polygon();
        bicycle.addPoint(9, 9);
        bicycle.addPoint(71, 9);
        bicycle.addPoint(71, 137);
        bicycle.addPoint(9, 137);

        Polygon bollard = new Polygon();
        bollard.addPoint(0, 0);
        bollard.addPoint(20, 0);
        bollard.addPoint(20, 60);
        bollard.addPoint(0, 60);

        Polygon boundary = new Polygon();
        boundary.addPoint(0, 0);
        boundary.addPoint(50, 0);
        boundary.addPoint(50, 85);
        boundary.addPoint(0, 85);

        Polygon car = new Polygon();
        car.addPoint(0, 0);
        car.addPoint(105, 0);
        car.addPoint(105, 238);
        car.addPoint(0, 238);

        Polygon longcar = new Polygon();
        longcar.addPoint(0, 0);
        longcar.addPoint(119, 0);
        longcar.addPoint(119, 286);
        longcar.addPoint(0, 286);

        Polygon crossWalk = new Polygon();
        crossWalk.addPoint(0, 0);
        crossWalk.addPoint(335, 0);
        crossWalk.addPoint(335, 195);
        crossWalk.addPoint(0, 195);

        Polygon garage = new Polygon();
        garage.addPoint(0, 0);
        garage.addPoint(336, 0);
        garage.addPoint(336, 294);
        garage.addPoint(0, 294);

        Polygon parking90 = new Polygon();
        parking90.addPoint(0, 0);
        parking90.addPoint(295, 0);
        parking90.addPoint(295, 465);
        parking90.addPoint(0, 465);

        Polygon paralellParking = new Polygon();
        paralellParking.addPoint(0, 0);
        paralellParking.addPoint(141, 0);
        paralellParking.addPoint(141, 624);
        paralellParking.addPoint(0, 624);

        Polygon road2lane6left = new Polygon();
        road2lane6left.addPoint(0, 35);
        road2lane6left.addPoint(345, 0);
        road2lane6left.addPoint(363, 363);
        road2lane6left.addPoint(19, 363);

        Polygon road2lane6right = new Polygon();
        road2lane6right.addPoint(19, 0);
        road2lane6right.addPoint(365, 39);
        road2lane6right.addPoint(347, 364);
        road2lane6right.addPoint(0, 360);

        Polygon road2lane45left = new Polygon();
        road2lane45left.addPoint(0, 247);
        road2lane45left.addPoint(247, 0);
        road2lane45left.addPoint(302, 60);
        road2lane45left.addPoint(348, 141);
        road2lane45left.addPoint(380, 226);
        road2lane45left.addPoint(401, 371);
        road2lane45left.addPoint(51, 371);
        road2lane45left.addPoint(43, 321);
        road2lane45left.addPoint(31, 291);
        road2lane45left.addPoint(23, 272);

        Polygon road2lane45right = new Polygon();
        road2lane45right.addPoint(0, 368);
        road2lane45right.addPoint(9, 264);
        road2lane45right.addPoint(52, 141);
        road2lane45right.addPoint(91, 74);
        road2lane45right.addPoint(154, 0);
        road2lane45right.addPoint(399, 244);
        road2lane45right.addPoint(372, 284);
        road2lane45right.addPoint(359, 316);
        road2lane45right.addPoint(349, 369);

        Polygon road2lane90left = new Polygon();
        road2lane90left.addPoint(0, 0);
        road2lane90left.addPoint(127, 17);
        road2lane90left.addPoint(206, 43);
        road2lane90left.addPoint(276, 79);
        road2lane90left.addPoint(343, 127);
        road2lane90left.addPoint(428, 221);
        road2lane90left.addPoint(456, 265);
        road2lane90left.addPoint(470, 293);
        road2lane90left.addPoint(506, 394);
        road2lane90left.addPoint(523, 521);
        road2lane90left.addPoint(175, 521);
        road2lane90left.addPoint(164, 462);
        road2lane90left.addPoint(121, 401);
        road2lane90left.addPoint(75, 369);
        road2lane90left.addPoint(0, 350);

        Polygon road2lane90right = new Polygon();
        road2lane90right.addPoint(0, 520);
        road2lane90right.addPoint(20, 375);
        road2lane90right.addPoint(62, 278);
        road2lane90right.addPoint(133, 175);
        road2lane90right.addPoint(244, 82);
        road2lane90right.addPoint(345, 28);
        road2lane90right.addPoint(522, 0);
        road2lane90right.addPoint(522, 350);
        road2lane90right.addPoint(468, 360);
        road2lane90right.addPoint(427, 388);
        road2lane90right.addPoint(386, 419);
        road2lane90right.addPoint(363, 456);
        road2lane90right.addPoint(348, 524);

        Polygon road2lanerotary = new Polygon();
        road2lanerotary.addPoint(0, 533);
        road2lanerotary.addPoint(341, 517);
        road2lanerotary.addPoint(525, 341);
        road2lanerotary.addPoint(525, 0);
        road2lanerotary.addPoint(870, 0);
        road2lanerotary.addPoint(860, 349);
        road2lanerotary.addPoint(1050, 530);
        road2lanerotary.addPoint(1397, 537);
        road2lanerotary.addPoint(1397, 862);
        road2lanerotary.addPoint(1040, 869);
        road2lanerotary.addPoint(860, 1045);
        road2lanerotary.addPoint(865, 1389);
        road2lanerotary.addPoint(530, 1389);
        road2lanerotary.addPoint(525, 1037);
        road2lanerotary.addPoint(345, 865);
        road2lanerotary.addPoint(0, 865);

        Polygon roadStraight = new Polygon();
        roadStraight.addPoint(0, 0);
        roadStraight.addPoint(350, 0);
        roadStraight.addPoint(350, 350);
        roadStraight.addPoint(0, 350);

        Polygon roadSignParking = new Polygon();
        roadSignParking.addPoint(0, 0);
        roadSignParking.addPoint(80, 0);
        roadSignParking.addPoint(80, 80);
        roadSignParking.addPoint(0, 80);

        Polygon roadSignPriorityStop = new Polygon();
        roadSignPriorityStop.addPoint(0, 23);
        roadSignPriorityStop.addPoint(23, 0);
        roadSignPriorityStop.addPoint(56, 0);
        roadSignPriorityStop.addPoint(80, 23);
        roadSignPriorityStop.addPoint(80, 55);
        roadSignPriorityStop.addPoint(55, 80);
        roadSignPriorityStop.addPoint(23, 80);

        Polygon roadSignSpeed = new Polygon();
        roadSignSpeed.addPoint(0, 38);
        roadSignSpeed.addPoint(10, 12);
        roadSignSpeed.addPoint(40, 0);
        roadSignSpeed.addPoint(66, 10);
        roadSignSpeed.addPoint(79, 40);
        roadSignSpeed.addPoint(70, 65);
        roadSignSpeed.addPoint(40, 80);
        roadSignSpeed.addPoint(10, 67);

        Polygon tree = new Polygon();
        tree.addPoint(73, 55);
        tree.addPoint(88, 63);
        tree.addPoint(94, 74);
        tree.addPoint(87, 94);
        tree.addPoint(71, 100);
        tree.addPoint(54, 93);
        tree.addPoint(45, 78);
        tree.addPoint(53, 63);

        Polygon woman = new Polygon();
        woman.addPoint(0, 0);
        woman.addPoint(47, 0);
        woman.addPoint(47, 74);
        woman.addPoint(0, 74);

        Polygon carIcon = new Polygon();
        carIcon.addPoint(0, 0);
        carIcon.addPoint(32, 0);
        carIcon.addPoint(32, 32);
        carIcon.addPoint(0, 32);

        for (WorldObject item : worldObjects) {
            if (item.imageFileName.equals("2_crossroad_1")) {
                item.polygon = crossroad1;
            } else if (item.imageFileName.equals("2_crossroad_2")) {
                item.polygon = crossroad1;
            } else if (item.imageFileName.equals("bicycle")) {
                item.polygon = bicycle;
            } else if (item.imageFileName.equals("bollard")) {
                item.polygon = bollard;
            } else if (item.imageFileName.equals("boundary")) {
                item.polygon = boundary;
            } else if (item.imageFileName.equals("car_1_blue") ||
                    item.imageFileName.equals("car_1_red") ||
                    item.imageFileName.equals("car_1_white") ||
                    item.imageFileName.equals("car_2_blue") ||
                    item.imageFileName.equals("car_2_red") ||
                    item.imageFileName.equals("car_2_white")
            ) {
                item.polygon = car;
            } else if (item.imageFileName.equals("car_3_black")) {
                item.polygon = longcar;
            } else if (item.imageFileName.equals("car-icon")) {
                item.polygon = carIcon;
            } else if (item.imageFileName.equals("crosswalk")) {
                item.polygon = crossWalk;
            } else if (item.imageFileName.equals("garage")) {
                item.polygon = garage;
            } else if (item.imageFileName.equals("man")) {
                item.polygon = woman;
            } else if (item.imageFileName.equals("parking_90")) {
                item.polygon = parking90;
            } else if (item.imageFileName.equals("parking_space_parallel")) {
                item.polygon = paralellParking;
            } else if (item.imageFileName.equals("road_2lane_6left")) {
                item.polygon = road2lane6left;
            } else if (item.imageFileName.equals("road_2lane_6right")) {
                item.polygon = road2lane6right;
            } else if (item.imageFileName.equals("road_2lane_45left")) {
                item.polygon = road2lane45left;
            } else if (item.imageFileName.equals("road_2lane_45right")) {
                item.polygon = road2lane45right;
            } else if (item.imageFileName.equals("road_2lane_90left")) {
                item.polygon = road2lane90left;
            } else if (item.imageFileName.equals("road_2lane_90right")) {
                item.polygon = road2lane90right;
            } else if (item.imageFileName.equals("road_2lane_rotary")) {
                item.polygon = road2lanerotary;
            } else if (item.imageFileName.equals("road_2lane_straight")) {
                item.polygon = roadStraight;
            } else if (item.imageFileName.equals("road_2lane_tjunctionleft")) {
                item.polygon = road2lanetjunctionleft;
            } else if (item.imageFileName.equals("road_2lane_tjunctionright")) {
                item.polygon = road2lanetjuctionright;
            } else if (item.imageFileName.equals("roadsign_parking_right")) {
                item.polygon = roadSignParking;
            } else if (item.imageFileName.equals("roadsign_priority_stop")) {
                item.polygon = roadSignPriorityStop;
            } else if (item.imageFileName.equals("roadsign_speed_40") ||
                    item.imageFileName.equals("roadsign_speed_50") || item.imageFileName.equals("roadsign_speed_60")) {
                item.polygon = roadSignSpeed;
            } else if (item.imageFileName.equals("tree")) {
                item.polygon = tree;
            } else if (item.imageFileName.equals("woman")) {
                item.polygon = woman;
            }


        }


    }
}
