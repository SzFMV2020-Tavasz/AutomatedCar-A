package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.IVector;

public interface CarPositionPacket {

    double getX();

    double getY();

    IVector getMoveVector();

}
