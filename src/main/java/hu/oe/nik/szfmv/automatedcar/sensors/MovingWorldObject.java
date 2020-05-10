package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class MovingWorldObject extends WorldObject {
    VirtualFunctionBus virtualFunctionBus;
    AutomatedCar automatedCar;
    private Integer lastPosX;
    private Integer lastPosY;
    private int movementVectorX;
    private int movementVectorY;
    private WorldObject worldObject; // storing the reference

    // calculate new  position and old position?
    //oldpos
    //newpos
    public MovingWorldObject(WorldObject worldObject, VirtualFunctionBus virtualFunctionBus) {
        // copy the whole WorldObject int the class so it could be passed as one if needed
        // with WorldObject information intact
        this.x = worldObject.getX();
        this.y = worldObject.getY();
        this.rotation = worldObject.getRotation();
        this.height = worldObject.getHeight();
        this.width = worldObject.getWidth();
        this.type = worldObject.getType();
        this.image = worldObject.getImage();
        this.rotationMatrix = worldObject.getRotationMatrix();
        this.imageFileName = worldObject.getImageFileName();
        this.id = worldObject.getId();
        this.polygons = worldObject.getPolygons();
        this.virtualFunctionBus = virtualFunctionBus;
        this.movementVectorX = 0;
        this.movementVectorY = 0;
        this.worldObject = worldObject;
        // set the radar highlight false
        this.worldObject.setHighlightedWhenRadarIsOn(false);

    }

    /**
     * Gets the original worldObject stored
     * @return the referenc to the original worldObject
     */
    public WorldObject getWorldObject() {
        return worldObject;
    }

    /**
     * Set the current x of the object
     * @param x the x component of the current position
     */
    @Override
    public void setX(int x) {
        lastPosX = this.getX();
        this.x = x;
        calculateMovementVector();
    }

    /**
     * Set the current y of the object
     * @param y the y component of the current position
     */
    @Override
    public void setY(int y) {
        lastPosY = this.getY();
        this.y = y;
        calculateMovementVector();
    }

    /**
     * gets the object's movement vector relative to the egocar
     *
     * @return the x component of the relative movement vector.
     */
    public int getRelativeMovementVectorX() {
        // car movement vector needs to be negative
        // as we need the elements' relative movement because of the automated car's movement
        return (int)-virtualFunctionBus.carMovePacket.getMoveVector().getXDiff() + movementVectorX;
    }

    /**
     * gets the object's movement vector relative to the egocar
     *
     * @return the y component of the relative movement vector.
     */
    public int getRelativeMovementVectorY() {
        // car movement vector needs to be negative
        // as we need the elements' relative movement because of the automated car's movement
        return (int)-virtualFunctionBus.carMovePacket.getMoveVector().getYDiff() + movementVectorY;
    }

    public int getMovementVectorX() {
        return movementVectorX;
    }

    public int getMovementVectorY() {
        return movementVectorY;
    }

    private void calculateMovementVector() {
        if (lastPosX != null && lastPosY != null) {
            this.movementVectorX = this.getX() - this.lastPosX;
            this.movementVectorY = this.getY() - this.lastPosY;
        }
    }
}
