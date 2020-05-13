package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus;

import hu.oe.nik.szfmv.automatedcar.cruisecontrol.CruiseControl;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
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

    /**@deprecated there is no need for a sample packet in production code.*/
    @Deprecated(forRemoval = true)
    public ReadOnlySamplePacket samplePacket;

    public ToPowerTrainPacket toPowerTrainPacket = new ToPowerTrainPacket();
    public GuiInputPacket guiInputPacket = new GuiInputPacket();

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

    public final CruiseControl.Packets cruiseControl = new CruiseControl.Packets();
    public final PowerTrain.Packets powerTrain = new PowerTrain.Packets();

    /**@deprecated use it via the {@link #powerTrain} field using {@link PowerTrain.Packets#getMovement()}.*/
    @Deprecated(forRemoval = true)
    public ICarMovePacket carMovePacket;
    /**@deprecated use it via the {@link #powerTrain} field using {@link PowerTrain.Packets#getEngineStatus()}.*/
    @Deprecated(forRemoval = true)
    public IEngineStatusPacket engineStatusPacket;

    /**All registered components.*/
    private final List<SystemComponent> components = new ArrayList<>();

    /**
     * Registers the provided {@link SystemComponent component}.
     *
     * @param component component to be registered.
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
