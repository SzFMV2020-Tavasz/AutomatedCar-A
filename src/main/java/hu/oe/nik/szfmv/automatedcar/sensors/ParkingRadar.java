package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.ParkingRadarDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.ParkingRadarVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.visualization.VisualizationConfig;

import java.awt.*;

public class ParkingRadar extends SystemComponent {

    // radar sensor triangles data
    private static final double RADAR_SENSOR_ANGLE = Math.PI / 6;  // 60 /2 - half angle
    private static final int RADAR_SENSOR_RANGE = 2 * VisualizationConfig.METER_IN_PIXELS;
    private static final Color RADAR_SENSOR_BG_COLOUR = new Color(
        255, 200, 100, VisualizationConfig.SENSOR_COLOR_ALPHA);
    private static final int TRIANGLE_POLYGON_POINTS = 3;

    // packets for sending data
    private final ParkingRadarVisualizationPacket parkingRadarVisualizationPacket;
    private final ParkingRadarDisplayStatePacket parkingRadarDisplayStatePacket;

    // objects references for reference
    private AutomatedCar automatedCar;
    private World world;

    public ParkingRadar(VirtualFunctionBus virtualFunctionBus, AutomatedCar automatedCar, World world) {
        super(virtualFunctionBus);
        parkingRadarDisplayStatePacket = new ParkingRadarDisplayStatePacket();
        virtualFunctionBus.parkingRadarDisplayStatePacket = parkingRadarDisplayStatePacket;
        parkingRadarVisualizationPacket = new ParkingRadarVisualizationPacket();
        virtualFunctionBus.parkingRadarVisualizationPacket = parkingRadarVisualizationPacket;
    }

    @Override
    public void loop() {

    }
}
