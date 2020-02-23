/**
 * Info about this package doing something for package-info.java file.
 */
package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

/**
 * Class for handling the objects to be displayed.
 * The class calculates the position and rotation of the original word object
 * so it could keep its relative position to the egocar
 * regardless of the egocars rotation and position.
 */
public class DisplayObject extends WorldObject {

    /**
     * The constructor of the DisplayObject class.
     * @param worldObject the WorldObject which position should be calculated.
     * @param automatedCar The AutomatedCar (egocar) that the DisplayObject should keep its relative position to.
     */
    public DisplayObject(final WorldObject worldObject, final AutomatedCar automatedCar) {
        super(worldObject.getX(), worldObject.getY(), worldObject.getImageFileName());

    }
}
