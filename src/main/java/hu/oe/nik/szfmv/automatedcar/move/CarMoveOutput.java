package hu.oe.nik.szfmv.automatedcar.move;

import hu.oe.nik.szfmv.automatedcar.math.IVector;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.average;

/**@author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
final class CarMoveOutput implements ICarLocation {

    final IVector newPosition;
    final IVector newFacing;

    private CarMoveOutput(CarMoveInput input) {
        IVector newCarFrontPosition = input.oldCarFrontPosition.add(input.moveInWheelFacingDirection);
        IVector newCarBackPosition = input.reverseMode
                ? input.oldCarBackPosition.subtract(input.movement.withDirection(input.oldFacing))
                : input.oldCarBackPosition.add(input.movement.withDirection(input.oldFacing));
        newPosition = average(newCarFrontPosition, newCarBackPosition);
        newFacing = newCarFrontPosition.subtract(newCarBackPosition);
    }

    static CarMoveOutput calculateMovement(CarMoveInput input) {
        return new CarMoveOutput(input);
    }

    @Override
    public IVector getPosition() {
        return newPosition;
    }

    @Override
    public IVector getFacing() {
        return newFacing;
    }

}
