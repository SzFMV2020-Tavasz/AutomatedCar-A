package hu.oe.nik.szfmv.automatedcar.visualization;

/**
 * Class for holding the calculated rotation and displacement data elements
 * needed for visualizing the image of the World elements
 *
 * Basically, a struct.
 */
class DisplayImageData {
    private int rotationDisplacementX;
    private int rotationDisplacementY;
    private int refDifferenceX;
    private int refDifferenceY;
    private int x;
    private int y;
    private float rotation;

    /**
     * Creates a {@link DisplayImageData} instance.
     * @param x                     the x component of the position if the image's reference point
     * @param y                     the y component of the position if the image's reference point
     * @param rotation              The rotation of the image
     * @param refDifferenceX        the x component of the segment line connecting the image's upper left corner
     *                              and the reference point
     * @param refDifferencY         the y component of the segment line connecting the image's upper left corner
     *                              and the reference point
     * @param rotationDisplacementX The x translation needed for fixing the position difference
     *                              caused by the difference between the displayObject's reference point
     *                              and the displayObject's image's rotation origo.
     * @param rotationDisplacementY The y translation needed for fixing the position difference
     *                              caused by the difference between the displayObject's reference point
     *                              and the displayObject's image's rotation origo.
     */
    DisplayImageData(int x, int y, float rotation,
                     int refDifferenceX, int refDifferencY,
                     int rotationDisplacementX, int rotationDisplacementY) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.refDifferenceX = refDifferenceX;
        this.refDifferenceY = refDifferencY;
        this.rotationDisplacementX = rotationDisplacementX;
        this.rotationDisplacementY = rotationDisplacementY;
    }

    /**
     * Gets the position of the image's reference point
     * @return the x component of the position.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the position of the image's reference point
     * @return the y component of the position.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the rotation of the image around it's reference point
     * @return the rotation value
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Gets the x translation needed for fixing the position difference caused by the difference
     * between the displayObject's reference point and the displayObject's image's rotation origo.
     * @return the x component of the translation
     */
    public int getRotationDisplacementX() {
        return rotationDisplacementX;
    }

    /**
     * Gets the y translation needed for fixing the position difference caused by the difference
     * between the displayObject's reference point and the displayObject's image's rotation origo.
     * @return the y component of the translation
     */
    public int getRotationDisplacementY() {
        return rotationDisplacementY;
    }

    /**
     * Gets the x component of the segment line connecting the image's upper left corner
     * and the refrence point
     * @return the distance X
     */
    public int getRefDifferenceX() {
        return refDifferenceX;
    }

    /**
     * Gets the y component of the segment line connecting the image's upper left corner
     * and the refrence point
     * @return the distance Y
     */
    public int getRefDifferenceY() {
        return refDifferenceY;
    }
}
