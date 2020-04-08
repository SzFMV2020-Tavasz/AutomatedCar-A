package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.CarPositionPacket;

import java.awt.*;

public class MovingWorldObject extends WorldObject{
    VirtualFunctionBus virtualFunctionBus;
    AutomatedCar automatedCar;
    private Integer lastPosX;
    private Integer lastPosY;
    private int movementVectorX;
    private int movementVectorY;

    // calculate new  position and old position?
    //oldpos
    //newpos
    public MovingWorldObject(WorldObject worldObject, VirtualFunctionBus virtualFunctionBus) {
        this.x = worldObject.getX();
        this.y = worldObject.getY();
        this.id = worldObject.getId();
        this.virtualFunctionBus = virtualFunctionBus;
        this.movementVectorX = 0;
        this.movementVectorY = 0;
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
    public int getRelativeMovementVectorX(){
           return (int)virtualFunctionBus.carPositionPacket.getMoveVector().getXDiff() + movementVectorX;
    }

    /**
     * gets the object's movement vector relative to the egocar
     *
     * @return the y component of the relative movement vector.
     */
    public int getRelativeMovementVectorY(){
        return (int)virtualFunctionBus.carPositionPacket.getMoveVector().getYDiff() + movementVectorY;
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
