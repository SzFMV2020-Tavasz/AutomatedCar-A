package hu.oe.nik.szfmv.automatedcar.visualization;


import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private final int width = 770;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;
    private Gui parent;


    /**
     * Initialize the course display
     *
     * @param pt parent Gui
     */
    CourseDisplay(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setDoubleBuffered(true);
        setLayout(null);
        setBounds(0, 0, width, height);
        parent = pt;
    }


    /**
     * Inherited method that can paint on the JPanel.
     *
     * @param g     {@link Graphics} object that can draw to the canvas
     * @param world {@link World} object that describes the virtual world
     */
    private void paintComponent(Graphics g, World world) {

        g.drawImage(renderDoubleBufferedScreen(world), 0, 0, this);
    }

    /**
     * Rendering method to avoid flickering
     *
     * @param world {@link World} object that describes the virtual world
     * @return the ready to render doubleBufferedScreen
     */
    private BufferedImage renderDoubleBufferedScreen(World world) {
        BufferedImage doubleBufferedScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) doubleBufferedScreen.getGraphics();
        Rectangle r = new Rectangle(0, 0, width, height);
        g2d.setPaint(new Color(backgroundColor));
        g2d.fill(r);

        drawObjects(g2d, world);

        return doubleBufferedScreen;
    }


    public void drawWorld(World world) {
        paintComponent(getGraphics(), world);
    }

    private void drawObjects(Graphics2D g2d, World world) {

        for (WorldObject object : world.getWorldObjects()) {
            AffineTransform t = new AffineTransform();
            t.translate(object.getX(), object.getY());
            g2d.drawImage(object.getImage(), t, this);
        }
    }
}
