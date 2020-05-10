package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParkingRadarTest {
    ParkingRadar parkingRadar;
    VirtualFunctionBus virtualFunctionBus;
    MockWorld mockWorld;


    class MockWorld extends World {
        List<WorldObject> passlist;
        MockWorldObject wo1;
        MockWorldObject wo2;
        MockWorldObject wo3;

        ArrayList<Path2D> testPolys;
        Path2D testPoly;

        private MockWorld(int width, int height) {
            super(width, height, new ArrayList<>());
            testPoly = new Path2D.Float();
            testPoly.moveTo(0, 0);
            testPoly.lineTo(1, 0);
            testPoly.lineTo(0, 1);
            testPoly.closePath();
            testPolys = new ArrayList<>();
            testPolys.add(testPoly);

            createWorldObjects(testPolys);
        }

        private void createWorldObjects(ArrayList<Path2D> testPolys) {
            // this is collidable but not in radar range
            wo1 = new MockWorldObject(-400, -25, "tree.png");
            wo1.setType("tree");
            wo1.setId("tree_1");
            wo1.setZ(1);
            wo1.setPolygonsFromOutside(testPolys);
            this.addObjectToWorld(wo1);

            // this is in radar range but not collideable
            wo2 = new MockWorldObject(-200, -25, "parking_space_parallel.png");
            wo2.setType("parking_space_parallel");
            wo2.setId("parking_space_parallel_2");
            wo2.setZ(0);
            wo2.setPolygonsFromOutside(testPolys);
            this.addObjectToWorld(wo2);

            // this is in left radar range and collieadble
            wo3 = new MockWorldObject(-100, -25, "tree.png");
            wo3.setType("tree");
            wo3.setId("tree_3");
            wo3.setZ(1);
            wo3.setPolygonsFromOutside(testPolys);
            this.addObjectToWorld(wo3);
        }

        // this is for testing the update
        private void removeWorldObject(int index) {
            if (this.worldObjects.size() > index && index >= 0) {
                worldObjects.remove(index);
            }
        }
    }

    private static class MockWorldObject extends WorldObject {

        MockWorldObject(int x, int y, String filename) {
            super(x, y, filename);
        }

        /**
         * Create a reachable set method for polygons
         */
        void setPolygonsFromOutside(ArrayList<Path2D> polygons) {
            this.polygons = polygons;
        }
    }


    @BeforeEach
    public void init() {
        AutomatedCar automatedCar = new AutomatedCar(10, 10, "car_2_white.png");
        automatedCar.setRotation((float) Math.toRadians(90));
        virtualFunctionBus = new VirtualFunctionBus();
        mockWorld = new MockWorld(1000, 1000);
        parkingRadar = new ParkingRadar(virtualFunctionBus, automatedCar, mockWorld);
    }

    /**
     * Check whether the class gets instantiated when new {@link ParkingRadar} called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(parkingRadar);
    }

    @Test
    public void testTriangles() {
        parkingRadar.loop();
        // right sensor
        assertEquals(-87, Math.round(virtualFunctionBus.parkingRadarVisualizationPacket.getSources()[0].getX()));
        assertEquals(45, Math.round(virtualFunctionBus.parkingRadarVisualizationPacket.getSources()[0].getY()));
        // left sensor
        assertEquals(-87, Math.round(virtualFunctionBus.parkingRadarVisualizationPacket.getSources()[1].getX()));
        assertEquals(-25, Math.round(virtualFunctionBus.parkingRadarVisualizationPacket.getSources()[1].getY()));
    }

    @Test
    public void testDistanceCalculation() {
        parkingRadar.loop();
        parkingRadar.loop(); // this is for the update check
        assertEquals(Math.round(
            13 / 50.0f * 10) / 10.0f, virtualFunctionBus.leftParkingDistance.getDistance());
        assertEquals(Math.round(
            71 / 50.0f * 10) / 10.0f, virtualFunctionBus.rightParkingDistance.getDistance());
        mockWorld.removeWorldObject(2);
        parkingRadar.loop();
        assertEquals(Math.round(
            Float.MAX_VALUE / 50 * 10) / 10f, virtualFunctionBus.leftParkingDistance.getDistance());
        assertEquals(Math.round(
            Float.MAX_VALUE / 50 * 10) / 10f, virtualFunctionBus.rightParkingDistance.getDistance());
    }
}
