package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import java.util.List;

/**
 * WILL BE REMOVED AT THE BEGINING OF SRPINT 3
 * SelectedDebugListPacket is already removed from Virtualfunctionbus
 * use WorldObject ..Highlight.. methods for emphasizing objects for sensors
 */
public interface ISelectedDebugListPacket {

    /**
     * Gets the list of WorldObject elements whose debug polygons need to be displayed
     * @return The list of id strings identifying the elements
     */
    public List<String> getDebugList();
}
