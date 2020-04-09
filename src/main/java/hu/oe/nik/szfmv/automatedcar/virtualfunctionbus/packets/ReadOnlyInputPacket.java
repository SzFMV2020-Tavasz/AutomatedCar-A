package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

/**
 * Read only interface for the InputPacket class. Has only getters for the values.
 *
 *  @author  beregvarizoltan
 *  @author  BazsoGabor
 *  @author  attilanemeth
 *
 *  Date: 2019-05-07
 */
public interface ReadOnlyInputPacket {

    /**
     * Gear shift values:
     * P - Park
     * R - Reverse
     * N - Neutral
     * D - Drive
     */
    public enum GearShiftValues {
        P, R, N, D
    }

    /**
     * Get the Gas pedal Position
     *
     * @return The Gas pedal Position value, in the range of 0 - 100
     */
    int getGasPedal();

    int getBreakPedal();

    int getSteeringWheel();

    GearShiftValues getGearShift();

    boolean isSignalLeftTurn();

    boolean isSignalRightTurn();

    boolean isLaneKeepingOn();

    boolean isParkingPilotOn();

    int getAccSpeed();

    public boolean getSensorDebug();

    double getAccDistance();

    boolean isAccOn();

}
