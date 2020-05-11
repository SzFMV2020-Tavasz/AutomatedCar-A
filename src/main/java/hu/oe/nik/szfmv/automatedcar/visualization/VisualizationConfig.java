package hu.oe.nik.szfmv.automatedcar.visualization;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Class for holding the base configuration data.
 * <p>
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
    public static final float DISPLAY_EGOCAR_ROTATION = 0; //-(float) Math.PI / 6;

    // set sensor polygon defaults
    public static final Stroke SENSOR_CENTER_LINE = new BasicStroke(2,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    public static final int METER_IN_PIXELS = 50; // A car is 240 px, an average car is a little shorter than 5 m....

    // ultrasound sensors
    public static final int ULTRASOUND_SENSORS_COUNT = 8;
    public static final int SENSOR_COLOR_ALPHA = 125;

    // radar sensors
    public static final int PARKING_RADAR_SENSORS_COUNT = 2;

    // debug mode color
    public static final Color RUN_OF_THE_MILL_DEBUG_COLOR = new Color(255, 0, 255);
    // selected element color
    public static final Color SELECTED_DEBUG_COLOR = new Color(255, 0, 0);
    // debug linetype
    public static final Stroke DEBUG_LINETYPE = new BasicStroke(3);

    public static Polygon camera_sensor_polygon;
    public static Shape camera_sensor_centerline;

    public static Polygon radar_sensor_polygon;
    public static Shape radar_sensor_centerline;

    public static ArrayList<Polygon> ultrasound_sensor_polygons;

    // Dictionary for image reference points
    private static Hashtable<String, Point2D> my_dict;

    /**
     * Constructor for static class
     * <p>
     * it does not need to be instantiated.
     */
    private VisualizationConfig() {
    }



   /* }

    private static void calculateRadarCenterLine() {
        // sensor triangle centerline endpoint
        double sensorTX = 0;
        double sensorTY = -RADAR_SENSOR_RANGE;
        AffineTransform tT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorTX, -sensorTY);
        AffineTransform sT =
            AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -RADAR_SENSOR_DX, -RADAR_SENSOR_DY);

        radar_sensor_centerline = new Line2D.Float(
            RADAR_SENSOR_X + (int) sT.getTranslateX(),
            RADAR_SENSOR_Y + (int) sT.getTranslateY(),
            (float) (RADAR_SENSOR_X + sensorTX + tT.getTranslateX()),
            (float) (RADAR_SENSOR_Y + sensorTY + tT.getTranslateY()));
    }
*/

    /**
     * Parse the xml file containing the reference points (rotation origos) of the image files
     */
    public static void loadReferencePoints(String fileName) {
        if (my_dict == null) {
            try {
                // initialize the dictionary
                my_dict = new Hashtable<>();

                File xmlFile = new File(ClassLoader.getSystemResource(fileName).getFile());
                DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document xmlDoc = docBuilder.parse(xmlFile);

                NodeList imageNodes = xmlDoc.getElementsByTagName("Image");
                for (int i = 0; i < imageNodes.getLength(); i++) {
                    Node imageNode = imageNodes.item(i);
                    storeRefPoints(imageNode);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    /**
     * Write the node dictionary entry where the imagefilename is the key
     * and the xy of the refrence points are the value as a Point2D
     *
     * @param imageNode the Node that needs to be examined
     */
    private static void storeRefPoints(Node imageNode) {
        if (imageNode.getNodeType() == Node.ELEMENT_NODE && imageNode.getNodeName().equals("Image")) {
            String fileName = imageNode.getAttributes().getNamedItem("name").getNodeValue();
            Node refPoint = imageNode.getFirstChild().getNextSibling();
            if (refPoint.getNodeName().equals("Refpoint")) {
                int xValue = Integer.parseInt(refPoint.getAttributes().item(0).getNodeValue());
                int yValue = Integer.parseInt(refPoint.getAttributes().item(1).getNodeValue());

                my_dict.put(fileName, new Point2D.Float(xValue, yValue));
            }
        }
    }

    /**
     * Gets the reference point of the image.
     *
     * @param filename The name of the image
     * @return The reference point of the image. The type is float, my needs to be casted to integer.
     */
    public static Point2D getReferencePoint(String filename) {
        Point2D refPoint = null;
        if (my_dict != null && !my_dict.isEmpty()) {
            refPoint = my_dict.get(filename);
        }

        if (refPoint == null) {
            refPoint = new Point2D.Float(0, 0);
        }
        return refPoint;
    }

    /**
     * Calulates the rotation angle from the rotation matrix
     *
     * @param matrix the rotationmatrix
     * @return the clockwise angle
     */
    public static float getAngleFromRotationMatrix(float[][] matrix) {
        double angle1 = Math.acos(matrix[0][0]);
        float sinValue = matrix[1][0];
        double angleFinal;

        if (sinValue >= 0) {
            angleFinal = angle1;
        } else {
            angleFinal = Math.PI * 2 - angle1;
        }
        return (float) -angleFinal;
    }
}
