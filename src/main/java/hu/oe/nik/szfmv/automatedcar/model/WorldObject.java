package hu.oe.nik.szfmv.automatedcar.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    protected Polygon polygon;
    protected boolean isStatic;

    public WorldObject(int x, int y, String imageFileName) {
        this.x = x;
        this.y = y;
        this.imageFileName = imageFileName;
        initImage();
    }

    public WorldObject(String id) {
        setId(id);
        this.rotationMatrix = new float[2][2];
    }

    public WorldObject() {

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
        if (id == null)
            setId(type + "_" + (uid++));
        setImageFileName(this.type + ".png");
        initImage();
    }
}
