package hu.oe.nik.szfmv.automatedcar.powertrain;

public class Transmission implements ITransmission {

    /**
     * IMPORTANT!!!
     * These constant values define the gear shifting thresholds in {@link CarTransmissionMode#D_DRIVE D} mode.
     * Changing these values can effect the workings of the car extremely.
     * Tweak carefully.
     * TODO: calibrate !!!
     */
    private static int RPM_GEAR_0_UPSHIFT_THRESHOLD = 2000;
    private static int RPM_GEAR_1_DOWNSHIFT_THRESHOLD = 1500;
    private static int RPM_GEAR_1_UPSHIFT_THRESHOLD = 6000;
    private static int RPM_GEAR_2_DOWNSHIFT_THRESHOLD = 3000;
    private static int RPM_GEAR_2_UPSHIFT_THRESHOLD = 6000;
    private static int RPM_GEAR_3_DOWNSHIFT_THRESHOLD = 3000;
    private static int RPM_GEAR_3_UPSHIFT_THRESHOLD = 6000;
    private static int RPM_GEAR_4_DOWNSHIFT_THRESHOLD = 3000;
    private static int RPM_GEAR_4_UPSHIFT_THRESHOLD = 6000;
    private static int RPM_GEAR_5_DOWNSHIFT_THRESHOLD = 3000;

    /**
     * Field storing the automatic transmissions internal gear state in {@link CarTransmissionMode#D_DRIVE D} mode.
     * Zero equals neutral.
     * Range: 0-5
     */
    private int driveinternalgear = 0;

    /**
     * Field storing the transmission's actual gear.
     */
    private CarTransmissionMode gearMode = CarTransmissionMode.P_PARKING;

    /**
     * Gets the automatic transmissions actual gear in drive mode.
     * @return Returns the actual gear. 
     */
    public int getDriveInternalGear(){
        return driveinternalgear;
    }

    /**
     * Gets the actual gear mode.
     *
     * @return The actual value of the gearMode field.
     */
    @Override
    public CarTransmissionMode getGearMode() {
        return gearMode;
    }

    /**
     * Requests the transmission to shift to a desired gear.
     * Keep in mind, the transmission will not change speed, if you ask for impossible things.
     * (Like going reverse from drive, when the car has forward speed for example.)
     *
     * @param gear The gear you want to switch into.
     * @return Returns true if the shifting was successful, false if it was unsuccessful.
     *
     * TODO: This implementation will change speeds anytime at the moment. We need to check the engine status before speed change.
     */
    @Override
    public boolean shift(CarTransmissionMode gear) {
        gearMode = gear;
        return true;
    }

    /**
     * Calculates the actual state of the transmission when it is called.
     * (I strongly recommend, to call it in the SystemComponent's loop method.)
     */
    @Override
    public void loop(int rpm) {
        switchGearsFowrard(rpm);
        switchGearsReverse(rpm);
    }

    /**
     * Switching gears in forward mode.
     * @param rpm The actual RPM value.
     */
    private void switchGearsReverse(int rpm) {
        if(gearMode == CarTransmissionMode.R_REVERSE){
            switch (driveinternalgear){
                case 0:
                    if(rpm > RPM_GEAR_0_UPSHIFT_THRESHOLD){
                        driveinternalgear++;
                    }
                    break;
                case 1:
                    if(rpm < RPM_GEAR_1_DOWNSHIFT_THRESHOLD){
                        driveinternalgear--;
                    }
                    break;
            }
        }
    }

    /**
     * Switching gears in reverse mode.
     * @param rpm The actual RPM value.
     */
    private void switchGearsFowrard(int rpm) {
        //switching gears in drive mode
        if(gearMode == CarTransmissionMode.D_DRIVE){
            switch (driveinternalgear){
                case 0:
                    if(rpm > RPM_GEAR_0_UPSHIFT_THRESHOLD) {
                        driveinternalgear++;
                    }
                    break;
                case 1:
                    if(rpm > RPM_GEAR_1_UPSHIFT_THRESHOLD) {
                        driveinternalgear++;
                    }
                    if(rpm < RPM_GEAR_1_DOWNSHIFT_THRESHOLD){
                        driveinternalgear--;
                    }
                    break;
                case 2:
                    if(rpm > RPM_GEAR_2_UPSHIFT_THRESHOLD){
                        driveinternalgear++;
                    }
                    if(rpm < RPM_GEAR_2_DOWNSHIFT_THRESHOLD){
                        driveinternalgear--;
                    }
                    break;
                case 3:
                    if(rpm > RPM_GEAR_3_UPSHIFT_THRESHOLD){
                        driveinternalgear++;
                    }
                    if(rpm < RPM_GEAR_3_DOWNSHIFT_THRESHOLD){
                        driveinternalgear--;
                    }
                    break;
                case 4:
                    if(rpm > RPM_GEAR_4_UPSHIFT_THRESHOLD){
                        driveinternalgear++;
                    }
                    if(rpm < RPM_GEAR_4_DOWNSHIFT_THRESHOLD){
                        driveinternalgear--;
                    }
                    break;
                case 5:
                    if(rpm < RPM_GEAR_5_DOWNSHIFT_THRESHOLD){
                        driveinternalgear--;
                    }
                    break;
            }
        }
    }
}
