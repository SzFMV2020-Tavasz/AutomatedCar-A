package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.deserializer.ReadPolygons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class WorldObject {

    private static int uid = 1;

    private static final Logger LOGGER = LogManager.getLogger(WorldObject.class);

    protected String id;
    protected String type;
    protected int x;
    protected int y;
    protected int z;
    protected int width;
    protected int height;
    protected float rotation = 0f;
    protected float[][] rotationMatrix;
    protected String imageFileName;
    protected BufferedImage image;
    protected ArrayList<Path2D> polygons;
    protected boolean isStatic;
    protected boolean highlightedWhenCameraIsOn;
    protected boolean highlightedWhenRadarIsOn;
    protected boolean highlightedWhenUltrasoundIsOn;
    protected boolean highlightedWhenParkinRadarIsOn;

    public WorldObject(int x, int y, String imageFileName) {
        this.x = x;
        this.y = y;
        this.imageFileName = imageFileName;
        initImage();
        this.highlightedWhenCameraIsOn = false;
        this.highlightedWhenRadarIsOn = false;
        this.highlightedWhenUltrasoundIsOn = false;

        this.polygons = new ArrayList<>();
        initPolygons();
    }

    public WorldObject(String id) {
        setId(id);
        this.rotationMatrix = new float[2][2];
        this.polygons = new ArrayList<>();
        initPolygons();
    }

    public WorldObject() {
        this.polygons = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float[][] getRotationMatrix() {
        return rotationMatrix;
    }

    public void setRotationMatrix(float[][] matrix) {
        this.rotationMatrix = matrix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getRotation() {
        return rotation;
    }

    public final void setRotation(double rotation) {
        this.rotation = (float) rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public boolean getIsStatic() {
        return isStatic;
    }

    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Gets the list of debug polygons for the object
     * If the list consists of more than one element, the object is a road element.
     * Although the elements are called polygons, tehcnically they are Path2D objects
     * as jawa.awt.geom.Polygons cannot be open shapes while Path2D elements can be open or closed.
     *
     * @return The list of polygons as {@link Path2D} objects
     */
    public ArrayList<Path2D> getPolygons() {
        return polygons;
    }

    /**
     * Sets the list of polygon for the object.
     *
     * @param polygons The List of Path2d objects that represent the debug polygons of the object.
     */
    void setPolygons(ArrayList<Path2D> polygons) {
        this.polygons = polygons;
    }

    public void initImage() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            image = ImageIO.read(classLoader.getResourceAsStream(imageFileName));
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void initObject() {
        if (id == null) {
            setId(type + "_" + (uid++));
        }
        setImageFileName(this.type + ".png");
        initImage();
    }

    /**
     * Gets whether the object is highlighted when radar sensor triangle is shown
     *
     * @return true if it is highlighted; false otherwise
     */
    public boolean isHighlightedWhenRadarIsOn() {
        return highlightedWhenRadarIsOn;
    }

    /**
     * Sets whether the object is highlighted when radar sensor triangle is shown
     *
     * @param highlightedWhenRadarIsOn true if the object should be hightlighted; false otherwise
     */
    public void setHighlightedWhenRadarIsOn(boolean highlightedWhenRadarIsOn) {
        this.highlightedWhenRadarIsOn = highlightedWhenRadarIsOn;
    }

    /**
     * Gets whether the object is highlighted when camera sensor triangle is shown
     *
     * @return true if it is highlighted; false otherwise
     */
    public boolean isHighlightedWhenCameraIsOn() {
        return highlightedWhenCameraIsOn;
    }

    /**
     * Sets whether the object is highlighted when camera sensor triangle is shown
     *
     * @param highlightedWhenCameraIsOn true if the object should be hightlighted; false otherwise
     */
    public void setHighlightedWhenCameraIsOn(boolean highlightedWhenCameraIsOn) {
        this.highlightedWhenCameraIsOn = highlightedWhenCameraIsOn;
    }

    /**
     * Gets whether the object is highlighted when ultrasound sensor triangle is shown
     *
     * @return true if it is highlighted; false otherwise
     */
    public boolean isHighlightedWhenUltrasoundIsOn() {
        return highlightedWhenUltrasoundIsOn;
    }

    /**
     * Sets whether the object is highlighted when ultrasound sensor triangle is shown
     *
     * @param highlightedWhenUltrasoundIsOn true if the object should be hightlighted; false otherwise
     */
    public void setHighlightedWhenUltrasoundIsOn(boolean highlightedWhenUltrasoundIsOn) {
        this.highlightedWhenUltrasoundIsOn = highlightedWhenUltrasoundIsOn;
    }

    /**
     * Gets whether the object is highlighted when ultrasound sensor triangle is shown
     *
     * @return true if it is highlighted; false otherwise
     */
    public boolean isHighlightedWhenParkinRadarIsOn() {
        return highlightedWhenParkinRadarIsOn;
    }

    /**
     * Sets whether the object is highlighted when ultrasound sensor triangle is shown
     *
     * @param highlightedWhenParkinRadarIsOn true if the object should be hightlighted; false otherwise
     */
    public void setHighlightedWhenParkinRadarIsOn(boolean highlightedWhenParkinRadarIsOn) {
        this.highlightedWhenParkinRadarIsOn = highlightedWhenParkinRadarIsOn;
    }

    public void initPolygons() {
        if (this.getType() == null && imageFileName != null) {
            this.type = this.imageFileName.split(".png")[0];
        }
        if (this.getType() != null) {
            ArrayList<Path2D> polygons = ReadPolygons.getPolygonsForObject(this.getType());
            if (polygons != null) {
                this.setPolygons(polygons);
            }
        }
    }
}
