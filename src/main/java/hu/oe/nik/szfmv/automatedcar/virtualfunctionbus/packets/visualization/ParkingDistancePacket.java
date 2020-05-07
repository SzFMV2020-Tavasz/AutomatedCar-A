package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public class ParkingDistancePacket implements IParkingDistancePacket {
    private float parkingDistance;

    /**
     * Packet for sending the parking distance mesured by the parking radar to the dashboard
     */
    public ParkingDistancePacket() {

    }

    /**
     * Gets the parking distance
     */
    public float getDistance() {
        return parkingDistance;
    }

    /**
     * Sets the parking distance
     */
    public void setDistance(float distance) {
        parkingDistance = distance;
    }
}
