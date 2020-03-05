package hu.oe.nik.szfmv.automatedcar.visualization;

/**
 * Class for holding the base configuration data.
 *
 * Can be changed later here in one place or even read from a config text file.
 */
public final class VisualizationConfig {

    // The height and width of the CourseDisplay JPanel
    public static final int DISPLAY_WIDTH = 770;
    public static final int DISPLAY_HEIGHT = 700;

    // The egocar will be displayed at the center of the JPanel,
    // with 0 degrees rotation relative to the x axis
    public static final int DISPLAY_EGOCAR_CENTER_POSITION_X = DISPLAY_WIDTH / 2;
    public static final int DISPLAY_EGOCAR_CENTER_POSITION_Y = DISPLAY_HEIGHT / 2;
    public static final double DISPLAY_EGOCAR_ROTATION = Math.PI/2;

    // Needed for testing; will be removed later with actual passed/read data
    public static final int DISPLAY_EGOCAR_REFERENCE_X = 104;
    public static final int DISPLAY_EGOCAR_REFERENCE_Y = 51;
    public static final int DISPLAY_EGOCAR_ROTATION_ORIGO_X =
            DISPLAY_EGOCAR_CENTER_POSITION_X + DISPLAY_EGOCAR_REFERENCE_X;
    public static final int DISPLAY_EGOCAR_ROTATION_ORIGO_Y =
            DISPLAY_EGOCAR_CENTER_POSITION_Y - DISPLAY_EGOCAR_REFERENCE_Y;
    public static final double DISPLAY_EGOCAR_ROTATION_OF_IMG = Math.PI / 2;

    public static final int DISPLAY_RIGHT90_REF_POINT_X = 349;
    public static final int DISPLAY_RIGHT90_REF_POINT_Y = 525;

    /**
     * Constructor for static class
     *
     * it does not need to be instantiated.
     */
    private VisualizationConfig() {
    }

}
