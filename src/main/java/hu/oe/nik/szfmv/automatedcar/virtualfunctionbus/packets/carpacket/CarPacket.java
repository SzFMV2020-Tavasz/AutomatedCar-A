package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.carpacket;


import java.awt.*;

public class CarPacket implements ReadOnlyCarPacket {

    private int carHeigth;
    private int carWidth;
    private int xPosition;
    private int yPosition;
    private float carRotation;
    private Polygon polygon;

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public float getCarRotation() {
        return carRotation;
    }

    public void setCarRotation(float carRotation) {
        this.carRotation = carRotation;
    }

    @Override
    public Polygon getPolygon() {
        return polygon;
    }

    @Override
    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }


    @Override
    public int getCarHeigth() {
        return carHeigth;
    }

    public void setCarHeigth(int carHeigth) {
        this.carHeigth = carHeigth;
    }

    @Override
    public int getCarWidth() {
        return carWidth;
    }

    public void setCarWidth(int carWidth) {
        this.carWidth = carWidth;
    }
}
