package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarPositionPacket;

import static java.lang.Double.NaN;

class CarPositionPacketData implements ICarPositionPacket {

    @Deprecated(forRemoval = true)
    private final double x;

    @Deprecated(forRemoval = true)
    private final double y;

    private final IVector moveVector;

    public CarPositionPacketData(IVector moveVector) {
        this.x = NaN;
        this.y = NaN;
        this.moveVector = moveVector;
    }

    @Deprecated(forRemoval = true)
    public CarPositionPacketData(double x, double y, IVector moveVector) {
        this.x = x;
        this.y = y;
        this.moveVector = moveVector;
    }

    @Deprecated(forRemoval = true)
    @Override
    public double getX() {
        return x;
    }

    @Deprecated(forRemoval = true)
    @Override
    public double getY() {
        return y;
    }

    @Override
    public IVector getMoveVector() {
        return moveVector;
    }
}
