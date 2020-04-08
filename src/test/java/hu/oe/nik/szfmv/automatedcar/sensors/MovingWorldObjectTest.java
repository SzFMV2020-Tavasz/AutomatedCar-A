package hu.oe.nik.szfmv.automatedcar.sensors;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.CarPositionPacket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovingWorldObjectTest {
    MovingWorldObject movingWorldObject;
    VirtualFunctionBus virtualFunctionBus;

    /**
     * implementing interfaces for testing
     */
    class MockCarPostionPacketData implements CarPositionPacket{
        int calledNumber = 0;
        MockCarPostionPacketData( ){ };

        @Override
        public double getX() {
            return 0;
        }

        @Override
        public double getY() {
            return 0;
        }

        @Override
        public IVector getMoveVector() {
            calledNumber++;
            if (calledNumber <= 2) {
                return new MockVector(25, 35);
            } else {
                return new MockVector(35, 45);
            }
        }
    }
    class MockVector implements IVector{
        int xDiff;
        int yDiff;
        MockVector(int xDiff, int yDiff){
            this.xDiff = xDiff;
            this.yDiff = yDiff;
        };

        @Override
        public double getXDiff() {
            return xDiff;
        }

        @Override
        public double getYDiff() {
            return yDiff;
        }

        @Override
        public double getLength() {
            return 0;
        }

        @Override
        public double getRadiansRelativeTo(IVector direction) {
            return 0;
        }

    }

    @BeforeEach
    public void init() {
        WorldObject worldObject = new WorldObject(10, 20, "tree.png");
        virtualFunctionBus = new VirtualFunctionBus();
        CarPositionPacket carPositionPacket = new MockCarPostionPacketData();
        virtualFunctionBus.carPositionPacket = carPositionPacket;

        movingWorldObject = new MovingWorldObject(worldObject, virtualFunctionBus);
    }

    /**
     * Check whether the class gets instantiatied when new Radar() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(movingWorldObject);
    }

    /**
     * Checks if the movement vector of the object is calulated right
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
        assertEquals(25, movingWorldObject.getRelativeMovementVectorX());
        assertEquals(35, movingWorldObject.getRelativeMovementVectorY());
        movingWorldObject.setX(20);
        movingWorldObject.setY(40);
        assertEquals(45, movingWorldObject.getRelativeMovementVectorX());
        assertEquals(65, movingWorldObject.getRelativeMovementVectorY());
    }
}
