package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.DebugModePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.RadarDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.RadarVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.visualization.VisualizationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Radar element class for holding the radar triangle data and the radar functions
 */
public class Radar extends SystemComponent {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    // radar sensor triangle data
    private static final double RADAR_SENSOR_ANGLE = Math.PI / 6;  // 60 /2 - half angle
    private static final int RADAR_SENSOR_RANGE = 200 * VisualizationConfig.METER_IN_PIXELS;
    private static final Color RADAR_SENSOR_BG_COLOUR = new Color(121, 0, 0, 125);
    private static final int TRIANGLE_POLYGON_POINTS = 3;

    // when the egocar is looking north
    private static final int RADAR_SENSOR_DX = 0;
    private static final int RADAR_SENSOR_DY = -104;
    private static final int RADAR_TRIANGLE_HALF_X = (int) (Math.tan(RADAR_SENSOR_ANGLE) * RADAR_SENSOR_RANGE);

    // packets for sending data
    private final RadarVisualizationPacket radarVisualizationPacket;
    private final RadarDisplayStatePacket radarDisplayStatePacket;
    // will be removed later when HMI will be setting this switch
    private final DebugModePacket debugModePacket;

    // objects references for reference(eh...)
    private AutomatedCar automatedCar;
    private World world;

    private List<MovingWorldObject> elementsSeenByRadar;

    private Polygon radarPolygon;

    public Radar(VirtualFunctionBus virtualFunctionBus, AutomatedCar automatedCar, World world) {
        super(virtualFunctionBus);
        radarVisualizationPacket = new RadarVisualizationPacket();
        virtualFunctionBus.radarVisualizationPacket = radarVisualizationPacket;
        radarDisplayStatePacket = new RadarDisplayStatePacket();
        virtualFunctionBus.radarDisplayStatePacket = radarDisplayStatePacket;
        debugModePacket = new DebugModePacket();
        virtualFunctionBus.debugModePacket = debugModePacket;
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
        List<WorldObject> selectedInTriangle = getCollideableElementsInRadarTriangle(source, corner1, corner2);
        updateElementsSeenByRadar(selectedInTriangle);
        showNearestElementInTriangle();

        // send radar display data
        radarVisualizationPacket.setSensorTriangle(source, corner1, corner2, RADAR_SENSOR_BG_COLOUR);
        radarDisplayStatePacket.setRadarDisplayState(true);

        // turn on debug mode - left here for debugging purposes
        virtualFunctionBus.debugModePacket.setDebuggingState(false);

    }

    /**
     * Returns the list of objects that are moving towards the egocar and will collide if nothing changes
     *
     * @return the list of relevant objects for Automatic Emergency Break system
     */
    public List<WorldObject> getRelevantObjectsForAEB() {
        List<WorldObject> returnList = new ArrayList<>();
        for (MovingWorldObject mo : elementsSeenByRadar) {
            // object if relevant if a) the relative movement vector is pointing towards the car
            // b) if the relative movement vector line intersects the car
            if (doesObjectMovementVectorPointTowardsTheEgocar(mo)) {
                Line2D movementVectorLine = new Line2D.Double(mo.getX(), mo.getY(),
                    mo.getX() + mo.getRelativeMovementVectorX(), mo.getY() + mo.getRelativeMovementVectorY());
                Line2D extendedLine = extendLineToReachBeyondPolygon(mo.getPolygon(), movementVectorLine);
                if (doesLineIntersectPolygon(extendedLine, ObjectTransform.transformPolygon(automatedCar))) {
                    returnList.add(mo.getWorldObject());
                }
            }
        }
        return returnList;
    }

    /**
     * Checks whether the given line intersects the polygon at all
     *
     * @param line    the line to check
     * @param polygon the polygon to check against
     * @return true if the line intersects the rectangle; false otherwise
     */
    private boolean doesLineIntersectPolygon(Line2D line, Polygon polygon) {
        if (polygon != null && !line.getP1().equals(line.getP2())) {
            // loop though the polygon
            for (int i = 0; i < polygon.npoints; i++) {
                if (i < polygon.npoints - 1) {
                    Line2D polygonSegment = new Line2D.Double(polygon.xpoints[i], polygon.ypoints[i],
                        polygon.xpoints[i + 1], polygon.ypoints[i + 1]);
                    if (polygonSegment.intersectsLine(line)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the object's movement vector is pointing towards the egocar
     *
     * @return true if it is pointing towards the car; false otherwise
     */
    private boolean doesObjectMovementVectorPointTowardsTheEgocar(MovingWorldObject mo) {
        // If the movement vector's end is closer to the egocar than it's source point
        // then it is pointing towards the egocar
        Point2D vectorStart = new Point2D.Double(mo.getX(), mo.getY());
        Point2D vectorEnd = new Point2D.Double(mo.getX() + mo.getRelativeMovementVectorX(),
            mo.getY() + mo.getRelativeMovementVectorY());

        Point2D egocarPos = new Point2D.Double(automatedCar.getX(), automatedCar.getY());
        if (egocarPos.distance(vectorStart) > egocarPos.distance(vectorEnd)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Extends a segment line to behave like a line from the polygon's point of view
     * ( to intersect it if the line would intersect it)
     *
     * @param polygon the polygon that may be intersected
     * @param line    the line to extend
     * @return an extended line
     */
    private Line2D extendLineToReachBeyondPolygon(Polygon polygon, Line2D line) {
        if (polygon != null) {
            // loop through the polygon's points and get the farthest one from the line's first point
            Point furthestPoint = new Point(polygon.xpoints[0], polygon.ypoints[0]);
            for (int i = 0; i < polygon.npoints; i++) {
                Point currentPoint = new Point(polygon.xpoints[i], polygon.ypoints[i]);
                if (currentPoint.distance(line.getP1()) > furthestPoint.distance(line.getP1())) {
                    furthestPoint = currentPoint;
                }
            }
            return extendLineBeyondPoint(furthestPoint, line);
        } else {
            return line;
        }
    }

    private Line2D extendLineBeyondPoint(Point furthestPoint, Line2D line) {
        // extend the line - first end
        double lineDeltaX = line.getX2() - line.getX1();
        double lineDeltaY = line.getY2() - line.getY1();
        double newP2X = line.getX1() + (line.getX1() - furthestPoint.getX()) * 2;
        double newP2Y = line.getY2();
        if (lineDeltaX != 0) {
            newP2Y = line.getY1() + (line.getX1() - furthestPoint.getX()) * 2  * lineDeltaY / lineDeltaX;
        }
        // extend the line - first end
        double newP1X = line.getX1() - (line.getX1() - furthestPoint.getX()) * 2;
        double newP1Y = line.getY1();
        if (lineDeltaX != 0) {
            newP1Y = line.getY1() - (line.getX1() - furthestPoint.getX()) * 2  * lineDeltaY / lineDeltaX;
        }
        return new Line2D.Double(newP1X, newP1Y, newP2X, newP2Y);
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
                if (isCollideable(object)) {
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
     * Checks if an object is already in the elements seeen by radar list
     *
     * @param object the object to check
     * @return the object reference in the list if found; null otherwise
     */
    private MovingWorldObject elementsSeenByRadarlistContainsObject(WorldObject object) {
        for (MovingWorldObject mo : elementsSeenByRadar) {
            if (object.getId().equals(mo.getId())) {
                return mo;
            }
        }
        return null;
    }

    /**
     * Returns the list of {@link MovingWorldObject} that is collideable and seen by the radar.
     * Collideable means that the object's Z value is bigger than 0.
     *
     * @return The list of objects.
     */
    public List<MovingWorldObject> getObjectsSeenByRadar() {
        return elementsSeenByRadar;
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
        for (MovingWorldObject mo : elementsSeenByRadar) {
            if (mo.getPolygon() != null) {
                Shape moPolyInplace = ObjectTransform.transformPolygon(mo);
                Shape egocarPolyInPlace = ObjectTransform.transformPolygon(automatedCar);
                double moDistance = calculateMinimumDistance(moPolyInplace, mo.getPolygon().npoints,
                    egocarPolyInPlace, automatedCar.getPolygon().npoints);
                if (moDistance < Double.MAX_VALUE && moDistance < distance) {
                    distance = moDistance;
                    nearestObject = mo;
                }
            }
        }

        return nearestObject != null ? nearestObject : null;
    }

    private double calculateMinimumDistance(Shape poly1, int poly1N, Shape poly2, int poly2N) {
        double distance = Double.MAX_VALUE;

        // check all poly1 points against all poly points and select the smallest distance
        PathIterator it1 = poly1.getPathIterator(null);
        int it1Index = 0;
        while (it1Index < poly1N && !it1.isDone()) {
            float[] p1 = new float[2];
            it1.currentSegment(p1);
            double tempDistance = getShapeMinimumDistanceFromPoint(poly2, poly2N, p1);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            it1.next();
            it1Index++;
        }
        return distance;
    }

    private double getShapeMinimumDistanceFromPoint(Shape poly, int polyN, float[] p1) {
        double distance = Double.MAX_VALUE;
        PathIterator it2 = poly.getPathIterator(null);
        int it2Index = 0;
        while (it2Index < polyN && !it2.isDone()) {
            float[] p2 = new float[2];
            it2.currentSegment(p2);
            double tempDistance = Point2D.distance(p1[0], p1[1], p2[0], p2[1]);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            it2.next();
            it2Index++;
        }
        return distance;
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

    /**
     * Gets the collideable objects inside the triangle defined by the 3 points
     *
     * @param source  source point of the triangle
     * @param corner1 second point of the triangle
     * @param corner2 third point of the triangle
     * @return the list of objects that are collideable and inside the triangle.
     */
    private List<WorldObject> getCollideableElementsInRadarTriangle(Point source, Point corner1, Point corner2) {
        List<WorldObject> elementsInTriangle = world.getObjectsInsideTriangle(source, corner1, corner2);

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
        int corner1DiffX = (RADAR_SENSOR_DX + RADAR_TRIANGLE_HALF_X);
        int corner1DiffY = -(RADAR_SENSOR_DY + RADAR_SENSOR_RANGE);
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
        int corner2DiffX = (RADAR_SENSOR_DX - RADAR_TRIANGLE_HALF_X);
        int corner2DiffY = -(RADAR_SENSOR_DY + RADAR_SENSOR_RANGE);
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

}
