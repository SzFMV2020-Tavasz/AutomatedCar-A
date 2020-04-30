package hu.oe.nik.szfmv.automatedcar.powertrain.off;

import static java.lang.System.currentTimeMillis;

public abstract class AccelerationModel1D {

    double acceleration = 0;

    long lastUpdateTimeStamp;

    protected AccelerationModel1D() {
        lastUpdateTimeStamp = currentTimeMillis();
    }

    protected abstract double getNaturalSlowingForce(double elapsedSeconds);

    protected abstract double calculateForce(double elapsedSeconds);

    protected abstract double getWeight();

    public double updateAcceleration() {
        long now = currentTimeMillis();
        double elapsedSeconds = (now - lastUpdateTimeStamp) / 1000.0;

        double weight = getWeight();
        applyExternalForce(elapsedSeconds, weight);
        applyNaturalSlowingForce(elapsedSeconds, weight);
        return this.acceleration;
    }

    private void applyNaturalSlowingForce(double elapsedSeconds, double weight) {
        double breakAcceleration = getNaturalSlowingForce(elapsedSeconds) / weight;
        if (breakAcceleration > acceleration) {
            this.acceleration = 0;
        } else {
            this.acceleration -= breakAcceleration;
        }
    }

    /**F = m * a ===> a = F / m
     * @param elapsedSeconds
     */
    private void applyExternalForce(double elapsedSeconds, double weight) {
        double externalForce = calculateForce(elapsedSeconds);
        acceleration += externalForce / weight;
    }

    public double getAcceleration() {
        return acceleration;
    }

}
