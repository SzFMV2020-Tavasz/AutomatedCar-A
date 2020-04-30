package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;

class CarMovePacketData implements ICarMovePacket {
    private final IVector accelerationVector;
    private final IVector wheelFacingDirection;

    public CarMovePacketData(IVector accelerationVector, IVector wheelFacingDirection) {
        this.accelerationVector = accelerationVector;
        this.wheelFacingDirection = wheelFacingDirection;
    }

    @Override
    public IVector getAccelerationVector() {
        return accelerationVector;
    }

    @Override
    public IVector getWheelFacingDirection() {
        return this.wheelFacingDirection;
    }
}
