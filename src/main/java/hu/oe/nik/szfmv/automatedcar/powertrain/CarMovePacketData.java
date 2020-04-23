package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;

import static java.lang.Double.NaN;

class CarMovePacketData implements ICarMovePacket {
    private final IVector moveVector;

    public CarMovePacketData(IVector moveVector) {
        this.moveVector = moveVector;
    }

    @Override
    public IVector getMoveVector() {
        return moveVector;
    }
}
