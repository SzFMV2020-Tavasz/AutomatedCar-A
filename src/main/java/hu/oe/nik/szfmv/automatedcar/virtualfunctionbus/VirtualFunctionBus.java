package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.ReadOnlySamplePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.GuiInputPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.IEngineStatusPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.*;

/**
 * This is the interface for the Virtual Function Bus. Components are only
 * allowed to collect sensory data exclusively using the VFB. The VFB stores the
 * input and output signals, inputs only have setters, while outputs only have
 * getters respectively.
 */
public abstract class VirtualFunctionBus {

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

    public abstract void registerComponent(SystemComponent component);

    public abstract void loop();

    public static SimpleVirtualFunctionBus createNewSimpleVFB() {
        return new SimpleVirtualFunctionBus();
    }

    public static DependentVirtualFunctionBus createNewVFBSupportingDependencies() {
        return new DependentVirtualFunctionBus();
    }

}
