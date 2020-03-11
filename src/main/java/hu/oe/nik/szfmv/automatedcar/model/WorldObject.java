package hu.oe.nik.szfmv.automatedcar.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WorldObject {

    private static int uid = 1;

    private static final Logger LOGGER = LogManager.getLogger(WorldObject.class);

    protected String id;
    protected String type;
    protected int x;
    protected int y;
    protected int z;
    protected int width;
    protected int height;
    protected float rotation = 0f;
    protected float[][] rotationMatrix;
    protected String imageFileName;
    protected BufferedImage image;
    protected Polygon polygon;
    protected boolean isStatic;

    public WorldObject(int x, int y, String imageFileName) {
        this.x = x;
        this.y = y;
        this.imageFileName = imageFileName;
        initImage();
    }

    public WorldObject(String id) {
        setId(id);
        this.rotationMatrix = new float[2][2];
    }

    public WorldObject() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float[][] getRotationMatrix() {
        return rotationMatrix;
    }

    public void setRotationMatrix(float[][] matrix) {
        this.rotationMatrix = matrix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public boolean getIsStatic() {
        return isStatic;
    }

    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void initImage() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            image = ImageIO.read(classLoader.getResourceAsStream(imageFileName));
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void initObject() {
        if (id == null)
            setId(type + "_" + (uid++));
        setImageFileName(this.type + ".png");
        initImage();
        addPolygons();
    }

    private Polygon CreateCrossRoadPolygon() {
        var crossroad = new Polygon();
        crossroad.addPoint(0, 520);
        crossroad.addPoint(345, 525);
        crossroad.addPoint(530, 345);
        crossroad.addPoint(525, 0);
        crossroad.addPoint(870, 0);
        crossroad.addPoint(865, 360);
        crossroad.addPoint(1050, 535);
        crossroad.addPoint(1400, 535);
        crossroad.addPoint(1400, 870);
        crossroad.addPoint(1055, 860);
        crossroad.addPoint(870, 1055);
        crossroad.addPoint(870, 1390);
        crossroad.addPoint(525, 1390);
        crossroad.addPoint(525, 1035);
        crossroad.addPoint(350, 870);
        crossroad.addPoint(0, 875);

        return crossroad;
    }

    private Polygon Create2LaneTJunctionPolygon(boolean isLeft) {
        var road2LaneTJunction = new Polygon();

        if (isLeft) {
            road2LaneTJunction.addPoint(0, 530);
            road2LaneTJunction.addPoint(350, 530);
            road2LaneTJunction.addPoint(530, 350);
            road2LaneTJunction.addPoint(530, 0);
            road2LaneTJunction.addPoint(865, 0);
            road2LaneTJunction.addPoint(875, 1400);
            road2LaneTJunction.addPoint(525, 1400);
            road2LaneTJunction.addPoint(520, 1050);
            road2LaneTJunction.addPoint(350, 875);
            road2LaneTJunction.addPoint(0, 875);
        } else {
            road2LaneTJunction.addPoint(0, 0);
            road2LaneTJunction.addPoint(350, 0);
            road2LaneTJunction.addPoint(350, 355);
            road2LaneTJunction.addPoint(525, 525);
            road2LaneTJunction.addPoint(875, 530);
            road2LaneTJunction.addPoint(870, 870);
            road2LaneTJunction.addPoint(520, 875);
            road2LaneTJunction.addPoint(345, 1055);
            road2LaneTJunction.addPoint(350, 1095);
            road2LaneTJunction.addPoint(0, 1095);
        }

        return road2LaneTJunction;
    }

    private Polygon CreateBicyclePolygon() {
        var bicycle = new Polygon();
        bicycle.addPoint(9, 9);
        bicycle.addPoint(71, 9);
        bicycle.addPoint(71, 137);
        bicycle.addPoint(9, 137);

        return bicycle;
    }

    private Polygon CreateBollardPolygon() {
        var bollard = new Polygon();
        bollard.addPoint(0, 0);
        bollard.addPoint(20, 0);
        bollard.addPoint(20, 60);
        bollard.addPoint(0, 60);

        return bollard;
    }

    private Polygon CreateBoundaryPolygon() {
        var boundary = new Polygon();
        boundary.addPoint(0, 0);
        boundary.addPoint(50, 0);
        boundary.addPoint(50, 85);
        boundary.addPoint(0, 85);

        return boundary;
    }

    private Polygon CreateCarPolygon(boolean isLongCar) {
        var car = new Polygon();

        if (isLongCar) {
            car.addPoint(0, 0);
            car.addPoint(119, 0);
            car.addPoint(119, 286);
            car.addPoint(0, 286);
        } else {
            car.addPoint(0, 0);
            car.addPoint(105, 0);
            car.addPoint(105, 238);
            car.addPoint(0, 238);
        }

        return car;
    }

    private Polygon CreateCrossWalkPolygon() {
        var crossWalk = new Polygon();
        crossWalk.addPoint(0, 0);
        crossWalk.addPoint(335, 0);
        crossWalk.addPoint(335, 195);
        crossWalk.addPoint(0, 195);

        return crossWalk;
    }

    private Polygon CreateGaragePolygon() {
        var garage = new Polygon();
        garage.addPoint(0, 0);
        garage.addPoint(336, 0);
        garage.addPoint(336, 294);
        garage.addPoint(0, 294);

        return garage;
    }

    private Polygon CreateParkingPolygon(boolean isParallel) {
        var parking = new Polygon();

        if (isParallel) {
            parking.addPoint(0, 0);
            parking.addPoint(141, 0);
            parking.addPoint(141, 624);
            parking.addPoint(0, 624);
        } else {
            parking.addPoint(0, 0);
            parking.addPoint(295, 0);
            parking.addPoint(295, 465);
            parking.addPoint(0, 465);
        }

        return parking;
    }

    private Polygon Create2Lane6Polygon(boolean isLeft) {
        var roa2lane6 = new Polygon();

        if (isLeft) {
            roa2lane6.addPoint(0, 35);
            roa2lane6.addPoint(345, 0);
            roa2lane6.addPoint(363, 363);
            roa2lane6.addPoint(19, 363);
        } else {
            roa2lane6.addPoint(19, 0);
            roa2lane6.addPoint(365, 39);
            roa2lane6.addPoint(347, 364);
            roa2lane6.addPoint(0, 360);
        }

        return roa2lane6;
    }

    private Polygon Create2Lane45Polygon(boolean isLeft) {
        var roa2lane45 = new Polygon();

        if (isLeft) {
            roa2lane45.addPoint(0, 247);
            roa2lane45.addPoint(247, 0);
            roa2lane45.addPoint(302, 60);
            roa2lane45.addPoint(348, 141);
            roa2lane45.addPoint(380, 226);
            roa2lane45.addPoint(401, 371);
            roa2lane45.addPoint(51, 371);
            roa2lane45.addPoint(43, 321);
            roa2lane45.addPoint(31, 291);
            roa2lane45.addPoint(23, 272);
        } else {
            roa2lane45.addPoint(0, 368);
            roa2lane45.addPoint(9, 264);
            roa2lane45.addPoint(52, 141);
            roa2lane45.addPoint(91, 74);
            roa2lane45.addPoint(154, 0);
            roa2lane45.addPoint(399, 244);
            roa2lane45.addPoint(372, 284);
            roa2lane45.addPoint(359, 316);
            roa2lane45.addPoint(349, 369);
        }

        return roa2lane45;
    }

    private Polygon Create2Lane90Polygon(boolean isLeft) {
        var road2lane90 = new Polygon();

        if (isLeft) {
            road2lane90.addPoint(0, 0);
            road2lane90.addPoint(127, 17);
            road2lane90.addPoint(206, 43);
            road2lane90.addPoint(276, 79);
            road2lane90.addPoint(343, 127);
            road2lane90.addPoint(428, 221);
            road2lane90.addPoint(456, 265);
            road2lane90.addPoint(470, 293);
            road2lane90.addPoint(506, 394);
            road2lane90.addPoint(523, 521);
            road2lane90.addPoint(175, 521);
            road2lane90.addPoint(164, 462);
            road2lane90.addPoint(121, 401);
            road2lane90.addPoint(75, 369);
            road2lane90.addPoint(0, 350);
        } else {
            road2lane90.addPoint(0, 520);
            road2lane90.addPoint(20, 375);
            road2lane90.addPoint(62, 278);
            road2lane90.addPoint(133, 175);
            road2lane90.addPoint(244, 82);
            road2lane90.addPoint(345, 28);
            road2lane90.addPoint(522, 0);
            road2lane90.addPoint(522, 350);
            road2lane90.addPoint(468, 360);
            road2lane90.addPoint(427, 388);
            road2lane90.addPoint(386, 419);
            road2lane90.addPoint(363, 456);
            road2lane90.addPoint(348, 524);
        }

        return road2lane90;
    }

    private Polygon Create2LaneRotaryPolygon() {
        var road2lanerotary = new Polygon();
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

        return road2lanerotary;
    }

    private Polygon CreateRoadStraightPolygon() {
        var roadStraight = new Polygon();
        roadStraight.addPoint(0, 0);
        roadStraight.addPoint(350, 0);
        roadStraight.addPoint(350, 350);
        roadStraight.addPoint(0, 350);

        return roadStraight;
    }

    private Polygon CreateParkingSignPolygon() {
        var parkingSign = new Polygon();
        parkingSign.addPoint(0, 0);
        parkingSign.addPoint(80, 0);
        parkingSign.addPoint(80, 80);
        parkingSign.addPoint(0, 80);

        return parkingSign;
    }

    private Polygon CreateStopSignPolygon() {
        var stopSign = new Polygon();
        stopSign.addPoint(0, 23);
        stopSign.addPoint(23, 0);
        stopSign.addPoint(56, 0);
        stopSign.addPoint(80, 23);
        stopSign.addPoint(80, 55);
        stopSign.addPoint(55, 80);
        stopSign.addPoint(23, 80);

        return stopSign;
    }

    private Polygon CreateSpeedSignPolygon() {
        var speedSign = new Polygon();
        speedSign.addPoint(0, 38);
        speedSign.addPoint(10, 12);
        speedSign.addPoint(40, 0);
        speedSign.addPoint(66, 10);
        speedSign.addPoint(79, 40);
        speedSign.addPoint(70, 65);
        speedSign.addPoint(40, 80);
        speedSign.addPoint(10, 67);

        return speedSign;
    }

    private Polygon CreateTreePolygon() {
        var tree = new Polygon();
        tree.addPoint(73, 55);
        tree.addPoint(88, 63);
        tree.addPoint(94, 74);
        tree.addPoint(87, 94);
        tree.addPoint(71, 100);
        tree.addPoint(54, 93);
        tree.addPoint(45, 78);
        tree.addPoint(53, 63);

        return tree;
    }

    private Polygon CreatePersonPolygon() {
        var person = new Polygon();
        person.addPoint(0, 0);
        person.addPoint(47, 0);
        person.addPoint(47, 74);
        person.addPoint(0, 74);

        return person;
    }

    public void addPolygons() {
        if (this.imageFileName.contains("2_crossroad")) {
            this.polygon = CreateCrossRoadPolygon();
        } else if (this.imageFileName.equals("bicycle")) {
            this.polygon = CreateBicyclePolygon();
        } else if (this.imageFileName.equals("bollard")) {
            this.polygon = CreateBollardPolygon();
        } else if (this.imageFileName.equals("boundary")) {
            this.polygon = CreateBoundaryPolygon();
        } else if (this.imageFileName.contains("car_1") ||
                this.imageFileName.contains("car_2")
        ) {
            this.polygon = CreateCarPolygon(false);
        } else if (this.imageFileName.equals("car_3_black")) {
            this.polygon = CreateCarPolygon(true);
        } else if (this.imageFileName.equals("crosswalk")) {
            this.polygon = CreateCrossWalkPolygon();
        } else if (this.imageFileName.equals("garage")) {
            this.polygon = CreateGaragePolygon();
        } else if (this.imageFileName.contains("man")) {
            this.polygon = CreatePersonPolygon();
        } else if (this.imageFileName.equals("parking_90")) {
            this.polygon = CreateParkingPolygon(false);
        } else if (this.imageFileName.equals("parking_space_parallel")) {
            this.polygon = CreateParkingPolygon(true);
        } else if (this.imageFileName.equals("road_2lane_6left")) {
            this.polygon = Create2Lane6Polygon(true);
        } else if (this.imageFileName.equals("road_2lane_6right")) {
            this.polygon = Create2Lane6Polygon(false);
        } else if (this.imageFileName.equals("road_2lane_45left")) {
            this.polygon = Create2Lane45Polygon(true);
        } else if (this.imageFileName.equals("road_2lane_45right")) {
            this.polygon = Create2Lane45Polygon(false);
        } else if (this.imageFileName.equals("road_2lane_90left")) {
            this.polygon = Create2Lane90Polygon(true);
        } else if (this.imageFileName.equals("road_2lane_90right")) {
            this.polygon = Create2Lane90Polygon(false);
        } else if (this.imageFileName.equals("road_2lane_rotary")) {
            this.polygon = Create2LaneRotaryPolygon();
        } else if (this.imageFileName.equals("road_2lane_straight")) {
            this.polygon = CreateRoadStraightPolygon();
        } else if (this.imageFileName.equals("road_2lane_tjunctionleft")) {
            this.polygon = Create2LaneTJunctionPolygon(true);
        } else if (this.imageFileName.equals("road_2lane_tjunctionright")) {
            this.polygon = Create2LaneTJunctionPolygon(false);
        } else if (this.imageFileName.equals("roadsign_parking_right")) {
            this.polygon = CreateParkingSignPolygon();
        } else if (this.imageFileName.equals("roadsign_priority_stop")) {
            this.polygon = CreateStopSignPolygon();
        } else if (this.imageFileName.contains("roadsign_speed")) {
            this.polygon = CreateSpeedSignPolygon();
        } else if (this.imageFileName.equals("tree")) {
            this.polygon = CreateTreePolygon();
        }
    }
}
