package hu.oe.nik.szfmv.automatedcar.powertrain.off;

import hu.oe.nik.szfmv.automatedcar.math.IVector;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.nullVector;
import static java.lang.System.currentTimeMillis;

public abstract class AccelerationModel2D {

    IVector moveVector = nullVector();

    long lastUpdateTimeStamp;

    protected AccelerationModel2D() {
        lastUpdateTimeStamp = currentTimeMillis();
    }

    protected double getNaturalSlowingForce(double elapsedSeconds) {
        return 0.0;
    }

    protected IVector calculateForce(double elapsedSeconds) {
        return IVector.vectorFromXY(0,0);
    }

    protected double getWeight() {
        return 10000;
    }

    public void updateAcceleration() {
        long now = currentTimeMillis();
        double elapsedSeconds = (now - lastUpdateTimeStamp) / 1000.0;

        double weight = getWeight();
        applyExternalForce(elapsedSeconds, weight);
        applyNaturalSlowingForce(elapsedSeconds, weight);
    }

    private void applyNaturalSlowingForce(double elapsedSeconds, double weight) {
        double breakAcceleration = getNaturalSlowingForce(elapsedSeconds) / weight;
        double currentNewAccelerationPower = this.moveVector.getLength();
        if (breakAcceleration > currentNewAccelerationPower) {
            this.moveVector = nullVector();
        } else {
            this.moveVector.withLength(currentNewAccelerationPower - breakAcceleration);
        }
    }

    /**F = m * a ===> a = F / m
     * @param elapsedSeconds
     */
    private void applyExternalForce(double elapsedSeconds, double weight) {
        IVector externalForce = calculateForce(elapsedSeconds);
        moveVector = moveVector.add(externalForce, externalForce.getLength() / weight);
    }

    public IVector getMoveVector() {
        return moveVector;
    }

}
