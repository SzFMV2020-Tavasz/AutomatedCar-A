package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Helper class for sensor methods.
 * Methods that needed for all sensors are collected here,
 * ie. distancefrom points, getting the list of collideable elements...
 */
public final class CommonSensorMethods {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor for static class
     * <p>
     * It does not need to be instantiated.
     * </p>
     */
    private CommonSensorMethods() {
    }

    

    /**
     * Checks whether an element is collideable by checking its Z value.
     *
     * Everything with a Z value higher than 0 is collidable
     *
     * @param object The {@link WorldObject} to check
     * @return True if the object is not on the not collideable element's list.
     */
    public static boolean isCollideable(WorldObject object) {
        return object.getZ() >= 1;
    }
}
