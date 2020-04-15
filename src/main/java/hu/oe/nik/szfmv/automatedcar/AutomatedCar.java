package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.average;
import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;

public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger(AutomatedCar.class);

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    private final PowerTrain powerTrain = new PowerTrain(virtualFunctionBus);

    /**Not necessarily a unit vector, can have any length.*/
    private IVector facingDirection = Axis.Y.negativeDirection();

    private final double HALF_CAR_LENGTH = 100;

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
    }

    public void drive() {
        virtualFunctionBus.loop();
        updatePositionAndOrientation();
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    public PowerTrain getPowerTrain() {
        return powerTrain;
    }

    //public Radar getRadar() {return radar}

    public IVector getPosition() {
        return vectorFromXY(this.x, this.y);
    }

    public void setPosition(IVector position) {
        this.setX((int)position.getXDiff());
        this.setY((int)position.getYDiff());
    }

    private void updatePositionAndOrientation() {
        IVector forwardMove = virtualFunctionBus.carPositionPacket.getMoveVector();
        this.moveForward(forwardMove);
    }

    private void moveForward(IVector forwardMove) {
        IVector currentPosition = this.getPosition();
        IVector toCarFrontVector = this.facingDirection.withLength(HALF_CAR_LENGTH);
        IVector carFrontPosition = currentPosition.add(toCarFrontVector);
        IVector carBackPosition = currentPosition.subtract(toCarFrontVector);

        IVector moveInDirection = forwardMove.rotateByRadians(this.facingDirection.getRadians());

        IVector newCarFrontPosition = carFrontPosition.add(moveInDirection);
        IVector newCarBackPosition = carBackPosition.add(forwardMove.withDirection(toCarFrontVector));

        this.setPosition(average(newCarFrontPosition, newCarBackPosition));

        this.facingDirection = newCarFrontPosition.subtract(newCarBackPosition);
        this.setRotation(facingDirection.getRadiansRelativeTo(Axis.Y.negativeDirection()));
    }

}
