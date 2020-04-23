package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.CarPositionPacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class RadarTest {
    Radar radar;
    AutomatedCar automatedCar;
    VirtualFunctionBus virtualFunctionBus;
    MockWorld world;

    /**
     * Mock class for controling the passed on data
     */
    class MockWorld extends World {
        int calledNumber = 0;
        List<WorldObject> passlist;
        MockWorldObject object1;
        MockWorldObject object3;
        Polygon testPoly = new Polygon(new int[]{0, 1, 0}, new int[]{0, 0, 1}, 3);

        MockWorld(int width, int height) {
            super(width, height, new ArrayList<WorldObject>());
            passlist = new ArrayList<>();
            object1 = new MockWorldObject(-100, -100, "roadsign_speed_40.png");
            object1.setId("roadsign_speed_40_1");
            object1.setZ(1);
            object1.setPolygon(testPoly);
            object3 = new MockWorldObject(600, 600, "tree.png");
            object3.setId("tree_3");
            object3.setZ(1);
            object3.setPolygon(testPoly);
        }

        public List<WorldObject> getPasslist() {
            return passlist;
        }

        @Override
        public List<WorldObject> getObjectsInsideTriangle(Point a, Point b, Point c) {
            calledNumber++;
            passlist = new ArrayList<>();
            if (calledNumber < 5) {

                // the collideable object
                // Made as class global so it could change values
                passlist.add((WorldObject) object1);

                // the not collideable object
                MockWorldObject object2 = new MockWorldObject(200, 400, "parking_space_parallel.png");
                object2.setId("parking_space_parallel_2");
                object2.setZ(0);
                object2.setPolygon(testPoly);
                passlist.add((WorldObject) object2);

                if (calledNumber == 1 || calledNumber == 3) {
                    // a collideable object
                    passlist.add((WorldObject) object3);
                }
            }
            return passlist;
        }

    }

    private class MockWorldObject extends WorldObject {
        int calledNumberX;
        int calledNumberY;

        MockWorldObject(int x, int y, String filename) {
            super(x, y, filename);
            calledNumberX = 0;
            calledNumberY = 0;
        }

        // add method for setting polygon for testing
        void setPolygon(Polygon poly) {
            this.polygon = poly;
        }

        /**
         * override for creating different movement vectors
         */
        @Override
        public int getX() {
            calledNumberX++;
            int tempX;
            switch (calledNumberX) {
                case 2:
                    tempX = super.getX() + 40;
                    break;
                case 3:
                    tempX = super.getX() + 40;
                    break;
                default:
                    tempX = super.getX();
                    break;
            }
            return tempX;
        }

        /**
         * override for creating different movement vectors
         */
        @Override
        public int getY() {
            calledNumberY++;
            int tempY;
            switch (calledNumberY) {
                case 2:
                    tempY = super.getY() + 25;
                    break;
                case 3:
                    tempY = super.getY() - 35;
                    break;
                default:
                    tempY = super.getY();
                    break;
            }
            return tempY;
        }
    }

    /**
     * implementing interfaces for testing
     */
    private class MockCarPostionPacketData implements CarPositionPacket {
        int calledNumber = 0;

        MockCarPostionPacketData() {
        }

        @Override
        public double getX() {
            return 10;
        }

        @Override
        public double getY() {
            return 10;
        }

        @Override
        public IVector getMoveVector() {
            return new MockVector(10, -5);
        }
    }

    private class MockVector implements IVector {
        int xDiff;
        int yDiff;

        MockVector(int xDiff, int yDiff) {
            this.xDiff = xDiff;
            this.yDiff = yDiff;
        }

        @Override
        public double getXDiff() {
            return xDiff;
        }

        @Override
        public double getYDiff() {
            return yDiff;
        }

        @Override
        public double getLength() {
            return 0;
        }

        @Override
        public double getRadiansRelativeTo(IVector direction) {
            return 0;
        }

    }

    @BeforeEach
    public void init() {
        virtualFunctionBus = new VirtualFunctionBus();
        automatedCar = new AutomatedCar(10, 10, "car_1_white.png");
        automatedCar.setRotation((float) Math.toRadians(-30));
        world = new MockWorld(1000, 1000);
        radar = new Radar(virtualFunctionBus, automatedCar, world);
        MockCarPostionPacketData carPostionPacketData = new MockCarPostionPacketData();
        virtualFunctionBus.carPositionPacket = carPostionPacketData;
    }

    /**
     * Check whether the class gets instantiatied when new Radar() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(radar);
    }

    /**
     * Check whether collidable objects are sent to the selectedDebugListPacket
     */
    @Test
    public void collidableObjects() {
        radar.loop();
        assertTrue(radar.getObjectsSeenByRadar().stream()
            .filter(t -> t.getId().equals("roadsign_speed_40_1")).findFirst().isPresent());
        assertFalse(radar.getObjectsSeenByRadar().stream()
            .filter(t -> t.getId().equals("parking_2")).findFirst().isPresent());
    }

    /**
     * Checks whether object is removed from the selectedDebugListPacket if no longer seen by radar
     */
    @Test
    public void collidableRemoved() {
        radar.loop();
        assertTrue(radar.getObjectsSeenByRadar().stream()
            .filter(t -> t.getId().equals("tree_3")).findFirst().isPresent());
        radar.loop();
        assertFalse(radar.getObjectsSeenByRadar().stream()
            .filter(t -> t.getId().equals("tree_3")).findFirst().isPresent());
    }

    /**
     * Checks whether the radar triangle position is right
     * and it is set to the right
     */
    @Test
    public void radarTriangleSet() {
        radar.loop();
        assertEquals(-42, virtualFunctionBus.radarVisualizationPacket.getSensorSource().getX());
        assertEquals(-80, virtualFunctionBus.radarVisualizationPacket.getSensorSource().getY());
        assertEquals(61, virtualFunctionBus.radarVisualizationPacket.getSensorCorner1().getX());
        assertEquals(-11446, virtualFunctionBus.radarVisualizationPacket.getSensorCorner1().getY());
        assertEquals(-9937, virtualFunctionBus.radarVisualizationPacket.getSensorCorner2().getX());
        assertEquals(-5673, virtualFunctionBus.radarVisualizationPacket.getSensorCorner2().getY());
    }

    /**
     * Checks whether the nearest collideable object is the right one
     */
    @Test
    public void nearestCollidableElement() {
        radar.loop();
        assertTrue(radar.getNearestCollideableElement().getId().equals("roadsign_speed_40_1"));
    }

    /**
     * Checks whether the list of elements seen by radar is updated right
     */
    @Test
    public void updateSeenByRadar() {
        radar.loop();
        radar.loop();
        radar.loop();
        assertTrue(radar.getObjectsSeenByRadar().stream()
            .filter(t -> t.getId().equals("tree_3")).findFirst().isPresent());
    }

    /**
     * Checks whether the nearest object is highlihgted in the World class instance
     */
    @Test
    public void nearestObjectHighlighted() {
        radar.loop();
        assertTrue(world.getPasslist().get(0).isHighlightedWhenRadarIsOn());
    }

    /**
     * Checks whether the highlighted elements of the world instance are cleared on every loop
     */
    @Test
    public void highlightedElementsCleared() {
        radar.loop();
        radar.loop();
        radar.loop();
        radar.loop();
        radar.loop();
        MovingWorldObject obj = radar.getNearestCollideableElement();
        assertFalse(world.getWorldObjects().stream()
            .filter(t -> t.isHighlightedWhenRadarIsOn() == true).findAny().isPresent());
        assertFalse(world.getDynamics().stream()
            .filter(t -> t.isHighlightedWhenRadarIsOn() == true).findAny().isPresent());
    }

    /**
     * Checks whether relative movement vector intersection is calulated right
     */
    @Test
    public void intersectsWithAutoCarPolygon() {
        // creating the needed movement vector
        radar.loop();
        radar.loop();
        List<WorldObject> list = radar.getRelevantObjectsForAEB();
        assertTrue(list.stream().filter(t -> t.getId().equals("roadsign_speed_40_1")).findAny().isPresent());
        radar.loop();
        list = radar.getRelevantObjectsForAEB();
        assertEquals(0, list.size());
    }
}
