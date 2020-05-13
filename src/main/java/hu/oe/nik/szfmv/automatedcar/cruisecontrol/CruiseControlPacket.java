package hu.oe.nik.szfmv.automatedcar.cruisecontrol;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.cruisecontrol.ICruiseControlPacket;

import static java.util.Objects.requireNonNull;

/**@author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public final class CruiseControlPacket implements ICruiseControlPacket {

    private final double gasPedalRatio;
    private final double breakPedalRatio;
    private final double steeringWheelRotationRatio;

    private CruiseControlPacket(double gasPedalRatio, double breakPedalRatio, double steeringWheelRotationRatio) {
        this.gasPedalRatio = gasPedalRatio;
        this.breakPedalRatio = breakPedalRatio;
        this.steeringWheelRotationRatio = steeringWheelRotationRatio;
    }

    @Override
    public double getGasPedalRatio() {
        return gasPedalRatio;
    }

    @Override
    public double getBreakPedalRatio() {
        return breakPedalRatio;
    }

    @Override
    public double getSteeringWheelRotation() {
        return steeringWheelRotationRatio;
    }

    @Override
    public double getMaxSteeringWheelRotation() {
        return 1.0;
    }

    public static Builder newCruiseControlPacket() {
        return new Builder();
    }

    public static class Builder {
        private Double gasPedalRatio;
        private Double breakPedalRatio;
        private Double steeringWheelRotationRatio;

        public Builder withGasPedalRatio(Double gasPedalRatio) {
            this.gasPedalRatio = gasPedalRatio;
            return this;
        }

        public Builder withBreakPedalRatio(Double breakPedalRatio) {
            this.breakPedalRatio = breakPedalRatio;
            return this;
        }

        /**@param rotationRatio - between {@code -1.0} and {@code +1.0}.*/
        public Builder withSteeringWheelRotationRatio(Double rotationRatio) {
            this.steeringWheelRotationRatio = rotationRatio;
            return this;
        }

        public CruiseControlPacket build() {
            return new CruiseControlPacket(
                    requireNonNull(gasPedalRatio, "Tempomat gas pedal value must be given."),
                    requireNonNull(breakPedalRatio, "Tempomat break pedal value must be given."),
                    requireNonNull(steeringWheelRotationRatio, "Tempomat steering wheel value must be given.")
            );
        }
    }

}
