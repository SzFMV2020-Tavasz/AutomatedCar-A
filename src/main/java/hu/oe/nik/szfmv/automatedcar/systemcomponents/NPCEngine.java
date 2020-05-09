package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class NPCEngine extends SystemComponent
{
    protected final World world;
    public NPCEngine(VirtualFunctionBus virtualFunctionBus, World world) {
        super(virtualFunctionBus);
        this.world = world;
    }

    @Override
    public void loop() {

    }
}
