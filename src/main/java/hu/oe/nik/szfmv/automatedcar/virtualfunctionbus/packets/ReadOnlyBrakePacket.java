package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

/**
 * @author  BazsoGabor
 *
 * Date: 2019-04-18
 */

public interface ReadOnlyBrakePacket {

    boolean isBrake();

    boolean isWarning();
    
}
