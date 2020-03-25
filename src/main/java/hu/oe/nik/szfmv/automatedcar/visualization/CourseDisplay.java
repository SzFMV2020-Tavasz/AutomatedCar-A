package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.model.World;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
        setBackground(new Color(backgroundColor));
        parent = pt;

    }


    /**
     * Inherited method that can paint on the JPanel.
     *
     * @param g            {@link Graphics} object that can draw to the canvas
     * @param displayWorld {@link World} object that describes the virtual world to be displayed
     */
    private void paintComponent(Graphics g, DisplayWorld displayWorld) {

        g.drawImage(renderDoubleBufferedScreen(displayWorld), 0, 0, this);
    }

    /**
     * Rendering method to avoid flickering
     *
     * @param displayWorld {@link DisplayWorld} object that describes the virtual world
     * @return the ready to render doubleBufferedScreen
     */
    private BufferedImage renderDoubleBufferedScreen(DisplayWorld displayWorld) {
        BufferedImage doubleBufferedScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) doubleBufferedScreen.getGraphics();
        Rectangle r = new Rectangle(0, 0, width, height);
        g2d.setPaint(new Color(backgroundColor));
        g2d.fill(r);

        drawObjects(g2d, displayWorld);

        return doubleBufferedScreen;
    }

    public void drawWorld(DisplayWorld displayWorld) {
        paintComponent(getGraphics(), displayWorld);
    }

    private void drawObjects(Graphics2D g2d, DisplayWorld displayWorld) {

        ArrayList<Path2D> runOfTheMillDebugPolygons = new ArrayList<>();
        ArrayList<Path2D> selectedDebugPolygons = new ArrayList<>();

        for (DisplayObject object : displayWorld.getDisplayObjects()) {
           drawDisplayObject(g2d, object);

            // draw debug polygons that are not selected individually.
            Path2D poly =  object.getDebugPolygon();
            // check if the polygon actually exists
            if (poly != null) {
                if (displayWorld.isDebugOn() && !displayWorld.getDebugObjects().contains(object.getId())) {
                    runOfTheMillDebugPolygons.add(poly);
                } else {
                    selectedDebugPolygons.add(poly);
                }
            }
        }

        drawPolygon(g2d, runOfTheMillDebugPolygons, selectedDebugPolygons);

        drawDisplayObject(g2d, displayWorld.getEgoCar());

        // needs to be drawn last so it shows above everything
        drawSensorsIfSet(g2d, displayWorld);
    }

    private void drawDisplayObject(Graphics2D g2d, DisplayObject object) {
        AffineTransform t = new AffineTransform();
        t.translate(object.getX() + object.getRotationDisplacementX() - object.getRefDifferenceX(),
            object.getY() + object.getRotationDisplacementY() - object.getRefDifferenceY());
        t.rotate(object.getRotation());
        g2d.drawImage(object.getImage(), t, this);
    }

    /**
     * Draws the rotated Debug polygon of the displayObject
     */
    private void drawPolygon(Graphics2D g2d,
                             ArrayList<Path2D> runOfTheMillDebugPolygons,  ArrayList<Path2D> selectedDebugPolygons) {
        for (Path2D poly : runOfTheMillDebugPolygons) {
            g2d.setStroke(VisualizationConfig.DEBUG_LINETYPE);
            g2d.setColor(VisualizationConfig.RUN_OF_THE_MILL_DEBUG_COLOR);
            g2d.draw(poly);
        }

        for (Path2D poly : selectedDebugPolygons) {
            g2d.setStroke(VisualizationConfig.DEBUG_LINETYPE);
            g2d.setColor(VisualizationConfig.SELECTED_DEBUG_COLOR);
            g2d.draw(poly);
        }
    }

    private void drawSensorsIfSet(Graphics2D g2d, DisplayWorld displayWorld) {
        if (displayWorld.isCameraShown()) {
            drawCameraSensor(g2d);
        }

        if (displayWorld.isRadarShown()) {
            drawRadarSensor(g2d);
        }

        if (displayWorld.isUltrasoundShown()) {
            drawUltraSoundSensor(g2d);
        }
    }

    private void drawCameraSensor(Graphics2D g2d) {

        g2d.setColor(VisualizationConfig.CAMERA_SENSOR_BG_COLOUR);
        g2d.fillPolygon(VisualizationConfig.camera_sensor_polygon);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.black);
        g2d.drawPolygon(VisualizationConfig.camera_sensor_polygon);

        g2d.setStroke(VisualizationConfig.SENZOR_CENTER_LINE);
        g2d.draw(VisualizationConfig.camera_sensor_centerline);
    }

    private void drawRadarSensor(Graphics2D g2d) {
        g2d.setColor(VisualizationConfig.RADAR_SENSOR_BG_COLOUR);
        g2d.fillPolygon(VisualizationConfig.radar_sensor_polygon);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.black);
        g2d.drawPolygon(VisualizationConfig.radar_sensor_polygon);

        g2d.setStroke(VisualizationConfig.SENZOR_CENTER_LINE);
        g2d.draw(VisualizationConfig.radar_sensor_centerline);
    }

    private void drawUltraSoundSensor(Graphics2D g2d) {
        for (Polygon poly : VisualizationConfig.ultrasound_sensor_polygons) {
            if (poly != null) {
                g2d.setColor(VisualizationConfig.ULTRASOUND_SENSOR_BG_COLOUR);
                g2d.fillPolygon(poly);
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(Color.black);
                g2d.drawPolygon(poly);
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(Color.black);
                g2d.drawPolygon(poly);
            }
        }
    }
}
