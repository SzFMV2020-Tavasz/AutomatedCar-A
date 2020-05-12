package hu.oe.nik.szfmv.automatedcar.cruisecontrol;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.DependsOn;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

/**A system that automatically controls the speed of a motor vehicle. The system is a servomechanism
 * that takes over the throttle of the car to maintain a steady speed as set by the driver.
 * <p></p>
 * <p>Depends on {@link Driver}, because manual input from the human driver affects the cruise control,
 * like turning it on/off via switch or by pressing the break pedal manually.</p>*/
@DependsOn(components = Driver.class)
public final class CruiseControl extends SystemComponent {

    public CruiseControl(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() {
        //under development
    }

}
