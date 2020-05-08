package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.UltrasoundDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.UltrasoundsVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.visualization.UltrasoundPositions;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ultrasonic {

    /*
      The front right sensor
      private static final int RADAR_SENSOR_DX = 40;
      private static final int RADAR_SENSOR_DY = -90;

      the front left sensor
      private static final int RADAR_SENSOR_DX = -40;
      private static final int RADAR_SENSOR_DY = -90;

      the rear right sensor
      private static final int RADAR_SENSOR_DX = 40;
      private static final int RADAR_SENSOR_DY = 90;

      the rear left sensor
      private static final int RADAR_SENSOR_DX = -40;
      private static final int RADAR_SENSOR_DY = 90;

  */
    private final static Point FRONT_RIGHT_SENSOR = new Point(40, -90);
    private final static Point FRONT_LEFT_SENSOR = new Point(-40, -90);
    private final static Point REAR_RIGHT_SENSOR = new Point(40, 90);
    private final static Point REAR_LEFT_SENSOR = new Point(-40, 90);

    private final static UltrasoundsVisualizationPacket ultrasoundsVisualizationPacket = new UltrasoundsVisualizationPacket();
    private final static UltrasoundDisplayStatePacket ultrasoundDisplayPacket = new UltrasoundDisplayStatePacket();
    private final VirtualFunctionBus virtualFunctionBus;
    private final AutomatedCar automatedCar;
    private final World world;
    private List<Sensor> sensors = new ArrayList<>();
    private List<WorldObject> collidableObjectsInRange = new ArrayList<>();
    private WorldObject nearestCollidableObject = null;

    public Ultrasonic(VirtualFunctionBus virtualFunctionBus, AutomatedCar automatedCar, World world) {
        this.virtualFunctionBus = virtualFunctionBus;
        this.automatedCar = automatedCar;
        this.world = world;
        sensors.add(new Sensor(this.virtualFunctionBus, this.automatedCar, this.world, FRONT_RIGHT_SENSOR,
                UltrasoundPositions.FRONT_RIGHT, ultrasoundDisplayPacket, ultrasoundsVisualizationPacket));
        sensors.add(new Sensor(this.virtualFunctionBus, this.automatedCar, this.world, FRONT_LEFT_SENSOR,
                UltrasoundPositions.FRONT_LEFT, ultrasoundDisplayPacket, ultrasoundsVisualizationPacket));
        sensors.add(new Sensor(this.virtualFunctionBus, this.automatedCar, this.world, REAR_RIGHT_SENSOR,
                UltrasoundPositions.REAR_RIGHT, ultrasoundDisplayPacket, ultrasoundsVisualizationPacket));
        sensors.add(new Sensor(this.virtualFunctionBus, this.automatedCar, this.world, REAR_LEFT_SENSOR,
                UltrasoundPositions.REAR_LEFT, ultrasoundDisplayPacket, ultrasoundsVisualizationPacket));
        sensors.add(new Sensor(this.virtualFunctionBus, this.automatedCar, this.world, FRONT_RIGHT_SENSOR,
                UltrasoundPositions.FRONT_RIGHT_SIDE, ultrasoundDisplayPacket, ultrasoundsVisualizationPacket));
        sensors.add(new Sensor(this.virtualFunctionBus, this.automatedCar, this.world, FRONT_LEFT_SENSOR,
                UltrasoundPositions.FRONT_LEFT_SIDE, ultrasoundDisplayPacket, ultrasoundsVisualizationPacket));
        sensors.add(new Sensor(this.virtualFunctionBus, this.automatedCar, this.world, REAR_RIGHT_SENSOR,
                UltrasoundPositions.REAR_RIGHT_SIDE, ultrasoundDisplayPacket, ultrasoundsVisualizationPacket));
        sensors.add(new Sensor(this.virtualFunctionBus, this.automatedCar, this.world, REAR_LEFT_SENSOR,
                UltrasoundPositions.REAR_LEFT_SIDE, ultrasoundDisplayPacket, ultrasoundsVisualizationPacket));


    }

    private double distanceBetweenTwoPoints(int Xone, int Yone, int Xtwo, int Ytwo) {
        return Math.sqrt((Xtwo - Xone) * (Xtwo - Xone) + (Ytwo - Yone) * (Ytwo - Yone));
    }

    private void getAllCollidablesInRange() {
        collidableObjectsInRange = new ArrayList<>();
        for (Sensor sensor : sensors) {
            for (WorldObject object : world.getObjectsInsideTriangle(sensor.getTriangleSource(),
                    sensor.getTriangleCorner1(), sensor.getTriangleCorner2())) {
                if (object.getZ() > 0 && !collidableObjectsInRange.contains(object)) {
                    collidableObjectsInRange.add(object);
                    if (nearestCollidableObject == null) {
                        nearestCollidableObject = object;
                    } else {
                        double distanceA = distanceBetweenTwoPoints(nearestCollidableObject.getX(),
                                nearestCollidableObject.getY(),
                                sensor.getTriangleSource().x, sensor.getTriangleSource().y);
                        double distanceB = distanceBetweenTwoPoints(object.getX(),
                                object.getY(),
                                sensor.getTriangleSource().x, sensor.getTriangleSource().y);
                        if (distanceA > distanceB) {
                            nearestCollidableObject = object;
                        }
                    }
                }
            }
        }

    }


    public List<WorldObject> getCollidableObjectsInRange() {
        getAllCollidablesInRange();
        return collidableObjectsInRange;
    }
}
