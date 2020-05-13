package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.cruisecontrol;

public interface IAccInputPacket {

    boolean isAccButtonPressed();

    boolean isAccIncreaseSpeedButtonPressed();

    boolean isAccDecreaseSpeedButtonPressed();

}
