package hu.oe.nik.szfmv.automatedcar.powertrain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Transmission Level Suggester Tests")
class TransmissionLevelSuggesterTest {

    private static final int MIN_LEVEL_IN_D = CarTransmissionMode.D_DRIVE.getMinimumLevel();
    private static final int MAX_LEVEL_IN_D = CarTransmissionMode.D_DRIVE.getMaximumLevel();

    @Test
    @DisplayName("suggests up when possible")
    void suggests_up_right() {
        TransmissionLevelSuggester impl = new TransmissionLevelSuggester();

        for (int level = MIN_LEVEL_IN_D; level < MAX_LEVEL_IN_D; ++level) {
            impl.update(Long.MAX_VALUE, level);
            assertEquals(level + 1, impl.getSuggestedLevel());
        }
    }

    @Test
    @DisplayName("suggests down when possible")
    void suggests_down_right() {
        TransmissionLevelSuggester impl = new TransmissionLevelSuggester();

        for (int level = MAX_LEVEL_IN_D; level > MIN_LEVEL_IN_D; --level) {
            impl.update(Long.MIN_VALUE, level);
            assertEquals(level - 1, impl.getSuggestedLevel());
        }
    }

    @Test
    @DisplayName("keeps highest level if there is no more upwards")
    void keeps_highest_level() {
        TransmissionLevelSuggester impl = new TransmissionLevelSuggester();

        impl.update(Long.MAX_VALUE, MAX_LEVEL_IN_D);

        assertEquals(MAX_LEVEL_IN_D, impl.getSuggestedLevel());
    }

    @Test
    @DisplayName("keeps lowest level if there is no more downwards")
    void keeps_lowest_level() {
        TransmissionLevelSuggester impl = new TransmissionLevelSuggester();

        impl.update(Long.MIN_VALUE, MIN_LEVEL_IN_D);

        assertEquals(MIN_LEVEL_IN_D, impl.getSuggestedLevel());
    }

}