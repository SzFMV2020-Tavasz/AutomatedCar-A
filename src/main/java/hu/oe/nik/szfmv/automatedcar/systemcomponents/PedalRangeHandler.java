package hu.oe.nik.szfmv.automatedcar.systemcomponents;

/**
 * Represents a value in the range between a minimum and a maximum value.
 * The value is increased or decreased when the loop method is called,
 * depending on the increase flag.
 *
 * @author  Robespierre19
 * @author  beregvarizoltan
 *
 * Date: 2019-04-09
 */

public class PedalRangeHandler {

    private static final int STEP_INC = 10;

    private static final int STEP_DEC = 10;

    private int minValue;

    private int maxValue;

    private int value;

    private boolean increase = false;

    public PedalRangeHandler(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        value = minValue;
    }

    public void loop() {
        if (increase) {
            value += STEP_INC;
            if (value > maxValue) {
                value = maxValue;
            }
        }
        else {
            value -= STEP_DEC;
            if (value < minValue) {
                value = minValue;
            }
        }
    }

    public void setIncrease(boolean increase) {
        this.increase = increase;
    }

    public int getValue() {
        return value;
    }
}
