package hu.oe.nik.szfmv.automatedcar.cruisecontrol;

import hu.oe.nik.szfmv.automatedcar.CarIndexState;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.cruisecontrol.CruiseControlPacket;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**@author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public final class CruiseControlPacketData implements CruiseControlPacket {

    private final int gasPedalValue;
    private final int breakPedalValue;
    private final int steeringWheelValue;
    private final CarIndexState carIndexState;
    private final Shitfer.ShiftPos shiftValue;

    private CruiseControlPacketData(int gasPedalValue, int breakPedalValue, int steeringWheelValue, CarIndexState carIndexState, Shitfer.ShiftPos getShiftValue) {
        this.gasPedalValue = gasPedalValue;
        this.breakPedalValue = breakPedalValue;
        this.steeringWheelValue = steeringWheelValue;
        this.carIndexState = carIndexState;
        this.shiftValue = getShiftValue;
    }

    @Override
    public int getGasPedalValue() {
        return gasPedalValue;
    }

    @Override
    public int getBreakPedalValue() {
        return breakPedalValue;
    }

    @Override
    public int getSteeringWheelValue() {
        return steeringWheelValue;
    }

    @SuppressWarnings("removal")
    @Override
    public int getIndexValue() {
        return carIndexState.asValue();
    }

    @Override
    public CarIndexState getIndexState() {
        return carIndexState;
    }

    @Override
    public Shitfer.ShiftPos getShiftValue() {
        return shiftValue;
    }

    public static Builder newCruiseControlPacket() {
        return new Builder();
    }

    public static class Builder {
        private Integer gasPedalValue;
        private Integer breakPedalValue;
        private Integer steeringWheelValue;
        private CarIndexState indexState;
        private Shitfer.ShiftPos shiftValue;

        public void withGasPedalValue(Integer gasPedalValue) {
            this.gasPedalValue = gasPedalValue;
        }

        public void withBreakPedalValue(Integer breakPedalValue) {
            this.breakPedalValue = breakPedalValue;
        }

        public void withSteeringWheelValue(Integer steeringWheelValue) {
            this.steeringWheelValue = steeringWheelValue;
        }

        public void withIndexState(CarIndexState indexState) {
            this.indexState = indexState;
        }

        public void withShiftValue(Shitfer.ShiftPos getShiftValue) {
            this.shiftValue = getShiftValue;
        }

        public Builder apply(Consumer<Builder> patch) {
            patch.accept(this);
            return this;
        }

        public CruiseControlPacketData build() {
            return new CruiseControlPacketData(
                    requireNonNull(gasPedalValue, "Tempomat gas pedal value must be given."),
                    requireNonNull(breakPedalValue, "Tempomat break pedal value must be given."),
                    requireNonNull(steeringWheelValue, "Tempomat steering wheel value must be given."),
                    requireNonNull(indexState, "Tempomat index state must be given."),
                    requireNonNull(shiftValue, "Tempomat shift value must be given.")
            );
        }
    }

}
