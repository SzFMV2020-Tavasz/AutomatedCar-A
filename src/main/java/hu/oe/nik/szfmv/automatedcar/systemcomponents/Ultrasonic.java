package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.World;
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

}
