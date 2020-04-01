package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import java.util.List;

public interface ISelectedDebugListPacket {

    /**
     * Gets the list of WorldObject elements whose debug polygons need to be displayed
     * @return The list of id strings identifying the elements
     */
    public List<String> getDebugList();
}
