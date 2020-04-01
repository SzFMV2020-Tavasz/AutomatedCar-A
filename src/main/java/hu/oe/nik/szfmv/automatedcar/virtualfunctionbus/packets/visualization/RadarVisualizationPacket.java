package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import java.awt.*;
import java.awt.geom.Point2D;

public class RadarVisualizationPacket implements IRadarVisualizationPacket {
    private Point2D source;
    private Point2D corner1;
    private Point2D corner2;
    private Color color;

    public RadarVisualizationPacket() {

    }

    /**
     * Sets the radarTriangle for display
     * @param source the source point of the radar sensor triangle
     * @param corner1 the first corner point of the radar sensor triangle
     * @param corner2 the second corner point of the radar sensor triangle
     * @param color the color of the radar sensor triangle. Only needs the RGB values,
     *              transparency will be added by visualization
     */
    public void setSensorTriangle(Point2D source, Point2D corner1, Point2D corner2, Color color) {
        this.source = source;
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.color = color;
    }

    /**
     * Gets the world source point of the radar triangle
     * @return source point
     */
    @Override
    public Point2D getSensorSource() {
        return source;
    }

    /**
     * Gets the world corner1 point of the radar triangle
     * @return corner1 point
     */
    @Override
    public Point2D getSensorCorner1() {
        return corner1;
    }

    /**
     * Gets the world corner2 point of the radar triangle
     * @return corner2 point
     */
    @Override
    public Point2D getSensorCorner2() {
        return corner2;
    }

    /**
     * Gets the color of the radar triangle
     * @return the color
     */
    @Override
    public Color getColor() {
        return color;
    }
}
