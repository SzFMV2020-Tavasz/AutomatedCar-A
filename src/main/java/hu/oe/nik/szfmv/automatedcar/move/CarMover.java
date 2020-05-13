package hu.oe.nik.szfmv.automatedcar.move;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.DependsOn;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import static hu.oe.nik.szfmv.automatedcar.move.CarMoveCalculatorStrategy.consideringBackWheels;

/**@author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
@DependsOn(components = PowerTrain.class)
public final class CarMover extends SystemComponent {

    private final AutomatedCar car;
    private final CarMoveCalculatorStrategy carMoveStrategy = consideringBackWheels();

    public CarMover(VirtualFunctionBus virtualFunctionBus, AutomatedCar car) {
        super(virtualFunctionBus);
        this.car = car;
    }

    @Override
    public void loop() {
        updatePositionAndOrientation(this.car);
    }

    /**@author Team 3*/
    private void updatePositionAndOrientation(AutomatedCar car) {
        this.carMoveStrategy.applyMovement(car, this.virtualFunctionBus.powerTrain.getMovement().getMoveVector());
    }

}
