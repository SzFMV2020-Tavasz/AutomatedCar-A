package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

/**
 * @author  Robespierre19
 *
 * Date: 2019-05-08
 */

public interface ReadOnlyPowertrainPacket {

    float getSpeed();

    int getRPM();

    int getActualAutoGear();

}
