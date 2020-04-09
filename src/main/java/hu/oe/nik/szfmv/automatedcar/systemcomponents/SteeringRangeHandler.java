package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a steering wheel value in the range between -range and +range.
 * The value is increased or decreased when the loop method is called,
 * depending on the current state:
 *  - turn left
 *  - turn right
 *  - released
 *  When released, the value moves one step closer to zero.
 *
 * @author  Robespierre19
 * @author  beregvarizoltan
 *
 * Date: 2019-04-09
 */

public class SteeringRangeHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    enum TURNING_STATES {
        TURN_LEFT,
        TURN_RIGHT,
        RELEASE
    }

    private static final int STEP_TURN = 10;

    private static final int STEP_BACK_TO_CENTER = 10;

    private int range;

    private int value = 0;

    private TURNING_STATES state = TURNING_STATES.RELEASE;

    public SteeringRangeHandler(int range) {
        this.range = range;
    }

    public void turnLeft() {
        state = TURNING_STATES.TURN_LEFT;
    }

    public void turnRight() {
        state = TURNING_STATES.TURN_RIGHT;
    }

    public void release() {
        state = TURNING_STATES.RELEASE;
    }

    public void loop() {
        switch (state) {
            case TURN_LEFT:
                value -= STEP_TURN;
                if (value < -range) {
                    value = -range;
                }
                break;
            case TURN_RIGHT:
                value += STEP_TURN;
                if (value > range) {
                    value = range;
                }
                break;
            case RELEASE:
                if (value < 0) {
                    value += STEP_BACK_TO_CENTER;
                    value = (value > 0 ? 0 : value);
                }
                else if (value > 0) {
                    value -= STEP_BACK_TO_CENTER;
                    value = (value < 0 ? 0 : value);
                }
                break;
            default:
                LOGGER.error("Invalid state in SteeringRangeHandler: " + state);
        }
    }

    public int getValue() {
        return value;
    }
}
