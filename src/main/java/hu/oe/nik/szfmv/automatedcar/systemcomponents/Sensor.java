package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import java.awt.*;

public class Sensor {
    private Point aCorner;
    private Point bCorner;
    private Point cCorner;

    public Point[] getCorners() {
        return new Point[]{aCorner, bCorner, cCorner};
    }
}
