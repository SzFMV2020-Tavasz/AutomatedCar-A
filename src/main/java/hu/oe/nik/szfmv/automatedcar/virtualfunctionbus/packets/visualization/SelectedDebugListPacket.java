package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import java.util.ArrayList;
import java.util.List;

/**
 * WILL BE REMOVED AT THE BEGINING OF SRPINT 3
 * SelectedDebugListPacket is already removed from Virtualfunctionbus
 * use WorldObject ..Highlight.. methods for emphasizing objects for sensors
 */
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
     * Replaces the whole list of WorldObject elements whose debug polygons need to be displayed
     * @param elementIDs List of Id strings identifyint the elements
     */
    public void setDebugListElements(List<String> elementIDs) {
        debugList = elementIDs;
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
