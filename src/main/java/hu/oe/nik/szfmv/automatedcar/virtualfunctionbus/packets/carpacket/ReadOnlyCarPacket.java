package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.carpacket;

import java.awt.*;

public interface ReadOnlyCarPacket {

    /**
     * @return the heigth of the car
     */
    int getCarHeigth();

    /**
     * @return the width of the car
     */
    int getCarWidth();

    int getxPosition();

    void setxPosition(int xPosition);

    int getyPosition();

    void setyPosition(int yPosition);

    float getCarRotation();

    void setCarRotation(float rotation);

    Polygon getPolygon();

    void setPolygon(Polygon polygon);
}
