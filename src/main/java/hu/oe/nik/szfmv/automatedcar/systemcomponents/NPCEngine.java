package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import java.util.List;

public class NPCEngine extends SystemComponent
{
    protected final World world;
    private List<WorldObject> NPCs;
    public NPCEngine(VirtualFunctionBus virtualFunctionBus, World world) {
        super(virtualFunctionBus);
        this.world = world;
        NPCs = world.getDynamics();
    }

    @Override
    public void loop() {

    }
}
