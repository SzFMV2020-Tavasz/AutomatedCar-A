package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing and refreshing the objects to be displayed.
 * <p>
 * The class gets the constantly changing data, listens to changevents,
 * provides interface for
 */
public class DisplayWorld {

    private List<WorldObject> fixWorldObjects;
    private List<WorldObject> dynamicWorldObjects;

    public DisplayWorld() {
        fixWorldObjects = new ArrayList<WorldObject>();
        dynamicWorldObjects = new ArrayList<WorldObject>();
        // at init get the fix world's object from the packet
    }
}
