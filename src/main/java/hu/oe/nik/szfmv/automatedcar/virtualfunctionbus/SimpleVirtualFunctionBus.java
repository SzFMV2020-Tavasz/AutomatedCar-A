package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;

import java.util.ArrayList;
import java.util.List;


public class SimpleVirtualFunctionBus extends VirtualFunctionBus {

    /**All registered components.*/
    private final List<SystemComponent> components = new ArrayList<>();

    /**
     * Registers the provided {@link SystemComponent component}.
     *
     * @param component component to be registed.
     */
    @Override
    public void registerComponent(SystemComponent component) {
        components.add(component);
    }

    /**
     * Calls cyclically the registered {@link SystemComponent component}s once the virtual function bus has started.
     */
    @Override
    public void loop() {
        for (SystemComponent component : components) {
            component.loop();
        }
    }

}
