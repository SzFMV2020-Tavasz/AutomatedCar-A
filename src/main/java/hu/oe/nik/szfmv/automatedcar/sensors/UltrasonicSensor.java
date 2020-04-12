package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
// import hu.oe.nik.szfmv.automatedcar.visualization.VisualizationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.List;

public class UltrasonicSensor extends SystemComponent {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    // private static final  double SONIC_SENSOR_ANGLE = Math.toRadians(100);
    // private static final int SONIC_SENSOR_RANGE = 3 * VisualizationConfig.METER_IN_PIXELS;
    // private static final Color SONIC_SENSOR_GB_COLOR = new Color(124,255,0,100);
    // private static final int TRIANGLE_POLYGON_POINTS = 3;

    private List<WorldObject> worldObjects;

    // private int sensor_id;
    private Point location;
    private Point corner1;
    private Point corner2;
    private World world;

    public Point getLocation() {
        return location;
    }

    protected UltrasonicSensor(VirtualFunctionBus virtualFunctionBus, World world) {
        super(virtualFunctionBus);
        this.world = world;
    }

    @Override
    public void loop() {
        // UPDATE sensor:
        // position
        // elements in triangle
        worldObjects = getCollideableElementInSonicTriangle(location, corner1, corner2);
        LOGGER.debug(getSonicAndWorldObjectDistance(worldObjects.get(0)));

        // visual
    }

    private List<WorldObject> getCollideableElementInSonicTriangle(Point sonic, Point corner1, Point corner2){
        List<WorldObject> elementInTriangle = world.getObjectsInsideTriangle(sonic, corner1, corner2);
        return  elementInTriangle;
    }

    private double getSonicAndWorldObjectDistance(WorldObject object){
        return calculateDistanceBetweenTwoPoints(this.getLocation(), new Point(object.getX(), object.getY()));
    }

    private double calculateDistanceBetweenTwoPoints(Point a, Point b){
        return Math.sqrt(Math.pow(a.x -b.x, 2) + Math.pow(a.y - b.y, 2));
    }
}
