package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;

public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger(AutomatedCar.class);

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    private final PowerTrain powerTrain = new PowerTrain(virtualFunctionBus);

    private IVector facingDirection = Axis.Y.negativeDirection();

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
        IVector moveInDirection = forwardMove.rotateByRadians(this.facingDirection.getRadians()); //+Y upwards
        //IVector moveInDirectionOnMap = moveInDirection.withY(-moveInDirection.getYDiff());        //-Y upwards

        this.setPosition(getPosition().add(moveInDirection));

        double rotationAngle;
        double speed = forwardMove.getLength();
        System.out.println(speed);

        if (speed < 50) {
            rotationAngle = forwardMove.getRadians() / 10 * (speed / 50);
        } else {
            rotationAngle = forwardMove.getRadians() / 7;
        }

        this.facingDirection = this.facingDirection.rotateByRadians(rotationAngle);
        this.setRotation(this.facingDirection.getRadiansRelativeTo(Axis.Y.negativeDirection()));
    }

}
