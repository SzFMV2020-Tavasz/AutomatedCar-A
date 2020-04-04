package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.GuiInputPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.InputPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.ToPowerTrainPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.Debugging.DebugMode;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.ReadOnlySamplePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.DebugModePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.ICameraDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.ICameraVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.IRadarDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.IRadarVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.ISelectedDebugListPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.IUltrasoundDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.IUltrasoundsVisualizationPacket;


import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for the Virtual Function Bus. Components are only
 * allowed to collect sensory data exclusively using the VFB. The VFB stores the
 * input and output signals, inputs only have setters, while outputs only have
 * getters respectively.
 */
public class VirtualFunctionBus {

    public InputPacket inputPacket = new InputPacket();
    public ToPowerTrainPacket toPowerTrainPacket = new ToPowerTrainPacket();
    public GuiInputPacket guiInputPacket = new GuiInputPacket();

    public ReadOnlySamplePacket samplePacket;
    public IRadarVisualizationPacket radarVisualizationPacket;
    public ICameraVisualizationPacket cameraVisualizationPacket;
    public IUltrasoundsVisualizationPacket ultrasoundsVisualizationPacket;
    public DebugModePacket debugModePacket;
    public ISelectedDebugListPacket selectedDebugListPacket;
    public ICameraDisplayStatePacket cameraDisplayStatePacket;
    public IRadarDisplayStatePacket radarDisplayStatePacket;
    public IUltrasoundDisplayStatePacket ultrasoundDisplayStatePacket;

    public List<WorldObject> worldObjects = new ArrayList<>();

    private List<SystemComponent> components = new ArrayList<>();

    public DebugMode DebugMode = new DebugMode();


    /**
     * Registers the provided {@link SystemComponent}
     *
     * @param comp a class that implements @{link ISystemComponent}
     */
    public void registerComponent(SystemComponent comp) {
        components.add(comp);
    }

    /**
     * Calls cyclically the registered {@link SystemComponent}s once the virtual function bus has started.
     */
    public void loop() {
        for (SystemComponent comp : components) {
            comp.loop();
        }
    }

}
