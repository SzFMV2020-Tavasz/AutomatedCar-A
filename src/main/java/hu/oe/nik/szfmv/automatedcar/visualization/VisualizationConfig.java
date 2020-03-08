package hu.oe.nik.szfmv.automatedcar.visualization;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
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
    public static final float DISPLAY_EGOCAR_ROTATION = 0;

    // set senzor polygons

    public static final Stroke SENZOR_CENTER_LINE = new BasicStroke(2,
        BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    public static final int METER_IN_PIXELS = 50; // A car is 240 px, an average car is a little shorter than 5 m....

    // camera sensors
    public static final double CAMERA_SENSOR_ANGLE = Math.PI / 6;  // 60 /2 - half angle
    public static final int CAMERA_SENSOR_RANGE = 80 * METER_IN_PIXELS;
    public static final Color CAMERA_SENSOR_BG_COLOUR = new Color(0, 84, 166, 125);

    // radar sensors
    public static final int RADAR_SENSOR_DX = 0;
    public static final int RADAR_SENSOR_DY = -104;

    public static final int RADAR_SENSOR_X = DISPLAY_EGOCAR_CENTER_POSITION_X + RADAR_SENSOR_DX;
    public static final int RADAR_SENSOR_Y = DISPLAY_EGOCAR_CENTER_POSITION_Y + RADAR_SENSOR_DY;

    public static final double RADAR_SENSOR_ANGLE = Math.PI / 6;  // 60 /2 - half angle
    public static final int RADAR_SENSOR_RANGE = 200 * METER_IN_PIXELS;
    public static final Color RADAR_SENSOR_BG_COLOUR = new Color(121, 0, 0, 125);

    // ultrasound sensors
    public static final double ULTRASOUND_SENSOR_ANGLE = Math.toRadians(50);  // 100 / 2 - half angle
    public static final int ULTRASOUND_SENSOR_RANGE = 3 * METER_IN_PIXELS;
    public static final Color ULTRASOUND_SENSOR_BG_COLOUR = new Color(0, 255, 0, 125);

    public static final int ULTRASOUND_AXIS_DIFF_S1_X = 35;
    public static final int ULTRASOUND_AXIS_DIFF_S1_Y = 95;
    public static final int ULTRASOUND_AXIS_DIFF_S2_X = 45;
    public static final int ULTRASOUND_AXIS_DIFF_S2_Y = 75;

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

    /**
     * Calculates the points of sensors of the egocar
     */
    public static void calculateSensorPolygons() {
        calculateCameraSensorPolygon();
        calculateCameraCenterLine();
        calculateRadarSensorPolygon();
        calculateRadarCenterLine();
        calculateUltraSoundSensorPolygons();
    }

    private static void calculateCameraSensorPolygon() {
        // sensor triangle A corner
        double sensorAX = -CAMERA_SENSOR_RANGE * Math.tan(CAMERA_SENSOR_ANGLE);
        double sensorAY = -CAMERA_SENSOR_RANGE;
        // sensor triangle B corner
        double sensorBX = CAMERA_SENSOR_RANGE * Math.tan(CAMERA_SENSOR_ANGLE);
        double sensorBY = -CAMERA_SENSOR_RANGE;

        AffineTransform aT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorAX, -sensorAY);
        AffineTransform bT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorBX, -sensorBY);

        int[] xPoly = {DISPLAY_EGOCAR_CENTER_POSITION_X,
            (int) (DISPLAY_EGOCAR_CENTER_POSITION_X + sensorAX + aT.getTranslateX()),
            (int) (DISPLAY_EGOCAR_CENTER_POSITION_X + sensorBX + bT.getTranslateX())};
        int[] yPoly = {DISPLAY_EGOCAR_CENTER_POSITION_Y,
            (int) (DISPLAY_EGOCAR_CENTER_POSITION_Y + sensorAY + aT.getTranslateY()),
            (int) (DISPLAY_EGOCAR_CENTER_POSITION_Y + sensorBY + bT.getTranslateY())};
        camera_sensor_polygon = new Polygon(xPoly, yPoly, xPoly.length);
    }

    private static void calculateCameraCenterLine() {
        // sensor triangle centerline endpoint
        double sensorTX = 0;
        double sensorTY = -CAMERA_SENSOR_RANGE;

        AffineTransform tT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorTX, -sensorTY);

        camera_sensor_centerline = new Line2D.Float(DISPLAY_EGOCAR_CENTER_POSITION_X, DISPLAY_EGOCAR_CENTER_POSITION_Y,
            (float) (DISPLAY_EGOCAR_CENTER_POSITION_X + sensorTX + tT.getTranslateX()),
            (float) (DISPLAY_EGOCAR_CENTER_POSITION_Y + sensorTY + tT.getTranslateY()));
    }

    private static void calculateRadarSensorPolygon() {
        // sensor triangle A corner
        double sensorAX = -RADAR_SENSOR_RANGE * Math.tan(RADAR_SENSOR_ANGLE);
        double sensorAY = -RADAR_SENSOR_RANGE;
        // sensor triangle B corner
        double sensorBX = RADAR_SENSOR_RANGE * Math.tan(RADAR_SENSOR_ANGLE);
        double sensorBY = -RADAR_SENSOR_RANGE;

        AffineTransform aT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorAX, -sensorAY);
        AffineTransform bT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorBX, -sensorBY);
        AffineTransform sT =
            AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -RADAR_SENSOR_DX, -RADAR_SENSOR_DY);

        int[] xPoly = {(int) (RADAR_SENSOR_X + sT.getTranslateX()),
            (int) (RADAR_SENSOR_X + sT.getTranslateX() + sensorAX + aT.getTranslateX()),
            (int) (RADAR_SENSOR_X + sT.getTranslateX() + sensorBX + bT.getTranslateX())};

        int[] yPoly = {RADAR_SENSOR_Y + (int) (sT.getTranslateY()),
            (int) (RADAR_SENSOR_Y + +sT.getTranslateY() + sensorAY + aT.getTranslateY()),
            (int) (RADAR_SENSOR_Y + +sT.getTranslateY() + sensorBY + bT.getTranslateY())};
        radar_sensor_polygon = new Polygon(xPoly, yPoly, xPoly.length);
    }

    private static void calculateRadarCenterLine() {
        // sensor triangle centerline endpoint
        double sensorTX = 0;
        double sensorTY = -RADAR_SENSOR_RANGE;
        AffineTransform tT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorTX, -sensorTY);
        AffineTransform sT =
            AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -RADAR_SENSOR_DX, -RADAR_SENSOR_DY);

        radar_sensor_centerline = new Line2D.Float(
            RADAR_SENSOR_X + (int)sT.getTranslateX(),
            RADAR_SENSOR_Y + (int)sT.getTranslateY(),
            (float) (RADAR_SENSOR_X + sensorTX + tT.getTranslateX()),
            (float) (RADAR_SENSOR_Y + sensorTY + tT.getTranslateY()));
    }

    private static void calculateUltraSoundSensorPolygons() {
        ultrasound_sensor_polygons = new ArrayList<Polygon>();
        calculateUltraSoundPolygon(1, 1, false);
        calculateUltraSoundPolygon(1, -1, false);
        calculateUltraSoundPolygon(-1, 1, false);
        calculateUltraSoundPolygon(-1, -1, false);
        calculateUltraSoundPolygon(1, 1, true);
        calculateUltraSoundPolygon(1, -1, true);
        calculateUltraSoundPolygon(-1, 1, true);
        calculateUltraSoundPolygon(-1, -1, true);

    }

    private static void calculateUltraSoundPolygon(int signumX, int signumY, boolean side) {
        int sensorSourceX;
        int sensorSourceY;
        if (side) {
            sensorSourceX = DISPLAY_EGOCAR_CENTER_POSITION_X + signumX * ULTRASOUND_AXIS_DIFF_S2_X;
            sensorSourceY = DISPLAY_EGOCAR_CENTER_POSITION_Y + signumY * ULTRASOUND_AXIS_DIFF_S2_Y;
        } else {
            sensorSourceX = DISPLAY_EGOCAR_CENTER_POSITION_X + signumX * ULTRASOUND_AXIS_DIFF_S1_X;
            sensorSourceY = DISPLAY_EGOCAR_CENTER_POSITION_Y + signumY * ULTRASOUND_AXIS_DIFF_S1_Y;
        }

        ultrasound_sensor_polygons.add(createSensorPolygon(signumX, signumY, side, sensorSourceX, sensorSourceY));
    }

    private static Polygon createSensorPolygon(int signumX, int signumY, boolean side,
                                               int sensorSourceX, int sensorSourceY) {

        Polygon resultPoly = null;
        if (side) {
            resultPoly = createSideSensorPolygon(signumX, sensorSourceX, sensorSourceY);
        } else {
            resultPoly = createForwardBackwardSensorPolygon(signumY, sensorSourceX, sensorSourceY);
        }

        return resultPoly;
    }

    private static Polygon createSideSensorPolygon(int signumX, int sensorSourceX, int sensorSourceY) {
        // sensor triangle A corner
        double sensorAX = signumX * ULTRASOUND_SENSOR_RANGE;
        double sensorAY = -ULTRASOUND_SENSOR_RANGE * Math.tan(ULTRASOUND_SENSOR_ANGLE);
        // sensor triangle B corner
        double sensorBX = signumX * ULTRASOUND_SENSOR_RANGE;
        double sensorBY = ULTRASOUND_SENSOR_RANGE * Math.tan(ULTRASOUND_SENSOR_ANGLE);

        AffineTransform aT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorAX, -sensorAY);
        AffineTransform bT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorBX, -sensorBY);
        AffineTransform sT =
            AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorSourceX, -sensorSourceY);


        int[] xPoly = {(int) (sensorSourceX + sT.getTranslateX()),
            (int) (sensorSourceX + sT.getTranslateX() + sensorAX + aT.getTranslateX()),
            (int) (sensorSourceX + sT.getTranslateX() + sensorBX + bT.getTranslateX())};

        int[] yPoly = {sensorSourceY + (int) (sT.getTranslateY()),
            (int) (sensorSourceY + +sT.getTranslateY() + sensorAY + aT.getTranslateY()),
            (int) (sensorSourceY + +sT.getTranslateY() + sensorBY + bT.getTranslateY())};

        return new Polygon(xPoly, yPoly, xPoly.length);
    }

    private static Polygon createForwardBackwardSensorPolygon(int signumY, int sensorSourceX, int sensorSourceY) {
        // sensor triangle A corner
        double sensorAX =  -ULTRASOUND_SENSOR_RANGE * Math.tan(ULTRASOUND_SENSOR_ANGLE);
        double sensorAY = signumY * ULTRASOUND_SENSOR_RANGE;
        // sensor triangle B corner
        double sensorBX =  ULTRASOUND_SENSOR_RANGE * Math.tan(ULTRASOUND_SENSOR_ANGLE);
        double sensorBY = signumY * ULTRASOUND_SENSOR_RANGE;

        AffineTransform aT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorAX, -sensorAY);
        AffineTransform bT = AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -sensorBX, -sensorBY);
        AffineTransform sT =
            AffineTransform.getRotateInstance(DISPLAY_EGOCAR_ROTATION, -ULTRASOUND_AXIS_DIFF_S1_X, ULTRASOUND_AXIS_DIFF_S1_Y);

        int[] xPoly = {(int) (sensorSourceX + sT.getTranslateX()),
            (int) (sensorSourceX + sT.getTranslateX() + sensorAX + aT.getTranslateX()),
            (int) (sensorSourceX + sT.getTranslateX() + sensorBX + bT.getTranslateX())};

        int[] yPoly = {sensorSourceY + (int) (sT.getTranslateY()),
            (int) (sensorSourceY + +sT.getTranslateY() + sensorAY + aT.getTranslateY()),
            (int) (sensorSourceY + +sT.getTranslateY() + sensorBY + bT.getTranslateY())};

        return new Polygon(xPoly, yPoly, xPoly.length);
    }

    /**
     * Parse the xml file containing the reference points (rotation origos) of the image files
     */
    public static void loadReferencePoints() {
        try {
            // initialize the dictionary
            my_dict = new Hashtable<String, Point2D>();

            File xmlFile = new File(ClassLoader.getSystemResource("reference_points.xml").getFile());
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
        if (my_dict == null || my_dict.isEmpty()) {
            loadReferencePoints();
        }

        Point2D refPoint = null;
        refPoint = my_dict.get(filename);
        if (refPoint == null) {
            refPoint = new Point2D.Float(0, 0);
        }
        return refPoint;
    }
}
