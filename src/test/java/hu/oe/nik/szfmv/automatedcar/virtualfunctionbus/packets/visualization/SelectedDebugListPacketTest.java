package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void elementRemovedTwice() {
        selectedDebugListPacket.removeDebugElement("ez");
        selectedDebugListPacket.removeDebugElement("ez");
        List<String> list = selectedDebugListPacket.getDebugList();
        assertEquals("az", list.get(0));
    }
}
