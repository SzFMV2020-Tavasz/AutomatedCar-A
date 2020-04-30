package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.CarVariant;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CourseDisplayTest {
    MockCourseDisplay courseDisplay;
    MockDisplayWorld displayWorld;
    AutomatedCar automatedCar;
    World world;
    BufferedImage bi;
    Graphics2D g2;

    class MockCourseDisplay extends CourseDisplay {
        MockCourseDisplay() {
            super(null);
        }
    }

    class MockDisplayObject extends DisplayObject {

        public MockDisplayObject() {
            super (new WorldObject(100, 100, "car_2_red.png"),
                new AutomatedCar(0, 0, CarVariant.TYPE_2_RED));

        }

        void setDisplayImageData(int x, int y) {
            displayImageData = new DisplayImageData(x, y, 0,
                0, 0, 0, 0);
        }

        @Override
        public void initImage() {
        }

        @Override
        public BufferedImage  getImage() {
            BufferedImage bi = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            int red = 0xFFFF0000;
            bi.setRGB(2, 2, red);
            return bi;
        }
    }

    class MockDisplayWorld extends DisplayWorld {
        MockDisplayWorld(World world, AutomatedCar automatedCar) {
            super (world, automatedCar);
        }

        @Override
        public ArrayList<DisplayObject> getDisplayObjects() {
            ArrayList<DisplayObject> returnList = new ArrayList<>();

            MockDisplayObject mockDisplayObject = new MockDisplayObject();
            mockDisplayObject.setDisplayImageData(0, 0);
            returnList.add(mockDisplayObject);
            return returnList;

        }
    }

    @BeforeEach
    public void init() {

        courseDisplay = new MockCourseDisplay();
        bi = new BufferedImage(32, 16, BufferedImage.TYPE_INT_RGB);
        g2 = bi.createGraphics();

        automatedCar = new AutomatedCar(0, 0, CarVariant.TYPE_2_RED);
        world = new World(100, 100);
        displayWorld = new MockDisplayWorld(world, automatedCar);

    }

    /**
     * Check whether the class gets instatniatied when new DispLayWorld() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(courseDisplay);
    }

    @Test
    public void objectsDrawn() {
        courseDisplay.drawObjects(g2, displayWorld);
        assertEquals(255, new Color(bi.getRGB(2, 2)).getRed());
    }

}
