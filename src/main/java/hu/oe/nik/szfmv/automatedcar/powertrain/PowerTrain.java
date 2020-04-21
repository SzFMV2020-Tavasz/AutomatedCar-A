package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

/**<p>The powertrain encompasses every component that converts the engine’s power into movement.</p>
<p>This includes the engine, transmission, the driveshaft, differentials, axles; basically anything from the engine through to the rotating wheels.</p>*/
public class PowerTrain extends SystemComponent {

    public PowerTrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override public void loop() {

    }

}
