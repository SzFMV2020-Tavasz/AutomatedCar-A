package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import java.util.ArrayList;
import java.util.List;

public class SelectedDebugListPacket implements ISelectedDebugListPacket {
    private List<String> debugList;

    public SelectedDebugListPacket() {
        debugList = new ArrayList<String>();
    }

    /**
     * Adds an element to the list of WorldObject elements whose debug polygons need to be displayed
     * @param elementID Id strings identifying the element
     */
    public void addDebugListElement(String elementID) {
        if (!debugList.contains(elementID)) {
            debugList.add(elementID);
        }
    }

    /**
     * Removes an element from the list of WorldObject elements whose debug polygons need to be displayed
     * @param elementID Id strings identifying the element
     */
    public void removeDebugElement(String elementID) {
        if (debugList.contains(elementID)) {
            debugList.remove(elementID);
        }
    }

    /**
     * Gets the list of WorldObject elements whose debug polygons need to be displayed
     * @return The list of id strings identifying the elements
     */
    public List<String> getDebugList() {
        return debugList;
    }
}
