package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.visualization.DisplayWorld;
import hu.oe.nik.szfmv.automatedcar.visualization.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final int CYCLE_PERIOD = 40;
    // The window handle
    private Gui window;
    private AutomatedCar car;
    private World world;
    private DisplayWorld displayWorld;

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

        // create an automated car
        // car = new AutomatedCar(3522, 2316, "car_2_white.png");
        // car = new AutomatedCar(474, 669, "car_2_white.png");
        car = new AutomatedCar(540, 1850, "car_2_white.png");
        car.setRotation(0);

        // create the displayworld
        displayWorld = new DisplayWorld(world, car);
        displayWorld.setShowCamera(true);
        displayWorld.setShowRadar(false);
        displayWorld.setShowUltrasound(false);
        displayWorld.setDebugOn(true);
        displayWorld.addObjectsToDebug(new ArrayList<String>(Arrays.asList("road_2lane_90right_24")));

        window = new Gui();
        window.setVirtualFunctionBus(car.getVirtualFunctionBus());

    }

    private void loop() {
        while (true) {
            try {
                car.drive();
                window.getCourseDisplay().drawWorld(displayWorld);
                window.getDashboard().refreshDrawing();
//                window.getCourseDisplay().refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

}
