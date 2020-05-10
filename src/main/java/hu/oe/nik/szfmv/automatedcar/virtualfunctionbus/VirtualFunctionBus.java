package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.ReadOnlySamplePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.GuiInputPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.IEngineStatusPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.*;

import java.util.ArrayList;
import java.util.List;

public class VirtualFunctionBus {

    public ToPowerTrainPacket toPowerTrainPacket = new ToPowerTrainPacket();
    public GuiInputPacket guiInputPacket = new GuiInputPacket();
    public ReadOnlySamplePacket samplePacket;
    public ICarMovePacket carMovePacket;
    public IEngineStatusPacket engineStatusPacket;
    public IRadarVisualizationPacket radarVisualizationPacket;
    public ICameraVisualizationPacket cameraVisualizationPacket;
    public IUltrasoundsVisualizationPacket ultrasoundsVisualizationPacket;
    public IParkingRadarVisualizationPacket parkingRadarVisualizationPacket;
    public IParkingDistancePacket leftParkingDistance;
    public IParkingDistancePacket rightParkingDistance;
    public IParkingRadarGuiStatePacket parkingRadarGuiStatePacket;
    public DebugModePacket debugModePacket = new DebugModePacket();
    public ICameraDisplayStatePacket cameraDisplayStatePacket;
    public IRadarDisplayStatePacket radarDisplayStatePacket;
    public IUltrasoundDisplayStatePacket ultrasoundDisplayStatePacket;
    public IParkingRadarDisplayStatePacket parkingRadarDisplayStatePacket;
    public ISelectedDebugListPacket selectedDebugListPacket;

    /**All registered components.*/
    private final List<SystemComponent> components = new ArrayList<>();

    /**
     * Registers the provided {@link SystemComponent component}.
     *
     * @param component component to be registed.
     */
    public void registerComponent(SystemComponent component) {
        if (component.getClass().getAnnotation(DependsOn.class) != null) {
            throw new IllegalArgumentException("System component '" + component.getClass().getSimpleName()
                    + "' has dependencies, please use DependentVirtualFunctionBus for it instead!");
        }

        components.add(component);
    }

    /**
     * Calls cyclically the registered {@link SystemComponent component}s once the virtual function bus has started.
     */
    public void loop() {
        for (SystemComponent component : components) {
            component.loop();
        }
    }

}
