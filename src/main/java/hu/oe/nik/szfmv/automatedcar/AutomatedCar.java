package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger();

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
    }

    public void drive() {
        virtualFunctionBus.loop();

        calculatePositionAndOrientation();
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    private void calculatePositionAndOrientation() {
        //TODO it is just a fake implementation

        switch (virtualFunctionBus.samplePacket.getKey()) {
            case 0:
                y -= 5;
                break;
            case 1:
                x += 5;
                break;
            case 2:
                y += 5;
                break;
            case 3:
                x -= 5;
                break;
            default:
                break;
        }
    }
}
