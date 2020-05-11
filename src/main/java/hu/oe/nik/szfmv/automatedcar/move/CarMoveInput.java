package hu.oe.nik.szfmv.automatedcar.move;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode;

/**@author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
final class CarMoveInput {
    final IVector movement;
    final IVector oldFacing;
    final IVector oldCarFrontPosition;
    final IVector oldCarBackPosition;
    final IVector moveInWheelFacingDirection;
    final boolean reverseMode;

    private CarMoveInput(AutomatedCar car, IVector movement) {
        this.movement = movement;
        oldFacing = car.getFacingDirection();
        IVector oldPosition = car.getPosition();
        IVector toCarFrontVector = oldFacing.withLength(car.getHeight() / 2.0);
        oldCarFrontPosition = oldPosition.add(toCarFrontVector);
        oldCarBackPosition = oldPosition.subtract(toCarFrontVector);
        moveInWheelFacingDirection = movement.rotateByRadians(oldFacing.getRadians());
        reverseMode = car.getPowerTrain().getTransmission().getCurrentTransmissionMode() == CarTransmissionMode.R_REVERSE;
    }

    static CarMoveInput prepareForMovement(AutomatedCar car, IVector movement) {
        return new CarMoveInput(car, movement);
    }

}
