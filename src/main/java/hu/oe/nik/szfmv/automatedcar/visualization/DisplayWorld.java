package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Class for storing and refreshing the objects to be displayed.
 * <p>
 * The class gets the constantly changing data, listens to changevents,
 * provides interface for
 */
public class DisplayWorld {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    final VirtualFunctionBus virtualFunctionBus;

    private List<WorldObject> fixWorldObjects;
    private List<WorldObject> dynamicWorldObjects;

    private World world;
    private AutomatedCar automatedCar;

    private boolean showCamera;
    private boolean showRadar;
    private boolean showUltrasound;

    private boolean debugOn;
    private List<String> debugObjects;

    private DisplaySensorObject displayRadar;

    private DisplaySensorObject displayCamera;
    private DisplaySensorObject[] displayUltrasounds;

    /**
     * The constructor for the DisplayWorld class
     *
     * @param world        the {@link World} object the fix and dynamic elements come from
     * @param automatedCar the {@link AutomatedCar} that has to be in the middle of the screen
     */
    public DisplayWorld(World world, AutomatedCar automatedCar) {
        virtualFunctionBus = automatedCar.getVirtualFunctionBus();
        fixWorldObjects = new ArrayList<>();
        dynamicWorldObjects = new ArrayList<>();
        this.world = world;
        this.automatedCar = automatedCar;
        getFixWorldObjectsFromWorld();
        getDynamicWorldObjectsFromWorld();
        VisualizationConfig.loadReferencePoints("reference_points.xml");
        displayCamera = new DisplaySensorObject(automatedCar);
        displayRadar = new DisplaySensorObject(automatedCar);
        displayUltrasounds = new DisplaySensorObject[VisualizationConfig.ULTRASOUND_SENSORS_COUNT];
        Arrays.fill(displayUltrasounds, new DisplaySensorObject(automatedCar));
        showCamera = false;
        showRadar = false;
        showUltrasound = false;
        debugOn = false;
        debugObjects = new ArrayList<>();
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
     *
     * @return List of DisplayObjects rotated and moved according to the egocar's position
     */
    List<DisplayObject> getDisplayObjects() {

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

    /**
     * Get the translated and rotated egocar.
     *
     * Method is done separately to fix display order.
     * @return the DisplayObject containing the egocar.
     */
    public DisplayObject getEgoCar() {
        DisplayObject egoCar = new DisplayObject(automatedCar, automatedCar);
        return egoCar;
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public void setShowRadar(boolean showRadar) {
        this.showRadar = showRadar;
    }

    public void setShowUltrasound(boolean showUltrasound) {
        this.showUltrasound = showUltrasound;
    }

    /**
     * Gets whether the camera's sensor triangle is shown or not
     * @return true if the camera is shown
     */
    public boolean isCameraShown() {
        return showCamera;
    }

    public boolean isRadarShown() {
        return showRadar;
    }

    /**
     * Gets whether the ultrasound's sensor triangle is shown or not
     * @return true if the ultraound is shown
     */
    public boolean isUltrasoundShown() {
        return showUltrasound;
    }

    /**
     * Gets whether the Debug mode is on
     * @return true if the debug mode is on
     */
    public boolean isDebugOn() {
        if (virtualFunctionBus.debugModePacket != null ) {
            debugOn = virtualFunctionBus.debugModePacket.getDebuggingState();
        }
        return debugOn;
    }

    /**
     * Gets the actual state of the DisplaySensorObject for the camera sensor
     * @return a {@link DisplaySensorObject} containing the passed data
     */
    DisplaySensorObject getDisplayCamera() {
        if (virtualFunctionBus.cameraVisualizationPacket != null) {
            Point2D source = virtualFunctionBus.cameraVisualizationPacket.getSensorSource();
            Point2D corner1 = virtualFunctionBus.cameraVisualizationPacket.getSensorCorner1();
            Point2D corner2 = virtualFunctionBus.cameraVisualizationPacket.getSensorCorner2();
            Color color = virtualFunctionBus.cameraVisualizationPacket.getColor();
            displayCamera.setSensorTriangle(source, corner1, corner2);
            displayCamera.setSensorColor(color);
            return displayCamera;
        } else {
            return null;
        }
    }

    /**
     * Gets the actual state of the DisplaySensorObject for the radar sensor
     * @return a {@link DisplaySensorObject} containing the passed data
     */
    DisplaySensorObject getDisplayRadar() {
        if (virtualFunctionBus.radarVisualizationPacket != null) {
            Point2D source = virtualFunctionBus.radarVisualizationPacket.getSensorSource();
            Point2D corner1 = virtualFunctionBus.radarVisualizationPacket.getSensorCorner1();
            Point2D corner2 = virtualFunctionBus.radarVisualizationPacket.getSensorCorner2();
            Color color = virtualFunctionBus.radarVisualizationPacket.getColor();
            displayRadar.setSensorTriangle(source, corner1, corner2);
            displayRadar.setSensorColor(color);
            return displayRadar;
        } else {
            return null;
        }

    }

    DisplaySensorObject[] getDisplayUltrasounds() {
        if(virtualFunctionBus.ultrasoundsVisualizationPacket != null) {
            Point2D[] sources = virtualFunctionBus.ultrasoundsVisualizationPacket.getSources();
            Point2D[] corner1s = virtualFunctionBus.ultrasoundsVisualizationPacket.getCorner1s();
            Point2D[] corner2s = virtualFunctionBus.ultrasoundsVisualizationPacket.getCorner2s();
            Color color = virtualFunctionBus.ultrasoundsVisualizationPacket.getColor();
            for (int i = 0; i < VisualizationConfig.ULTRASOUND_SENSORS_COUNT; i++) {
                if (sources[i] != null) {
                    displayUltrasounds[i].setSensorTriangle(sources[i], corner1s[i], corner2s[i]);
                    displayUltrasounds[i].setSensorColor(color);
                }
            }
            return displayUltrasounds;
        } else {
            return null;
        }
    }

    /**
     * Gets the list of object IDs of the objects that should be shown lined with a polygon
     * @return
     */
    public List<String> getDebugObjects() {

        if (virtualFunctionBus.selectedDebugListPacket != null) {
            debugObjects = virtualFunctionBus.selectedDebugListPacket.getDebugList();
        }
        return debugObjects;
    }
}
