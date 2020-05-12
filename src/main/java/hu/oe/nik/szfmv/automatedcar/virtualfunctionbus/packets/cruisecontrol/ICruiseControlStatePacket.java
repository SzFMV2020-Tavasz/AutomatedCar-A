package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.cruisecontrol;

/**@author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public interface ICruiseControlStatePacket {

    /**@return whether Cruise Control is enabled currently or not.*/
    boolean isEnabled();

    /**@return the target speed, that the Cruise Control is keeping currently.*/
    double getTargetSpeed();

}
