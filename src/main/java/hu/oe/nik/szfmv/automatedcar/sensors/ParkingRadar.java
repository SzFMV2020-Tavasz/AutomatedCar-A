package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.*;
import hu.oe.nik.szfmv.automatedcar.visualization.ParkingRadarPositions;
import hu.oe.nik.szfmv.automatedcar.visualization.VisualizationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class ParkingRadar extends SystemComponent {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final float FOR_DIGIT = 10f;

    // parking radar sensor triangles data
    private static final double PARKING_RADAR_SENSOR_ANGLE = Math.toRadians(36);  // 60 /2 - half angle
    private static final int PARKING_RADAR_SENSOR_RANGE = VisualizationConfig.METER_IN_PIXELS;
    private static final Color PARKING_RADAR_SENSOR_BG_COLOUR = new Color(
        255, 200, 100, VisualizationConfig.SENSOR_COLOR_ALPHA);
    private static final int TRIANGLE_POLYGON_POINTS = 3;

    // distance from the car's reference point if
    private static final int PARKING_RADAR_POS_X = 35;
    private static final int PARKING_RADAR_POS_Y = 97;
    private static final int PARKING_RADAR_TRIANGLE_HALF_X =
        (int) (Math.tan(PARKING_RADAR_SENSOR_ANGLE) * PARKING_RADAR_SENSOR_RANGE);

    // this is for demo
    private static final float MAX_D = 1f;
    private static final float MIN_D = .2f;
    private static final float STEP_D = .1f;

    // packets for sending data
    private final ParkingRadarVisualizationPacket parkingRadarVisualizationPacket;
    private final ParkingRadarDisplayStatePacket parkingRadarDisplayStatePacket;
    private final ParkingDistancePacket leftParkingDistancePacket;
    private final ParkingDistancePacket rightParkingDistancePacket;
    private final ParkingRadarGuiStatePacket parkingRadarGuiStatePacket;
    private final SelectedDebugListPacket selectedDebugListPacket;

    // objects references for reference
    private AutomatedCar automatedCar;
    private World world;

    // distance calculation
    private float distanceLeft = 1f;
    private float distanceRight = 1f;

    private List<MovingWorldObject> elementsSeenByRadar;
    private Polygon radarTriangleLeft;
    private Polygon radarTriangleRight;

    public ParkingRadar(VirtualFunctionBus virtualFunctionBus, AutomatedCar automatedCar, World world) {
        super(virtualFunctionBus);
        parkingRadarDisplayStatePacket = new ParkingRadarDisplayStatePacket();
        virtualFunctionBus.parkingRadarDisplayStatePacket = parkingRadarDisplayStatePacket;
        parkingRadarVisualizationPacket = new ParkingRadarVisualizationPacket();
        virtualFunctionBus.parkingRadarVisualizationPacket = parkingRadarVisualizationPacket;
        leftParkingDistancePacket = new ParkingDistancePacket();
        rightParkingDistancePacket = new ParkingDistancePacket();
        virtualFunctionBus.leftParkingDistance = leftParkingDistancePacket;
        virtualFunctionBus.rightParkingDistance = rightParkingDistancePacket;
        parkingRadarGuiStatePacket = new ParkingRadarGuiStatePacket();
        virtualFunctionBus.parkingRadarGuiStatePacket = parkingRadarGuiStatePacket;
        selectedDebugListPacket = new SelectedDebugListPacket();
        virtualFunctionBus.selectedDebugListPacket = selectedDebugListPacket;
        this.automatedCar = automatedCar;
        this.world = world;
        elementsSeenByRadar = new ArrayList<>();
    }

    @Override
    public void loop() {
        calculateSensorPolygons();
        setParkingRadarHighglightOffOnElements();

        List<WorldObject> selectedInTriangle = getCollideableElementsInRadarTriangles();
        updateElementsSeenByRadar(selectedInTriangle);
        distanceLeft =
            calulateMinimumDistanceFromSensorPoint(ParkingRadarPositions.LEFT) / VisualizationConfig.METER_IN_PIXELS;
        distanceRight =
            calulateMinimumDistanceFromSensorPoint(ParkingRadarPositions.RIGHT) / VisualizationConfig.METER_IN_PIXELS;

        parkingRadarVisualizationPacket.setSensorColor(PARKING_RADAR_SENSOR_BG_COLOUR);
        leftParkingDistancePacket.setDistance(Math.round(distanceLeft * FOR_DIGIT) / FOR_DIGIT);
        rightParkingDistancePacket.setDistance(Math.round(distanceRight * FOR_DIGIT) / FOR_DIGIT);

        parkingRadarGuiStatePacket.setParkingRadarGuiState(
            isCarInReverse() && min(distanceRight, distanceLeft) <= 2.0f);
        parkingRadarDisplayStatePacket.setRadarDisplayState(true);

    }

    private float calulateMinimumDistanceFromSensorPoint(ParkingRadarPositions parkingRadarPosition) {
        float distance = Float.MAX_VALUE;
        Integer[] sourcePoint = getSourcePointOfRadarTriangle(parkingRadarPosition);
        if (sourcePoint != null) {
            for (WorldObject wo : elementsSeenByRadar) {
                if (wo.getPolygons().size() > 0) {
                    ArrayList<Path2D> woPolyInplace = ObjectTransform.transformPath2DPolygon(wo);
                    for (Path2D poly : woPolyInplace) {
                        float tempDistance = getShapeMinimumDistanceFromPoint(poly, sourcePoint);
                        if (tempDistance < distance) {
                            distance = tempDistance;
                        }
                    }
                }
            }
        }
        return distance;
    }

    private Integer[] getSourcePointOfRadarTriangle(ParkingRadarPositions parkingRadarPosition) {
        Integer[] sourcePoint;
        if (parkingRadarPosition == ParkingRadarPositions.LEFT && radarTriangleLeft.npoints > 0) {
            sourcePoint = new Integer[]{radarTriangleLeft.xpoints[0], radarTriangleLeft.ypoints[0]};
        } else if (parkingRadarPosition == ParkingRadarPositions.RIGHT && radarTriangleRight.npoints > 0) {
            sourcePoint = new Integer[]{radarTriangleRight.xpoints[0], radarTriangleRight.ypoints[0]};
        } else {
            return null;
        }
        return sourcePoint;
    }

    private float getShapeMinimumDistanceFromPoint(Path2D poly, Integer[] p1) {
        float distance = Float.MAX_VALUE;
        PathIterator it2 = poly.getPathIterator(null);
        while (!it2.isDone()) {
            float[] p2 = new float[2];
            it2.currentSegment(p2);
            float tempDistance = (float) Point2D.distance(p1[0], p1[1], p2[0], p2[1]);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            it2.next();
        }
        return distance;
    }


    /**
     * Gets the list of element that the two parking radar sensors can see
     *
     * @return the list of elements
     */
    private List<WorldObject> getCollideableElementsInRadarTriangles() {
        List<WorldObject> collideableElementsInTriangle
            = new ArrayList<>(getCollideableElementsInRadarTriangle(ParkingRadarPositions.LEFT));
        for (WorldObject obj : getCollideableElementsInRadarTriangle(ParkingRadarPositions.RIGHT)) {
            WorldObject wo = listContainsObject(obj, collideableElementsInTriangle);
            if (wo == null) {
                collideableElementsInTriangle.add(obj);

            }
        }
        return collideableElementsInTriangle;
    }

    /**
     * Checks if an object is already in the list
     *
     * @param object the object to check
     * @return the object reference in the list if found; null otherwise
     */
    private WorldObject listContainsObject(WorldObject object, List<WorldObject> list) {
        for (WorldObject mo : list) {
            if (object.getId().equals(mo.getId())) {
                return mo;
            }
        }
        return null;
    }


    /**
     * Gets the collideable objects inside the triangle defined by a polygon
     *
     * @param parkingRadarPosition left or right parking radar sensor triangle
     * @return the list of objects that are collideable and inside the triangle.
     */
    private List<WorldObject> getCollideableElementsInRadarTriangle(ParkingRadarPositions parkingRadarPosition) {

        Polygon radarTriangle =
            (parkingRadarPosition == ParkingRadarPositions.LEFT) ? radarTriangleLeft : radarTriangleRight;
        Point source = new Point((int) radarTriangle.xpoints[0], (int) radarTriangle.ypoints[0]);
        Point corner1 = new Point((int) radarTriangle.xpoints[1], (int) radarTriangle.ypoints[1]);
        Point corner2 = new Point((int) radarTriangle.xpoints[2], (int) radarTriangle.ypoints[2]);
        List<WorldObject> elementsInTriangle = world.getObjectsInsideTriangle(source, corner1, corner2);

        List<WorldObject> collideableElementsInTriangle = new ArrayList<>();
        for (WorldObject object : elementsInTriangle) {
            if (CommonSensorMethods.isCollideable(object)) {
                collideableElementsInTriangle.add(object);
                object.setHighlightedWhenParkinRadarIsOn(true);
            }
        }

        return collideableElementsInTriangle;
    }

    private void setParkingRadarHighglightOffOnElements() {
        for (WorldObject object : world.getWorldObjects()) {
            object.setHighlightedWhenParkinRadarIsOn(false);
        }
        for (WorldObject object : world.getDynamics()) {
            object.setHighlightedWhenParkinRadarIsOn(false);
        }
    }

    /**
     * Keeps the list of elements seen by radar up to date
     * by comparing it to the freshly requested elements
     *
     * @param elementsInRadarTriangle the fresh list of the elements seen by radar
     */
    private void updateElementsSeenByRadar(List<WorldObject> elementsInRadarTriangle) {
        for (WorldObject object : elementsInRadarTriangle) {
            // check if element is already stored with that id;
            MovingWorldObject mo = elementsSeenByRadarlistContainsObject(object);
            if (mo != null) {
                // if yes, update data
                mo.setX(object.getX());
                mo.setY(object.getY());
            } else {
                // if no, add
                if (CommonSensorMethods.isCollideable(object)) {
                    MovingWorldObject moveObject = new MovingWorldObject(object, virtualFunctionBus);
                    elementsSeenByRadar.add(moveObject);
                }
            }
        }

        // check if there are any elements that have gotten outside the radar triangle and needs to be removed
        for (int i = elementsSeenByRadar.size() - 1; i >= 0; i--) {
            MovingWorldObject mo = elementsSeenByRadar.get(i);
            if (!elementInNewRadarTriangleList(elementsInRadarTriangle, mo)) {
                elementsSeenByRadar.remove(mo);
            }
        }
    }

    /**
     * Checks if a {@link MovingWorldObject} is in the list of {@link WorldObject}
     *
     * @param selectedInTriangle The list of object to check against
     * @param movingWorldObject  The object to search for in the list
     * @return true if the objects id is found in the list; false otherwise
     */
    private boolean elementInNewRadarTriangleList(List<WorldObject> selectedInTriangle,
                                                  MovingWorldObject movingWorldObject) {
        for (WorldObject object : selectedInTriangle) {
            if (object.getId().equals(movingWorldObject.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an object is already in the elements seen by radar list
     *
     * @param object the object to check
     * @return the object reference in the list if found; null otherwise
     */
    private MovingWorldObject elementsSeenByRadarlistContainsObject(WorldObject object) {
        if (elementsSeenByRadar.size() > 0) {
            for (MovingWorldObject mo : elementsSeenByRadar) {
                if (object.getId().equals(mo.getId())) {
                    return mo;
                }
            }
        }
        return null;
    }

    /**
     * calculates the sensor polygons' current position and orientation
     */
    private void calculateSensorPolygons() {
        createSensorPolygon(ParkingRadarPositions.LEFT);
        createSensorPolygon(ParkingRadarPositions.RIGHT);
    }

    /**
     * creates one of the sensor polygons (left or right).
     *
     * @param parkingRadarPosition decides
     */
    private void createSensorPolygon(ParkingRadarPositions parkingRadarPosition) {
        Point2D source = getSource(parkingRadarPosition);
        Point2D corner1 = getCorner1(parkingRadarPosition);
        Point2D corner2 = getCorner2(parkingRadarPosition);

        Polygon tempTriangle = new Polygon(new int[]{
            (int) source.getX(), (int) corner1.getX(), (int) corner2.getX()},
            new int[]{(int) source.getY(), (int) corner1.getY(), (int) corner2.getY()},
            TRIANGLE_POLYGON_POINTS);
        if (parkingRadarPosition == ParkingRadarPositions.LEFT) {
            radarTriangleLeft = tempTriangle;
        } else {
            radarTriangleRight = tempTriangle;
        }
        parkingRadarVisualizationPacket.setSensorTriangle(parkingRadarPosition,
            source, corner1, corner2);
    }

    /**
     * Calculates the world position of the parking radar sensor polygon's source point
     *
     * @param parkingRadarPosition sets which sensor is calculated
     * @return The calculated source point
     */
    private Point2D getSource(ParkingRadarPositions parkingRadarPosition) {
        // get the x and y components of the segment line between the egocar's rotation origo
        // and the source of the radar triangle
        Point2D source;
        if (parkingRadarPosition == ParkingRadarPositions.RIGHT) {
            AffineTransform sourceT = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                -PARKING_RADAR_POS_X, -PARKING_RADAR_POS_Y);
            source = new Point2D.Double(automatedCar.getX() + PARKING_RADAR_POS_X + sourceT.getTranslateX(),
                automatedCar.getY() + PARKING_RADAR_POS_Y + sourceT.getTranslateY());
        } else {
            AffineTransform sourceT = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                PARKING_RADAR_POS_X, -PARKING_RADAR_POS_Y);
            source = new Point2D.Double(automatedCar.getX() - PARKING_RADAR_POS_X + sourceT.getTranslateX(),
                automatedCar.getY() + PARKING_RADAR_POS_Y + sourceT.getTranslateY());
        }

        return source;

    }

    /**
     * Calculates the world position of the parking radar sensor polygon's second point
     *
     * @param parkingRadarPosition sets which sensor is calculated
     * @return The calculated second point
     */
    private Point2D getCorner1(ParkingRadarPositions parkingRadarPosition) {
        // get the x and y components of the segment line between the egocars rotation origo
        // and the corner of the radar triangle
        int corner1DiffX = PARKING_RADAR_POS_X + PARKING_RADAR_TRIANGLE_HALF_X;
        int corner1DiffY = PARKING_RADAR_POS_Y + PARKING_RADAR_SENSOR_RANGE;
        Point2D corner1;
        if (parkingRadarPosition == ParkingRadarPositions.RIGHT) {
            AffineTransform corner1T = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                -corner1DiffX, -corner1DiffY);
            corner1 = new Point2D.Double(automatedCar.getX() + corner1DiffX + corner1T.getTranslateX(),
                automatedCar.getY() + corner1DiffY + corner1T.getTranslateY());
        } else {
            AffineTransform corner1T = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                corner1DiffX, -corner1DiffY);
            corner1 = new Point2D.Double(automatedCar.getX() - corner1DiffX + corner1T.getTranslateX(),
                automatedCar.getY() + corner1DiffY + corner1T.getTranslateY());
        }
        return corner1;
    }

    /**
     * Calculates the world position of the parking radar sensor polygon's third point
     *
     * @param parkingRadarPosition sets which sensor is calculated
     * @return The calculated third point
     */
    private Point2D getCorner2(ParkingRadarPositions parkingRadarPosition) {
        // get the x and y components of the segment line between the egocars rotation origo
        // and the corner of the radar triangle
        int corner2DiffX = PARKING_RADAR_POS_X - PARKING_RADAR_TRIANGLE_HALF_X;
        int corner2DiffY = PARKING_RADAR_POS_Y + PARKING_RADAR_SENSOR_RANGE;
        Point2D corner2;
        if (parkingRadarPosition == ParkingRadarPositions.RIGHT) {
            AffineTransform corner1T = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                -corner2DiffX, -corner2DiffY);
            corner2 = new Point2D.Double(automatedCar.getX() + corner2DiffX + corner1T.getTranslateX(),
                automatedCar.getY() + corner2DiffY + corner1T.getTranslateY());
        } else {
            AffineTransform corner1T = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                corner2DiffX, -corner2DiffY);
            corner2 = new Point2D.Double(automatedCar.getX() - corner2DiffX + corner1T.getTranslateX(),
                automatedCar.getY() + corner2DiffY + corner1T.getTranslateY());
        }
        return corner2;
    }

    private boolean isCarInReverse() {
        return automatedCar.getPowerTrain().transmission.getCurrentTransmissionMode() == CarTransmissionMode.R_REVERSE;
    }
}

