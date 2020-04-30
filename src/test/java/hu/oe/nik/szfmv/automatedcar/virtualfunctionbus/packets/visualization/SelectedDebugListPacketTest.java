package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * WILL BE REMOVED AT THE BEGINING OF SRPINT 3
 * SelectedDebugListPacket is already removed from Virtualfunctionbus
 * use WorldObject ..Highlight.. methods for emphasizing objects for sensors
 */
public class SelectedDebugListPacketTest {
    SelectedDebugListPacket selectedDebugListPacket;

    @BeforeEach
    public void init() {
        selectedDebugListPacket = new SelectedDebugListPacket();
        selectedDebugListPacket.addDebugListElement("ez");
        selectedDebugListPacket.addDebugListElement("az");
    }
    /**
     *
     * Check whether the class gets instantiated
     */
    @Test
    public void classInstantiated() {
        assertNotNull(selectedDebugListPacket);
    }

    /**
     * Checks whether elements are added to the list
     */
    @Test
    public void checkAdded() {
        List<String> list = selectedDebugListPacket.getDebugList();
        assertEquals("ez", list.get(0));
        assertEquals("az", list.get(1));
    }

    /**
     * Checks whether the element is removed from the list
     */
    @Test
    public void checkRemoved() {
        selectedDebugListPacket.removeDebugElement("ez");
        List<String> list = selectedDebugListPacket.getDebugList();
        assertEquals("az", list.get(0));
    }

    /**
     * Checks whether element removed twice causes problem
     */
    @Test
    public void elementRemovedTwice() {
        selectedDebugListPacket.removeDebugElement("ez");
        selectedDebugListPacket.removeDebugElement("ez");
        List<String> list = selectedDebugListPacket.getDebugList();
        assertEquals("az", list.get(0));
    }

    /**
     * Checks whether the elements are changed when setting the whole list
     */
    @Test
    public void replaceList() {
        List<String> replacingList = Arrays.asList("amaz", "emez");
        selectedDebugListPacket.setDebugListElements(replacingList);
        assertEquals("amaz", selectedDebugListPacket.getDebugList().get(0));
        assertEquals("emez", selectedDebugListPacket.getDebugList().get(1));

    }
}
