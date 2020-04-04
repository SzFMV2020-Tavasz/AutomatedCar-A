package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

import java.awt.*;
import java.awt.geom.Point2D;

public interface ICameraVisualizationPacket {
    /**
     * Gets the world source point of the camera sensor triangle
     * @return source point
     */
    Point2D getSensorSource();

    /**
     * Gets the world corner1 point of the camera sensor triangle
     * @return corner1 point
     */
    Point2D getSensorCorner1();

    /**
     * Gets the world corner2 point of the camera sensor triangle
     * @return corner2 point
     */
    Point2D getSensorCorner2();

    /**
     * Gets the color of the camera sensor triangle
     * @return the color
     */
    Color getColor();
}
