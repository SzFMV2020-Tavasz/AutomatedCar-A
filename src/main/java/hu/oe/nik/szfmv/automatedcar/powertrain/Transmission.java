package hu.oe.nik.szfmv.automatedcar.powertrain;

public class Transmission implements ITransmission {

    /**
     * IMPORTANT!!!
     * These constant values define the gear shifting thresholds in {@link CarTransmissionMode#D_DRIVE D} mode.
     * Changing these values can effect the workings of the car extremely.
     * Tweak carefully.
     * TO-DO: calibrate !!!
     */
    private final int RPM_gear_0_upshift_threshold = 0;
    private final int RPM_gear_1_downshift_threshold = 0;
    private final int RPM_gear_1_upshift_threshold = 0;
    private final int RPM_gear_2_downshift_threshold = 0;
    private final int RPM_gear_2_upshift_threshold = 0;
    private final int RPM_gear_3_downshift_threshold = 0;
    private final int RPM_gear_3_upshift_threshold = 0;
    private final int RPM_gear_4_downshift_threshold = 0;
    private final int RPM_gear_4_upshift_threshold = 0;
    private final int RPM_gear_5_downshift_threshold = 0;

    /**
     * Field storing the automatic transmissions internal gear state in {@link CarTransmissionMode#D_DRIVE D} mode.
     * Zero equals neutral.
     * Range: 0-5
     */
    private int drive_internal_gear = 0;

    /**
     * Field storing the transmission's actual gear.
     */
    private CarTransmissionMode gearMode = CarTransmissionMode.P_PARKING;

    /**
     * Getter method for the {@link Transmission#drive_internal_gear} field.
     * @return
     */
    public int getDrive_internal_gear(){
        return drive_internal_gear;
    }

    /**
     * Getter method for {@link Transmission#gearMode} field.
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
     */
    @Override
    public boolean Shift(CarTransmissionMode gear) {
        return false;
    }

    /**
     * Calculates the actual state of the transmission when it is called.
     * (I strongly recommend, to call it in the SystemComponent's loop method.)
     */
    @Override
    public void Loop(int rpm) {
        //switching gears in drive mode
        if(gearMode == CarTransmissionMode.D_DRIVE){
            switch (drive_internal_gear){
                case 0:
                    if(rpm > RPM_gear_0_upshift_threshold) drive_internal_gear++;
                    break;
                case 1:
                    if(rpm > RPM_gear_1_upshift_threshold) drive_internal_gear++;
                    if(rpm < RPM_gear_1_downshift_threshold) drive_internal_gear--;
                    break;
                case 2:
                    if(rpm > RPM_gear_2_upshift_threshold) drive_internal_gear++;
                    if(rpm < RPM_gear_2_downshift_threshold) drive_internal_gear--;
                    break;
                case 3:
                    if(rpm > RPM_gear_3_upshift_threshold) drive_internal_gear++;
                    if(rpm < RPM_gear_3_downshift_threshold) drive_internal_gear--;
                    break;
                case 4:
                    if(rpm > RPM_gear_4_upshift_threshold) drive_internal_gear++;
                    if(rpm < RPM_gear_4_downshift_threshold) drive_internal_gear--;
                    break;
                case 5:
                    if(rpm < RPM_gear_5_downshift_threshold) drive_internal_gear--;
                    break;
            }
        }
        // switching gears in reverse mode
        if(gearMode == CarTransmissionMode.R_REVERSE){
            switch (drive_internal_gear){
                case 0:
                    if(rpm > RPM_gear_0_upshift_threshold) drive_internal_gear++;
                    break;
                case 1:
                    if(rpm < RPM_gear_1_downshift_threshold) drive_internal_gear--;
                    break;
            }
        }
    }
}
