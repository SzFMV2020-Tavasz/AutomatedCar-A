package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import hu.oe.nik.szfmv.automatedcar.visualization.UltrasoundPositions;
import hu.oe.nik.szfmv.automatedcar.visualization.VisualizationConfig;

import java.awt.*;
import java.awt.geom.Point2D;

public class UltrasoundsVisualizationPacket implements IUltrasoundsVisualizationPacket {

    private Point2D[] sources;
    private Point2D[] corner1s;
    private Point2D[] corner2s;
    private Color color;

    public UltrasoundsVisualizationPacket() {
        sources = new Point2D[VisualizationConfig.ULTRASOUND_SENSORS_COUNT];
        corner1s = new Point2D[VisualizationConfig.ULTRASOUND_SENSORS_COUNT];
        corner2s = new Point2D[VisualizationConfig.ULTRASOUND_SENSORS_COUNT];
    }

    /**
     * Sets the ultrasound sensor triangle for display
     *
     * @param source  the source point of the camera sensor triangle
     * @param corner1 the first corner point of the camera sensor triangle
     * @param corner2 the second corner point of the camera sensor triangle
     */
    public void setSensorTriangle(UltrasoundPositions ultrasoundPosition,
                                  Point2D source, Point2D corner1, Point2D corner2) {
        sources[ultrasoundPosition.getNumVal()] = source;
        corner1s[ultrasoundPosition.getNumVal()] = corner1;
        corner2s[ultrasoundPosition.getNumVal()] = corner2;
        color = new Color(0, 0, 0);
    }

    /**
     * Sets the color of the ultrasound triangles.
     *
     * @param color the color of the radar sensor triangle. Only needs the RGB values,
     *              transparency will be added by visualization
     */
    public void setSensorColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the world source points of the ultrasound sensor triangles
     *
     * @return source points
     */
    public Point2D[] getSources() {
        return sources;
    }

    /**
     * Gets the world corner1 points of the ultrasound sensor triangles
     *
     * @return corner1 points
     */
    public Point2D[] getCorner1s() {
        return corner1s;
    }

    /**
     * Gets the world corner2 points of the ultrasound sensor triangles
     *
     * @return source points
     */
    public Point2D[] getCorner2s() {
        return corner2s;
    }

    /**
     * Gets the color of the ultrasound camera sensor triangle
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }
}
