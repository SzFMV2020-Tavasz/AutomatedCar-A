package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.CameraDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.DebugModePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.ParkingRadarDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.RadarDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.UltrasoundDisplayStatePacket;

public final class Config {
    static VirtualFunctionBus virtualFunctionBus;

    static DebugModePacket debugModePacket;
    static ParkingRadarDisplayStatePacket parkingRadarDisplayStatePacket;
    static RadarDisplayStatePacket radarDisplayStatePacket;
    static CameraDisplayStatePacket cameraDisplayStatePacket;
    static UltrasoundDisplayStatePacket ultrasoundDisplayStatePacket;

    private static boolean IS_DEBUG_ON = false;
    private static boolean SHOW_PARKING_RADAR = true;
    private static boolean SHOW_RADAR = false;
    private static boolean SHOW_CAMERA = false;
    private static boolean SHOW_ULTRASOUND = false;

    private static int[] PARKING_SPACE_STARTING_POS = new int[]{540, 1850};
    private static int[] UPPER_RIGHT_STARTING_POS = new int[]{3420, 700};
    private static int[] T_JUNCTION_STARTING_POS = new int[]{3900, 1400};
    private static int[] LANE_KEEPING_TEST_STARTING_POS = new int[]{3490, 1561};
    private static int[] POLYGON_CHECKER_WORLD_STARTING_POS = new int[]{350, 350};


    /**
     * remove comment if you want a different starting position than the parking space
     */
    public static AutomatedCar carStarterPosForEgoCar() {
        AutomatedCar car;
        // car starts in parking space
        car = new AutomatedCar(
            PARKING_SPACE_STARTING_POS[0], PARKING_SPACE_STARTING_POS[1], "car_2_white.png");
        // car starts at the upper right trees
        /* car = new AutomatedCar(
            UPPER_RIGHT_STARTING_POS[0], UPPER_RIGHT_STARTING_POS[1], "car_2_white.png");*/
        // car start at T-junction
        /* car = new AutomatedCar(
            T_JUNCTION_STARTING_POS[0], T_JUNCTION_STARTING_POS[1], "car_2_white.png");*/
        // for lane keeping test
        /*car = new AutomatedCar(
            LANE_KEEPING_TEST_STARTING_POS[0], LANE_KEEPING_TEST_STARTING_POS[1], "car_2_white.png");*/
        // debug polygon checker world starting position
        /* car = new AutomatedCar(
            POLYGON_CHECKER_WORLD_STARTING_POS[0], POLYGON_CHECKER_WORLD_STARTING_POS[1],
            "car_2_white.png");*/
        return car;
    }

    /**
     * starting configuration can be changed here
     */
    public static void setUpStartter(VirtualFunctionBus bus) {
        virtualFunctionBus = bus;
        initDebug();
        initCamera();
        initParkingRadar();
        initRadar();
        initUltraSound();
    }

    private static void initDebug() {
        debugModePacket = new DebugModePacket();
        virtualFunctionBus.debugModePacket = debugModePacket;
        debugModePacket.setDebuggingState(IS_DEBUG_ON);
    }

    private static void initParkingRadar() {
        parkingRadarDisplayStatePacket = new ParkingRadarDisplayStatePacket();
        virtualFunctionBus.parkingRadarDisplayStatePacket = parkingRadarDisplayStatePacket;
        parkingRadarDisplayStatePacket.setRadarDisplayState(SHOW_PARKING_RADAR);
    }

    private static void initRadar() {
        radarDisplayStatePacket = new RadarDisplayStatePacket();
        virtualFunctionBus.radarDisplayStatePacket = radarDisplayStatePacket;
        radarDisplayStatePacket.setRadarDisplayState(SHOW_RADAR);
    }

    private static void initCamera() {
        cameraDisplayStatePacket = new CameraDisplayStatePacket();
        virtualFunctionBus.cameraDisplayStatePacket = cameraDisplayStatePacket;
        cameraDisplayStatePacket.setCameraDisplayState(SHOW_CAMERA);
    }

    private static void initUltraSound() {
        ultrasoundDisplayStatePacket = new UltrasoundDisplayStatePacket();
        virtualFunctionBus.ultrasoundDisplayStatePacket = ultrasoundDisplayStatePacket;
        ultrasoundDisplayStatePacket.setUltrasoundDisplayState(SHOW_ULTRASOUND);
    }

}
