package hu.oe.nik.szfmv.automatedcar.visualization;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisualizationConfigTest {

    String filename;

    @BeforeEach
    public void init() {
        filename = "road_2lane_tjunctionleft.png";
    }

    /**
     * Check whether the right reference point values are returned
     */
    @Test
    public void readValues() {

        VisualizationConfig.loadReferencePoints();
        assertEquals(875, VisualizationConfig.getReferencePoint(filename).getX());
        assertEquals(0, VisualizationConfig.getReferencePoint(filename).getY());
    }

    /**
     * Check whether the right reference point values are returned
     * if the loadReferencePoints method was not called before
     */
    @Test
    public void readValuesWithoutLoadingFirs() {
        assertEquals(875, VisualizationConfig.getReferencePoint(filename).getX());
        assertEquals(0, VisualizationConfig.getReferencePoint(filename).getY());
    }

}
