package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.CameraDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.CameraVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.DebugModePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.RadarDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.RadarVisualizationPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.SelectedDebugListPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.UltrasoundDisplayStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization.UltrasoundsVisualizationPacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


public class DisplayWorldTest {

    private DisplayWorld displayWorld;
    private MockWorld mockWorld;


    /**
     * Extend AutomatedCar to control virtualbus data
     */
    class MockAutomatedCar extends AutomatedCar {
        MockAutomatedCar(int x, int y, String imageFileName) {
            super(x, y, imageFileName);
        }

        @Override
        public VirtualFunctionBus getVirtualFunctionBus() {

            VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

            Point2D source = new Point2D.Double(10, 11);
            Point2D corner1 = new Point2D.Double(12, 13);
            Point2D corner2 = new Point2D.Double(14, 15);
            Color color = new Color(50, 51, 57);

            RadarVisualizationPacket radarVisualizationPacket = new RadarVisualizationPacket();
            radarVisualizationPacket.setSensorTriangle(source, corner1, corner2, color);
            virtualFunctionBus.radarVisualizationPacket = radarVisualizationPacket;

            CameraVisualizationPacket cameraVisualisationPacket = new CameraVisualizationPacket();
            cameraVisualisationPacket.setSensorTriangle(source, corner1, corner2, color);
            virtualFunctionBus.cameraVisualizationPacket = cameraVisualisationPacket;

            UltrasoundsVisualizationPacket ultrasoundsVisualizationPacket = new UltrasoundsVisualizationPacket();
            ultrasoundsVisualizationPacket.setSensorTriangle(
                UltrasoundPositions.REAR_LEFT_SIDE, source, corner1, corner2);
            virtualFunctionBus.ultrasoundsVisualizationPacket = ultrasoundsVisualizationPacket;

            debugPacketSets(virtualFunctionBus);
            sensorStateSets(virtualFunctionBus);

            return virtualFunctionBus;
        }

        private void debugPacketSets(VirtualFunctionBus virtualFunctionBus) {
            DebugModePacket debugModePacket = new DebugModePacket();
            debugModePacket.setDebuggingState(true);
            virtualFunctionBus.debugModePacket = debugModePacket;

            SelectedDebugListPacket selectedDebugListPacket = new SelectedDebugListPacket();
            selectedDebugListPacket.addDebugListElement("id1");
            selectedDebugListPacket.addDebugListElement("id2");
            virtualFunctionBus.selectedDebugListPacket = selectedDebugListPacket;
        }

        private void sensorStateSets(VirtualFunctionBus virtualFunctionBus) {
            CameraDisplayStatePacket cameraDisplayStatePacket = new CameraDisplayStatePacket();
            cameraDisplayStatePacket.setCameraDisplayState(true);
            virtualFunctionBus.cameraDisplayStatePacket = cameraDisplayStatePacket;

            RadarDisplayStatePacket radarDisplayStatePacket = new RadarDisplayStatePacket();
            radarDisplayStatePacket.setRadarDisplayState(true);
            virtualFunctionBus.radarDisplayStatePacket = radarDisplayStatePacket;

            UltrasoundDisplayStatePacket ultrasoundDisplayStatePacket =  new UltrasoundDisplayStatePacket();
            ultrasoundDisplayStatePacket.setUltrasoundDisplayState(true);
            virtualFunctionBus.ultrasoundDisplayStatePacket = ultrasoundDisplayStatePacket;
        }
    }

    /**
     * Extend AutomatedCar to control virtualbus data
     */
    class MockNullAutomatedCar extends AutomatedCar {
        MockNullAutomatedCar(int x, int y, String imageFileName) {
            super(x, y, imageFileName);
        }

        @Override
        public VirtualFunctionBus getVirtualFunctionBus() {

            VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();
            return virtualFunctionBus;
        }
    }

    class MockWorld extends World {

        MockWorld() {
            super(100, 100);
        }

        @Override
        public List<WorldObject> getWorldObjects() {
            WorldObject fixWorldObject1 = new WorldObject(10, 20, "boundary.png");
            fixWorldObject1.setZ(5);
            WorldObject fixWorldObject2 = new WorldObject(30, 40, "road_2lane_90right.png");
            fixWorldObject2.setZ(2);
            List<WorldObject> fixObjects = List.of(
                fixWorldObject1,
                fixWorldObject2);
            return fixObjects;
        }

        @Override
        public List<WorldObject> getDynamics() {
            WorldObject dynamicWorldObject1 = new WorldObject(50, 60, "roadsign_priority_stop.png");
            dynamicWorldObject1.setZ(1);
            WorldObject dynamicWorldObject2 = new WorldObject(50, 60, "2_crossroad_1.png");
            dynamicWorldObject2.setZ(4);

            List<WorldObject> dynamicObjects = List.of(
                dynamicWorldObject1,
                dynamicWorldObject2);

            return dynamicObjects;
        }
    }

    @Nested
    @DisplayName("Packets are on the virtualfuncionbus")
    class NotNulls {

        private MockAutomatedCar automatedCar;

        /**
         * Setting up the test
         */
        @BeforeEach
        public void init() {
            mockWorld = new MockWorld();

            automatedCar = new MockAutomatedCar(200, 200, "car_2_red.png");
            automatedCar.setRotation((float) Math.PI / 2);  // No rotation

            displayWorld = new DisplayWorld(mockWorld, automatedCar);
        }


        /**
         * Check whether the class gets instatniatied when new DispLayWorld() called.
         */
        @Test
        public void classInstantiated() {
            assertNotNull(displayWorld);
        }

        @Test
        public void displayObjectsSortedRight() {
            assertEquals("roadsign_priority_stop.png", displayWorld.getDisplayObjects().get(0).getImageFileName());
            assertEquals("road_2lane_90right.png", displayWorld.getDisplayObjects().get(1).getImageFileName());
            assertEquals("2_crossroad_1.png", displayWorld.getDisplayObjects().get(2).getImageFileName());
            assertEquals("boundary.png", displayWorld.getDisplayObjects().get(3).getImageFileName());
        }

        @Test
        public void debugModeSet() {
            assertEquals(true, displayWorld.isDebugOn());
        }

        @Test
        public void cameraSensorDisplayOn() {
            assertEquals(true, displayWorld.isCameraShown());
        }

        @Test
        public void radarSensorDisplayOn() {
            assertEquals(true, displayWorld.isRadarShown());
        }

        @Test
        public void ultrasSoundSensorDisplayOn() {
            assertEquals(true, displayWorld.isUltrasoundShown());
        }

        @Test
        public void showEgoCar() {
            DisplayImageData egocarDisplayImageData = displayWorld.getEgoCar().getDisplayImageData();
            assertEquals(385, egocarDisplayImageData.getX());
            assertEquals(350, egocarDisplayImageData.getY());
            assertEquals(0, egocarDisplayImageData.getRotation());
        }

        @Test
        public void radarPacket() {
            DisplaySensorObject dso = displayWorld.getDisplayRadar();
            assertEquals(10, dso.source.getX());
        }

        @Test
        public void cameraPacket() {
            DisplaySensorObject dso = displayWorld.getDisplayCamera();
            assertEquals(10, dso.source.getX());
        }

        @Test
        public void ultrasoundPacket() {
            DisplaySensorObject[] dso = displayWorld.getDisplayUltrasounds();
            assertEquals(10, dso[3].source.getX());
        }

        @Test void checkDebugListPacket() {
            List<String> list = displayWorld.getDebugObjects();
            assertEquals("id1", list.get(0));
            assertEquals("id2", list.get(1));
        }
    }

    @Nested
    @DisplayName("Packets are not on the virtualfuncionbus")
    class AreNulls {

        private MockNullAutomatedCar automatedCar;

        /**
         * Setting up the test
         */
        @BeforeEach
        public void init() {
            mockWorld = new MockWorld();

            automatedCar = new MockNullAutomatedCar(200, 200, "car_2_red.png");
            automatedCar.setRotation((float) Math.PI / 2);  // No rotation

            displayWorld = new DisplayWorld(mockWorld, automatedCar);
        }

        @Test
        public void nullRadarPacket() {
            DisplaySensorObject dso = displayWorld.getDisplayRadar();
            assertNull(dso);
        }

        @Test
        public void nullCameraPacket() {
            DisplaySensorObject dso = displayWorld.getDisplayCamera();
            assertNull(dso);
        }

        @Test
        public void nullUltrasoundPacket() {
            DisplaySensorObject[] dso = displayWorld.getDisplayUltrasounds();
            assertNull(dso);
        }
    }
}
