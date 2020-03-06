package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
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
    public static final double DISPLAY_EGOCAR_ROTATION = Math.PI / 2;

    // Needed for testing; will be removed later with actual passed/read data
    public static final int DISPLAY_EGOCAR_REFERENCE_X = 104;
    public static final int DISPLAY_EGOCAR_REFERENCE_Y = 51;
    public static final int DISPLAY_EGOCAR_ROTATION_ORIGO_X =
            DISPLAY_EGOCAR_CENTER_POSITION_X + DISPLAY_EGOCAR_REFERENCE_X;
    public static final int DISPLAY_EGOCAR_ROTATION_ORIGO_Y =
            DISPLAY_EGOCAR_CENTER_POSITION_Y - DISPLAY_EGOCAR_REFERENCE_Y;
    public static final double DISPLAY_EGOCAR_ROTATION_OF_IMG = Math.PI / 2;

    public static int DISPLAY_RIGHT90_REF_POINT_X = 349;
    public static int DISPLAY_RIGHT90_REF_POINT_Y = 525;

    private static Hashtable<String, Point2D> my_dict;

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    /**
     * Constructor for static class
     * <p>
     * it does not need to be instantiated.
     */
    private VisualizationConfig() {
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

            NodeList imageNodes =  xmlDoc.getElementsByTagName("Image");
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
      * @param filename The name of the image
     * @return The reference point of the image. The type is float, my needs to be casted to integer.
     */
    public static Point2D getReferencePoint(String filename) {
        if (my_dict == null || my_dict.isEmpty())
        {
            loadReferencePoints();
        }

        Point2D refPoint = null;
        refPoint = my_dict.get(filename);
        return refPoint;
    }
}
