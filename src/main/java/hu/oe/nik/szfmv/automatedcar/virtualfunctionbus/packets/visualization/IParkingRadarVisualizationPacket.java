package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import java.awt.Color;
import java.awt.geom.Point2D;

public interface IParkingRadarVisualizationPacket {
    /**
     * Gets the world source points of the parking radar sensor triangles
     *
     * @return source points
     */
    public Point2D[] getSources();

    /**
     * Gets the world corner1 points of the parking radar sensor triangles
     *
     * @return corner1 points
     */
    public Point2D[] getCorner1s();

    /**
     * Gets the world corner2 points of the parking radar sensor triangles
     *
     * @return source points
     */
    public Point2D[] getCorner2s();

    /**
     * Gets the color of the parking radar sensor triangles
     *
     * @return the color
     */
    public Color getColor();
}
