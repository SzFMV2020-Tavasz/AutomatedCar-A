package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

/**
 * Packet class containing the output values of the keyboard input component.
 *
 * @author  beregvarizoltan
 * @author  BazsoGabor
 * @author  attilanemeth
 *
 * Date: 2019-05-07
 */

public class InputPacket implements ReadOnlyInputPacket {

    /**
     * 0 ... 100
     */
    private int gasPedal = 0;

    /**
     * 0 ... 100
     */
    private int breakPedal = 0;

    /**
     * -100 ... +100
     */
    private int steeringWheel = 0;

    private GearShiftValues gearShift = GearShiftValues.P;

    private boolean signalLeftTurn = false;

    private boolean signalRightTurn = false;

    private boolean laneKeepingOn = false;

    private boolean parkingPilotOn = false;
    private boolean sensorDebug = false;

    /**
     * Automatic Cruise Control target speed
     * 0: ACC switched off
     * 30 ... 160: ACC switched on
     */
    private int accSpeed = 0;

    /**
     * Automatic Cruise Control target distance
     * Possible values are: 0.8, 1.0, 1.2, 1.4 (sec)
     */
    private double accDistance = 0.8;

    private boolean accOn = false;

    public int getGasPedal() {
        return gasPedal;
    }

    public void setGasPedal(int gasPedal) {
        this.gasPedal = gasPedal;
    }

    public int getBreakPedal() {
        return breakPedal;
    }

    public void setBreakPedal(int breakPedal) {
        this.breakPedal = breakPedal;
    }

    public int getSteeringWheel() {
        return steeringWheel;
    }

    public void setSteeringWheel(int steeringWheel) {
        this.steeringWheel = steeringWheel;
    }

    public GearShiftValues getGearShift() {
        return gearShift;
    }

    public void setGearShift(GearShiftValues gearShift) {
        this.gearShift = gearShift;
    }

    public boolean isSignalLeftTurn() {
        return signalLeftTurn;
    }

    public void setSignalLeftTurn(boolean signalLeftTurn) {
        this.signalLeftTurn = signalLeftTurn;
    }

    public boolean isSignalRightTurn() {
        return signalRightTurn;
    }

    public void setSignalRightTurn(boolean signalRightTurn) {
        this.signalRightTurn = signalRightTurn;
    }

    public boolean isLaneKeepingOn() {
        return laneKeepingOn;
    }

    public void setLaneKeepingOn(boolean laneKeepingOn) {
        this.laneKeepingOn = laneKeepingOn;
    }

    public boolean isParkingPilotOn() {
        return parkingPilotOn;
    }

    public void setParkingPilotOn(boolean parkingPilotOn) {
        this.parkingPilotOn = parkingPilotOn;
    }

    public int getAccSpeed() {
        return accSpeed;
    }

    public void setAccSpeed(int accSpeed) {
        this.accSpeed = accSpeed;
    }

    public double getAccDistance() {
        return accDistance;
    }

    @Override
    public boolean getSensorDebug() {
        return sensorDebug;
    }

    public void toggleSensorDebug() {
        sensorDebug = !sensorDebug;
    }

    public void setAccDistance(double accDistance) {
        this.accDistance = accDistance;
    }

    @Override
    public boolean isAccOn() {
        return accOn;
    }

    public void setAccOn(boolean accOn) {
        this.accOn = accOn;
    }
}
