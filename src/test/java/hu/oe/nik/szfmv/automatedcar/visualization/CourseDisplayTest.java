package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CourseDisplayTest {
    Gui gui ;
    CourseDisplay courseDisplay;
    MockDisplayWorld displayWorld;
    AutomatedCar automatedCar;
    World world;
    BufferedImage bi;
    Graphics2D g2;


    class MockDisplayObject extends DisplayObject {

        public MockDisplayObject() {
            super ( new WorldObject(100, 100, "car_2_red.png"),
                new AutomatedCar(0, 0, "car_2_red.png"));

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
        public List<DisplayObject> getDisplayObjects() {
            List<DisplayObject> returnlist = new ArrayList<DisplayObject>();

            MockDisplayObject mockDisplayObject = new MockDisplayObject();
            mockDisplayObject.setDisplayImageData(0, 0);
            returnlist.add(mockDisplayObject);
            return returnlist;

        }
    }

    @BeforeEach
    public void init() {
        gui = new Gui();
        courseDisplay = new CourseDisplay(gui);
        bi = new BufferedImage(32, 16, BufferedImage.TYPE_INT_RGB);
        g2 = bi.createGraphics();

        automatedCar = new AutomatedCar(0, 0, "car_2_red.png");
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
