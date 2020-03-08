package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class for storing and refreshing the objects to be displayed.
 *
 * The class gets the constantly changing data, listens to changevents,
 * provides interface for
 */
public class DisplayWorld {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private List<WorldObject> fixWorldObjects;
    private List<WorldObject> dynamicWorldObjects;

    private World world;
    private AutomatedCar automatedCar;

    /**
     * The constructor for the DisplayWorld class
     * @param world the {@link World} object the fix and dynamic elements come from
     * @param automatedCar the {@link AutomatedCar} that has to be in the middle of the screen
     */
    public DisplayWorld(World world, AutomatedCar automatedCar) {
        fixWorldObjects = new ArrayList<WorldObject>();
        dynamicWorldObjects = new ArrayList<WorldObject>();
        this.world = world;
        this.automatedCar = automatedCar;
        getFixWorldObjectsFromWorld();
        getDynamicWorldObjectsFromWorld();
        VisualizationConfig.loadReferencePoints();
    }

    /**
     * Get the fix data objects from world.
     * Needs to be a separate method from constuctor because it might be called several times
     */
    public void getFixWorldObjectsFromWorld() {
        fixWorldObjects = world.getWorldObjects();
    }

    /**
     * Get the fix data objects from world.
     * Needs to be a separate method from constuctor because it might be called several times
     */
    public void getDynamicWorldObjectsFromWorld() {
        dynamicWorldObjects = world.getDynamics();
    }

    /**
     * Gets all objects that needs to be drawn
     * @return List of DisplayObjects rotated and moved according to the egocar's position
     */
    public List<DisplayObject> getDisplayObjects() {

        List<DisplayObject> returnList = new ArrayList<DisplayObject>();

        // loop through the fix objects and create their DisplayObjects
        for (WorldObject obj : fixWorldObjects) {
            DisplayObject dispObj = new DisplayObject(obj, automatedCar);
            returnList.add(dispObj);
        }

        // refresh dynamic objects
        this.getDynamicWorldObjectsFromWorld();
        // loop through the dynamic objects and create their DisplayObjects
        for (WorldObject obj : dynamicWorldObjects) {
            DisplayObject dispObj = new DisplayObject(obj, automatedCar);
            returnList.add(dispObj);
        }

        // sort by Z
        returnList.sort(Comparator.comparing(DisplayObject::getZ));

        // add egocar
        DisplayObject egoCar = new DisplayObject(automatedCar, automatedCar);
        returnList.add(egoCar);

        return returnList;
    }
}
