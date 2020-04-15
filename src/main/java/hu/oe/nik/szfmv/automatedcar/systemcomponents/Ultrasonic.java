package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import java.util.List;

public class Ultrasonic {

    private World world;
    private List<Sensor> sensors;
    private List<WorldObject> worldObjectInSensorRange;

    public void setWorld(World world) {
        this.world = world;
    }

    public void addSensor(Sensor newSensor) {
        this.sensors.add(newSensor);
    }

    private void getWorldObjectInSensorRange() {
        for (Sensor sensor : sensors) {
            worldObjectInSensorRange.addAll(world.getObjectsInsideTriangle(sensor.getCorners()[0], sensor.getCorners()[1],
                    sensor.getCorners()[2]));
        }
    }
}
