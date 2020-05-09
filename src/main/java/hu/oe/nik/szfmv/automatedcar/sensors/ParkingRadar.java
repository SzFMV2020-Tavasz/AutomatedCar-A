package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.DebugModePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.ParkingDistancePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.ParkingRadarDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.ParkingRadarVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.visualization.VisualizationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class ParkingRadar extends SystemComponent {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    // radar sensor triangles data
    private static final double RADAR_SENSOR_ANGLE = Math.PI / 6;  // 60 /2 - half angle
    private static final int RADAR_SENSOR_RANGE = 2 * VisualizationConfig.METER_IN_PIXELS;
    private static final Color RADAR_SENSOR_BG_COLOUR = new Color(
        255, 200, 100, VisualizationConfig.SENSOR_COLOR_ALPHA);
    private static final int TRIANGLE_POLYGON_POINTS = 3;

    // this is for demo
    private static final float MAX_D = 1f;
    private static final float MIN_D = .2f;
    private static final float STEP_D = .1f;

    // packets for sending data
    private final ParkingRadarVisualizationPacket parkingRadarVisualizationPacket;
    private final ParkingRadarDisplayStatePacket parkingRadarDisplayStatePacket;
    private final ParkingDistancePacket leftParkingDistancePacket;
    private final ParkingDistancePacket rightParkingDistancePacket;
    private final DebugModePacket debugModePacket;

    // objects references for reference
    private AutomatedCar automatedCar;
    private World world;

    // this is for demo
    private float distanceLeft = 1f;
    private float distanceRight = 1f;
    private int signum = -1;
    private int loopcounter = 0;

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
        this.automatedCar = automatedCar;
        this.world = world;
        debugModePacket = new DebugModePacket();
        virtualFunctionBus.debugModePacket = debugModePacket;
    }

    @Override
    public void loop() {
        // this is for demo only
        if (loopcounter == 20) {
            if (signum > 0) {
                distanceLeft += STEP_D;
                distanceRight += STEP_D;
            } else {
                distanceLeft -= STEP_D;
                distanceRight -= STEP_D;
            }
            loopcounter = 0;

            if (distanceRight >= MAX_D || distanceRight <= MIN_D) {
                signum *= -1;
            }
        }
        loopcounter++;

        leftParkingDistancePacket.setDistance(distanceLeft);
        rightParkingDistancePacket.setDistance(distanceRight);
        // turn on debug mode - left here for debugging purposes
        virtualFunctionBus.debugModePacket.setDebuggingState(false);
    }
}
