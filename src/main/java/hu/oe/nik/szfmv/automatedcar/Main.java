package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.sensors.ParkingRadar;
import hu.oe.nik.szfmv.automatedcar.sensors.Radar;
import hu.oe.nik.szfmv.automatedcar.sensors.Ultrasonic;
import hu.oe.nik.szfmv.automatedcar.visualization.DisplayWorld;
import hu.oe.nik.szfmv.automatedcar.visualization.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final int CYCLE_PERIOD = 40;
    // The window handle
    private Gui window;
    private AutomatedCar car;
    private World world;
    private DisplayWorld displayWorld;
    private Radar radar;
    private Ultrasonic ultrasonic;
    private ParkingRadar parkingRadar;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        init();
        loop();
    }

    private void init() {
        // create the world
        world = new World(5000, 3000);

        // set up the automated car
        car = Config.carStarterPosForEgoCar();
        car.setRotation(0);

        radar = new Radar(car.getVirtualFunctionBus(), car, world);
        ultrasonic = new Ultrasonic(car.getVirtualFunctionBus(), car, world);
        parkingRadar = new ParkingRadar(car.getVirtualFunctionBus(), car, world);


        // create the displayworld
        displayWorld = new DisplayWorld(world, car);

        window = new Gui();
        window.setVirtualFunctionBus(car.getVirtualFunctionBus());

        Config.setUpStartter(window.getVirtualFunctionBus());
    }

    private void loop() {
        while (true) {
            try {
                car.drive();
                window.getCourseDisplay().drawWorld(displayWorld);
                window.getDashboard().refresh(car.getX(), car.getY());
//                window.getCourseDisplay().refreshFrame();
                ultrasonic.getCollidableObjectsInRange();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

}
