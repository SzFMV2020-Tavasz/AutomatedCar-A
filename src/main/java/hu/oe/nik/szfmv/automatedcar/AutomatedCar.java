package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Polygon;

public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger(AutomatedCar.class);

    // temporary fix for checkstyle errors from merging master.
    private static final int TEN = 10;

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    private final PowerTrain powerTrain = new PowerTrain(virtualFunctionBus);

    // may or may not be permanent: the egocar's debug polygon
    private Polygon debugPoly = new Polygon(
        new int[]{50, 38, 27, 18, 12, 7, 6, 6, 3, 1, 0, 7, 7, 6, 6, 6, 9, 13, 17, 21, 26, 31, 37, 42, 50, 58, 63,
            69, 76, 79, 83, 87, 91, 94, 94, 94, 93, 93, 100, 99, 97, 94, 94, 93, 88, 82, 73, 62},
        new int[]{1, 2, 4, 8, 14, 23, 33, 63, 65, 67, 70, 69, 150, 152, 188, 191, 194, 198, 201, 203, 205, 206,
            207, 208, 208, 208, 207, 206, 205, 203, 201, 198, 194, 191, 188, 152, 150, 69, 70, 67, 65,
            63, 33, 23, 14, 8, 4, 2},
        48);

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);

        this.polygon = debugPoly;
    }

    public void drive() {
        virtualFunctionBus.loop();
        calculatePositionAndOrientation();

    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    public PowerTrain getPowerTrain() {
        return powerTrain;
    }

    //public Radar getRadar() {return radar}

    private void calculatePositionAndOrientation() {
        
        y -= virtualFunctionBus.toPowerTrainPacket.getGasPedalValue() / TEN;
        y += virtualFunctionBus.toPowerTrainPacket.getBreakPedalValue() / TEN;
        x += virtualFunctionBus.toPowerTrainPacket.getSteeringWheelValue() / TEN;
    }
}
