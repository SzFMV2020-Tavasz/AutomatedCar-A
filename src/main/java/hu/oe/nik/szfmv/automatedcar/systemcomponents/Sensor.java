package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.sensors.MovingWorldObject;
import hu.oe.nik.szfmv.automatedcar.sensors.ObjectTransform;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.DebugModePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.UltrasoundDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.UltrasoundsVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.visualization.UltrasoundPositions;
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

public class Sensor extends SystemComponent {


    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final int RADAR_RANGE_IN_METER = 3;
    private static final double RADAR_SENSOR_ANGLE = Math.PI / 180 * 50;// 50°
    //  private static final Color RADAR_SENSOR_BG_COLOUR = new Color(255, 0, 0, 125);
    private static final int TRIANGLE_POLYGON_POINTS = 3;
    private static final int RADAR_SENSOR_RANGE = RADAR_RANGE_IN_METER * VisualizationConfig.METER_IN_PIXELS;
    // radar sensor triangle data
    private static final int RADAR_TRIANGLE_HALF_X = (int) (Math.tan(RADAR_SENSOR_ANGLE) * RADAR_SENSOR_RANGE);
    // when the egocar is looking north
    private final int RADAR_SENSOR_DX;
    private final int RADAR_SENSOR_DY;
    private final DebugModePacket debugModePacket;
    private UltrasoundsVisualizationPacket ultrasoundsVisualizationPacket;
    private UltrasoundDisplayStatePacket ultrasoundDisplayStatePacket;
    // objects references for reference(eh...)
    private AutomatedCar automatedCar;
    private World world;

    private java.util.List<MovingWorldObject> elementsSeenByRadar;

    private Polygon radarPolygon;

    private UltrasoundPositions sensorPosition;

    public Sensor(VirtualFunctionBus virtualFunctionBus, AutomatedCar automatedCar, World world, Point startPoint,
                  UltrasoundPositions sensorPosition, UltrasoundDisplayStatePacket ultrasoundDisplayStatePacket,
                  UltrasoundsVisualizationPacket ultrasoundsVisualizationPacket) {
        super(virtualFunctionBus);
        RADAR_SENSOR_DX = (int) startPoint.getX();
        RADAR_SENSOR_DY = (int) startPoint.getY();
        this.ultrasoundsVisualizationPacket = ultrasoundsVisualizationPacket;
        virtualFunctionBus.ultrasoundsVisualizationPacket = ultrasoundsVisualizationPacket;
        this.ultrasoundDisplayStatePacket = ultrasoundDisplayStatePacket;
        virtualFunctionBus.ultrasoundDisplayStatePacket = ultrasoundDisplayStatePacket;
        debugModePacket = new DebugModePacket();
        virtualFunctionBus.debugModePacket = debugModePacket;
        this.sensorPosition = sensorPosition;

        this.automatedCar = automatedCar;
        this.world = world;
        this.elementsSeenByRadar = new ArrayList<>();
    }

    /**
     * Should be changed later when actual data arrives from the CarPositionPacket
     */
    @Override
    public void loop() {
        // calculate radar polygon's current position
        calculateDefaultPolygon();
        Point source = new Point((int) radarPolygon.xpoints[0], (int) radarPolygon.ypoints[0]);
        Point corner1 = new Point((int) radarPolygon.xpoints[1], (int) radarPolygon.ypoints[1]);
        Point corner2 = new Point((int) radarPolygon.xpoints[2], (int) radarPolygon.ypoints[2]);

        // get elements in triangle
        java.util.List<WorldObject> selectedInTriangle = getCollideableElementsInRadarTriangle(source, corner1, corner2);
        updateElementsSeenByRadar(selectedInTriangle);
        showNearestElementInTriangle();

        // send radar display data
        ultrasoundsVisualizationPacket.setSensorTriangle(sensorPosition, source, corner1, corner2);
        ultrasoundDisplayStatePacket.setUltrasoundDisplayState(this.virtualFunctionBus.guiInputPacket.getDebugSwitch());

    }

    /**
     * Keepst the list of elements seen by radar up to date
     * by comparing it to the freshly requested elements
     *
     * @param elementsInRadarTriangle the fresh list of the elements seen by radar
     */
    private void updateElementsSeenByRadar(java.util.List<WorldObject> elementsInRadarTriangle) {
        for (WorldObject object : elementsInRadarTriangle) {
            // check if element is already stored with that id;
            MovingWorldObject mo = elementsSeenByRadarlistContainsObject(object);
            if (mo != null) {
                // if yes, update data
                mo.setX(object.getX());
                mo.setY(object.getY());
            } else {
                // if no, add
                MovingWorldObject moveObject = new MovingWorldObject(object, virtualFunctionBus);
                elementsSeenByRadar.add(moveObject);
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
     * Checks if an object is already in the elements seeen by radar list
     *
     * @param object the object to check
     * @return the object reference in the list if found; null otherwise
     */
    private MovingWorldObject elementsSeenByRadarlistContainsObject(WorldObject object) {
        for (MovingWorldObject mo : elementsSeenByRadar) {
            if (object.getId() == mo.getId()) {
                return mo;
            }
        }
        return null;
    }

    /**
     * Checks if a {@link MovingWorldObject} is in the list of {@link WorldObject}
     *
     * @param selectedInTriangle The list of object to check against
     * @param movingWorldObject  The object to search for in the list
     * @return true if the objects id is found in the list; false otherwise
     */
    private boolean elementInNewRadarTriangleList(java.util.List<WorldObject> selectedInTriangle,
                                                  MovingWorldObject movingWorldObject) {
        for (WorldObject object : selectedInTriangle) {
            if (object.getId() == movingWorldObject.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the list of elements that are shown with different color inside the radar triangle
     * Gets the geometrically nearest element and passes it to the selectedDebuglistpacket
     */
    private void showNearestElementInTriangle() {
        // turn off highlight on all world elements to avoid stuck highlights
        setRadarHighglightOffOnElements();
        // show nearest element in triangle
        MovingWorldObject nearestElement = getNearestCollideableElement();
        if (nearestElement != null) {
            nearestElement.getWorldObject().setHighlightedWhenRadarIsOn(true);
        }
    }

    private void setRadarHighglightOffOnElements() {
        for (WorldObject object : world.getWorldObjects()) {
            object.setHighlightedWhenRadarIsOn(false);
        }
        for (WorldObject object : world.getDynamics()) {
            object.setHighlightedWhenRadarIsOn(false);
        }
    }

    /**
     * Calculates the nearest collideable element. No lateral offset, just the geometically nearest object
     * based on the debug polygons
     *
     * @return the nearest object if exists, null if none seen by radar
     */
    public MovingWorldObject getNearestCollideableElement() {
        MovingWorldObject nearestObject = null;
        double distance = Double.MAX_VALUE;
        if (automatedCar.getPolygons().size() > 0) {
            Path2D egocarPolyInPlace = ObjectTransform.transformPath2DPolygon(automatedCar).get(0);
            for (MovingWorldObject mo : elementsSeenByRadar) {
                for (Path2D moPolyInplace : ObjectTransform.transformPath2DPolygon(mo)) {
                    double moDistance = calculateMinimumDistance(moPolyInplace, egocarPolyInPlace);
                    if (moDistance < Double.MAX_VALUE && moDistance < distance) {
                        distance = moDistance;
                        nearestObject = mo;
                    }
                }
            }
        }

        return nearestObject != null ? nearestObject : null;
    }

    private double calculateMinimumDistance(Path2D poly1, Path2D poly2) {
        double distance = Double.MAX_VALUE;

        // check all poly1 points against all poly points and select the smallest distance
        PathIterator it1 = poly1.getPathIterator(null);
        while (!it1.isDone()) {
            float[] p1 = new float[2];
            it1.currentSegment(p1);
            double tempDistance = getShapeMinimumDistanceFromPoint(poly2, p1);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            it1.next();
        }
        return distance;
    }


    private double getShapeMinimumDistanceFromPoint(Shape poly, float[] p1) {
        double distance = Double.MAX_VALUE;
        PathIterator it2 = poly.getPathIterator(null);
        while (!it2.isDone()) {
            float[] p2 = new float[2];
            it2.currentSegment(p2);
            double tempDistance = Point2D.distance(p1[0], p1[1], p2[0], p2[1]);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            it2.next();
        }
        return distance;
    }


    /**
     * Gets the collideable objects inside the triangle defined by the 3 points
     *
     * @param source  source point of the triangle
     * @param corner1 second point of the triangle
     * @param corner2 third point of the triangle
     * @return the list of objects that are collideable and inside the triangle.
     */
    private java.util.List<WorldObject> getCollideableElementsInRadarTriangle(Point source, Point corner1, Point corner2) {
        java.util.List<WorldObject> elementsInTriangle = world.getObjectsInsideTriangle(source, corner1, corner2);

        List<WorldObject> collideableElementsInTriangle = new ArrayList<>();
        for (WorldObject object : elementsInTriangle) {
            if (isCollideable(object)) {
                collideableElementsInTriangle.add(object);
            }
        }

        return collideableElementsInTriangle;
    }

    /**
     * Calculates the world position of the radar sensor polygon according to the position of the egocar
     * and sets the class's radarpolygon's coordinates
     */
    private void calculateDefaultPolygon() {
        // source point
        Point2D source = getSource();
        Point2D corner1 = getCorner1();
        Point2D corner2 = getCorner2();

        radarPolygon = new Polygon(new int[]{(int) source.getX(), (int) corner1.getX(), (int) corner2.getX()},
                new int[]{(int) source.getY(), (int) corner1.getY(), (int) corner2.getY()}, TRIANGLE_POLYGON_POINTS);
    }

    /**
     * Calculates the world position of the radar sensor polygon's source point
     *
     * @return The calculated source point
     */
    private Point2D getSource() {
        // get the x and y components of the segment line between the egocar's rotation origo
        // and the source of the radar triangle
        AffineTransform sourceT = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                -RADAR_SENSOR_DX, -RADAR_SENSOR_DY);
        Point2D source = new Point2D.Double(automatedCar.getX() + RADAR_SENSOR_DX + sourceT.getTranslateX(),
                automatedCar.getY() + RADAR_SENSOR_DY + sourceT.getTranslateY());
        return source;
    }

    /**
     * Calculates the world position of the radar sensor polygon's second point
     *
     * @return The calculated second point
     */
    private Point2D getCorner1() {
        // get the x and y components of the segment line between the egocars rotation origo
        // and the corner of the radar triangle

        int corner1DiffX = 0;
        int corner1DiffY = 0;

        // look forward
        if (this.sensorPosition == UltrasoundPositions.FRONT_LEFT || this.sensorPosition == UltrasoundPositions.FRONT_RIGHT) {
            corner1DiffX = (RADAR_SENSOR_DX + RADAR_TRIANGLE_HALF_X);
            corner1DiffY = (RADAR_SENSOR_DY - RADAR_SENSOR_RANGE);
        }
        // look left
        else if (this.sensorPosition == UltrasoundPositions.FRONT_LEFT_SIDE || this.sensorPosition == UltrasoundPositions.REAR_LEFT_SIDE) {
            corner1DiffX = (RADAR_SENSOR_DX - RADAR_SENSOR_RANGE);
            corner1DiffY = (RADAR_SENSOR_DY - RADAR_TRIANGLE_HALF_X);
        }
        // look right
        else if (this.sensorPosition == UltrasoundPositions.FRONT_RIGHT_SIDE || this.sensorPosition == UltrasoundPositions.REAR_RIGHT_SIDE) {
            corner1DiffX = (RADAR_SENSOR_DX + RADAR_SENSOR_RANGE);
            corner1DiffY = (RADAR_SENSOR_DY + RADAR_TRIANGLE_HALF_X);
        }
        // look backward
        else if (this.sensorPosition == UltrasoundPositions.REAR_LEFT || this.sensorPosition == UltrasoundPositions.REAR_RIGHT) {
            corner1DiffX = (RADAR_SENSOR_DX + RADAR_TRIANGLE_HALF_X);
            corner1DiffY = (RADAR_SENSOR_DY + RADAR_SENSOR_RANGE);
        }

        AffineTransform corner1T = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                -corner1DiffX, -corner1DiffY);
        Point2D corner1 = new Point2D.Double(automatedCar.getX() + corner1DiffX + corner1T.getTranslateX(),
                automatedCar.getY() + corner1DiffY + corner1T.getTranslateY());
        return corner1;
    }

    /**
     * Calculates the world position of the radar sensor polygon's third point
     *
     * @return The calculated third point
     */
    private Point2D getCorner2() {
        // get the x and y components of the segment line between the egocars rotation origo
        // and the corner of the radar triangle
        int corner2DiffX = 0;
        int corner2DiffY = 0;
        // look forward
        if (this.sensorPosition == UltrasoundPositions.FRONT_LEFT || this.sensorPosition == UltrasoundPositions.FRONT_RIGHT) {
            corner2DiffX = (RADAR_SENSOR_DX - RADAR_TRIANGLE_HALF_X);
            corner2DiffY = (RADAR_SENSOR_DY - RADAR_SENSOR_RANGE);
        }
        // look left
        else if (this.sensorPosition == UltrasoundPositions.FRONT_LEFT_SIDE || this.sensorPosition == UltrasoundPositions.REAR_LEFT_SIDE) {
            corner2DiffX = (RADAR_SENSOR_DX - RADAR_SENSOR_RANGE);
            corner2DiffY = (RADAR_SENSOR_DY + RADAR_TRIANGLE_HALF_X);
        }
        // look right
        else if (this.sensorPosition == UltrasoundPositions.FRONT_RIGHT_SIDE || this.sensorPosition == UltrasoundPositions.REAR_RIGHT_SIDE) {
            corner2DiffX = (RADAR_SENSOR_DX + RADAR_SENSOR_RANGE);
            corner2DiffY = (RADAR_SENSOR_DY - RADAR_TRIANGLE_HALF_X);
        }
        // look backward
        else if (this.sensorPosition == UltrasoundPositions.REAR_LEFT || this.sensorPosition == UltrasoundPositions.REAR_RIGHT) {
            corner2DiffX = (RADAR_SENSOR_DX - RADAR_TRIANGLE_HALF_X);
            corner2DiffY = (RADAR_SENSOR_DY + RADAR_SENSOR_RANGE);
        }

        AffineTransform corner1T = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                -corner2DiffX, -corner2DiffY);
        Point2D corner2 = new Point2D.Double(automatedCar.getX() + corner2DiffX + corner1T.getTranslateX(),
                automatedCar.getY() + corner2DiffY + corner1T.getTranslateY());
        return corner2;
    }

    /**
     * Checks whether an element is collideable by checking its Z value.
     * <p>
     * Everything with a Z value higher than 0 is collidable
     *
     * @param object The {@link WorldObject} to check
     * @return True if the object is not on the not collideable element's list.
     */
    private boolean isCollideable(WorldObject object) {
        return object.getZ() >= 1;
    }

    public Point getTriangleSource() {
        Point2D cache = getSource();
        return new Point((int) cache.getX(), (int) cache.getY());
    }

    public Point getTriangleCorner1() {
        Point2D cache = getCorner1();
        return new Point((int) cache.getX(), (int) cache.getY());
    }

    public Point getTriangleCorner2() {
        Point2D cache = getCorner2();
        return new Point((int) cache.getX(), (int) cache.getY());
    }

    public UltrasoundPositions getSensorPosition() {
        return sensorPosition;
    }
}
