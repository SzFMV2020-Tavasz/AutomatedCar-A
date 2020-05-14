package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.move.CarMover;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.cruisecontrol.CruiseControl;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.DependentVirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;
import static java.lang.Math.round;

/**Represents a car object with all its inner components contained.*/
public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger(AutomatedCar.class);

    /**Host for information interchange between components of the car via packets.*/
    private final DependentVirtualFunctionBus virtualFunctionBus = new DependentVirtualFunctionBus();

    private final PowerTrain powerTrain = new PowerTrain(virtualFunctionBus);

    /**Known as "tempomat" in hungarian.*/
    private final CruiseControl cruiseControl = new CruiseControl(virtualFunctionBus);

    /**Not necessarily a unit vector, can have any length.*/
    private IVector facingDirection = Axis.Y.negativeDirection();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new CarMover(virtualFunctionBus, this);

        this.virtualFunctionBus.validateAllDependenciesSatisfied();
    }

    public AutomatedCar(CarVariant variant) {
        this(0, 0, variant.getImageResourceName());
    }

    public IVector getFacingDirection() {
        return facingDirection;
    }

    public void drive() {
        virtualFunctionBus.loop();
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    public PowerTrain getPowerTrain() {
        return powerTrain;
    }

    /**Gets the current center position of the car.*/
    public IVector getPosition() {
        return vectorFromXY(this.x, this.y);
    }

    /**Sets the center position of the car.*/
    public void setPosition(IVector position) {
        this.setX((int)round(position.getXDiff()));
        this.setY((int)round(position.getYDiff()));
    }

    /**Sets the center position of the car.*/
    public void setPosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public void setFacingDirection(IVector direction) {
        this.facingDirection = direction;
        this.setRotation(direction.getRadiansRelativeTo(Axis.Y.negativeDirection()));
    }

}
