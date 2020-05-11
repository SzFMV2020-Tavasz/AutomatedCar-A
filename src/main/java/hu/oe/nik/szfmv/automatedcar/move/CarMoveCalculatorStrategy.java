package hu.oe.nik.szfmv.automatedcar.move;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;

/**@author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
abstract class CarMoveCalculatorStrategy {

    protected abstract ICarLocation calculateMovement(AutomatedCar car, IVector movement);

    /**@param movement The movement to apply.
     * <p>- its direction is interpreted as facing direction of the front wheels.</p>
     * <p>- its length is interpreted as the speed of movement.</p>*/
    final void applyMovement(AutomatedCar car, IVector movement) {
        ICarLocation newLocation = this.calculateMovement(car, movement);

        car.setPosition(newLocation.getPosition());
        car.setFacingDirection(newLocation.getFacing());
        car.setRotation(newLocation.getFacing().getRadiansRelativeTo(Axis.Y.negativeDirection()));
    }

    /**Applies movement to the car, although not the given vector directly.
     * Moves the car approximately in the direction of the given move also considering the rotation of the car.*/
    static CarMoveCalculatorStrategy consideringBackWheels() {
        return new CarMoveCalculatorStrategy() {
            @Override
            public ICarLocation calculateMovement(AutomatedCar car, IVector movement) {
                CarMoveInput input = CarMoveInput.prepareForMovement(car, movement);
                return CarMoveOutput.calculateMovement(input);
            }
        };
    }

}
