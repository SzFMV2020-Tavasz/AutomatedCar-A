package hu.oe.nik.szfmv.automatedcar.sensors;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hu.oe.nik.szfmv.automatedcar.math.Axis.baseDirection;
import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovingWorldObjectTest {
    MovingWorldObject movingWorldObject;
    VirtualFunctionBus virtualFunctionBus;

    /**
     * implementing interfaces for testing
     */
    private class DummyCarMovePacketData implements ICarMovePacket {
        int calledNumber = 0;
        DummyCarMovePacketData( ){ }

        @Override
        public IVector getAccelerationVector() {
            calledNumber++;
            if (calledNumber <= 2) {
                return vectorFromXY(25, 35);
            } else {
                return vectorFromXY(35, 45);
            }
        }

        @Override
        public IVector getWheelFacingDirection() {
            return baseDirection();
        }
    }

    @BeforeEach
    public void init() {
        WorldObject worldObject = new WorldObject(10, 20, "tree.png");
        virtualFunctionBus = new VirtualFunctionBus();
        virtualFunctionBus.carMovePacket = new DummyCarMovePacketData();

        movingWorldObject = new MovingWorldObject(worldObject, virtualFunctionBus);
    }

    /**
     * Check whether the class gets instantiated when new Radar() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(movingWorldObject);
    }

    /**
     * Checks if the movement vector of the object is calculated right
     */
    @Test
    public void movementVector(){
        assertEquals(0, movingWorldObject.getMovementVectorX());
        assertEquals(0, movingWorldObject.getMovementVectorY());
        movingWorldObject.setX(20);
        movingWorldObject.setY(40);
        assertEquals(10, movingWorldObject.getMovementVectorX());
        assertEquals(20, movingWorldObject.getMovementVectorY());
    }

    @Test
    public void relativeMovementVector(){
        assertEquals(-25, movingWorldObject.getRelativeMovementVectorX());
        assertEquals(-35, movingWorldObject.getRelativeMovementVectorY());
        movingWorldObject.setX(20);
        movingWorldObject.setY(30);
        assertEquals(-25, movingWorldObject.getRelativeMovementVectorX());
        assertEquals(-35, movingWorldObject.getRelativeMovementVectorY());
    }

}
