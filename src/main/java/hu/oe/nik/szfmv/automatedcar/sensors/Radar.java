package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.RadarDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.RadarVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.SelectedDebugListPacket;
import hu.oe.nik.szfmv.automatedcar.visualization.VisualizationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Radar element class for holding the radar triangle data and the radar functions
 */
public class Radar extends SystemComponent {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    // radar sensor triangle data
    private static final double RADAR_SENSOR_ANGLE = Math.PI / 180 * 30;  // 60 /2 - half angle
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
    private final SelectedDebugListPacket selectedDebugListPacket;

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
        selectedDebugListPacket = new SelectedDebugListPacket();
        virtualFunctionBus.selectedDebugListPacket = selectedDebugListPacket;

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
        showElementsInTriangle(selectedDebugListPacket);

        // send radar display data
        radarVisualizationPacket.setSensorTriangle(source, corner1, corner2, RADAR_SENSOR_BG_COLOUR);
        radarDisplayStatePacket.setRadarDisplayState(true);

    }

    /**
     * Keepst the list of elements seen by radar up to date
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
    private boolean elementInNewRadarTriangleList(List<WorldObject> selectedInTriangle,
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
     *
     * @param selectedDebugListPacket the packet that will pass o the list
     */
    private void showElementsInTriangle(SelectedDebugListPacket selectedDebugListPacket) {
        // select elemets for debugpolygons that are collideable and in the triangle
        List<String> selectedIds = new ArrayList<>();
        for (WorldObject object : elementsSeenByRadar) {
            selectedIds.add(object.getId());
        }
        selectedDebugListPacket.setDebugListElements(selectedIds);
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
        int corner1DiffY = (RADAR_SENSOR_DY - RADAR_SENSOR_RANGE);
        AffineTransform corner1T = AffineTransform.getRotateInstance(automatedCar.getRotation(),
                -corner1DiffX, -corner1DiffY);
        Point2D corner1 = new Point2D.Double(automatedCar.getX() + corner1DiffX + corner1T.getTranslateX(),
                automatedCar.getY() + corner1DiffY + corner1T.getTranslateY());
        int X = automatedCar.getX();
        int Y = automatedCar.getY();
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
        int corner2DiffY = (RADAR_SENSOR_DY - RADAR_SENSOR_RANGE);
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
