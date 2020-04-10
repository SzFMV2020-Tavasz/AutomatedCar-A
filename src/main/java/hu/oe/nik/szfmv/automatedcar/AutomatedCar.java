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
        //TODO it is just a fake implementation

//        switch (virtualFunctionBus.samplePacket.getKey()) {
//            case 0:
//                y -= 5;
//                break;
//            case 1:
//                x += 5;
//                break;
//            case 2:
//                y += 5;
//                break;
//            case 3:
//                x -= 5;
//                break;
//            default:
//        }

        y -= virtualFunctionBus.toPowerTrainPacket.getGasPedalValue()/10;
        y +=virtualFunctionBus.toPowerTrainPacket.getBreakPedalValue()/10;
        x +=virtualFunctionBus.toPowerTrainPacket.getSteeringWheelValue()/10;
    }

    /**
     * Temporary method for turning the egocar.
     * Needs passed keys from Gui
     * Will be removed when actual steering will be in place
     */
    private void rotateCar() {
        switch (virtualFunctionBus.samplePacket.getKey()) {
            case ROTATE_LEFT:
                rotation -= Math.toRadians(1);
                break;
            case ROTATE_RIGHT:
                rotation += Math.toRadians(1);
                break;
            default:
                break;
        }
    }
}
