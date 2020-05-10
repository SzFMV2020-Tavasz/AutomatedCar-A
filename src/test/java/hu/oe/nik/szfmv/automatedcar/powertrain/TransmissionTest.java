package hu.oe.nik.szfmv.automatedcar.powertrain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static java.util.Comparator.reverseOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Transmission Tests")
class TransmissionTest {

    SimpleTransmission testSubject = new SimpleTransmission();

    @Test
    @DisplayName("transmission shifts properly")
    void shifts() {
        stream(CarTransmissionMode.values()).forEachOrdered(testMode -> {
            for (int testLevel = testMode.getMinimumLevel(); testLevel <= testMode.getMaximumLevel(); ++testLevel) {
                testSubject.forceShift(testMode, testLevel);
                assertEquals(testSubject.getCurrentTransmissionMode(), testMode);
                assertEquals(testSubject.getCurrentTransmissionLevel(), testLevel);
            }
        });

        stream(CarTransmissionMode.values()).sorted(reverseOrder()).forEachOrdered(testMode -> {
            for (int testLevel = testMode.getMaximumLevel(); testLevel >= testMode.getMinimumLevel(); --testLevel) {
                testSubject.forceShift(testMode, testLevel);
                assertEquals(testSubject.getCurrentTransmissionMode(), testMode);
                assertEquals(testSubject.getCurrentTransmissionLevel(), testLevel);
            }
        });
    }

    @Test
    @DisplayName("gas pedal increases RPM")
    void test_gas_pedal_rpm_acceleration() {
        testSubject.forceShift(CarTransmissionMode.D_DRIVE, 1);
        testSubject.update(1);

        long previousRPM = 0;
        for (int i = 0; i < 10; ++i) {
            sleepSome();
            testSubject.update(1);
            long newRPM = testSubject.getCurrentRPM();
            assertTrue(newRPM > previousRPM);
            previousRPM = newRPM;
        }
    }

    private void sleepSome() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}