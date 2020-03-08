package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DisplayWorldTest {

    private DisplayWorld displayWorld;
    private World world;
    private AutomatedCar automatedCar;

    /**
     * Setting up the test
     */
    @BeforeEach
    public void init() {

        WorldObject fixWorldObject1 = new WorldObject(10, 20, "boundary.png");
        fixWorldObject1.setZ(5);
        WorldObject fixWorldObject2 = new WorldObject(30, 40, "road_2lane_90right.png");
        fixWorldObject2.setZ(2);
        WorldObject dynamicWorldObject1 = new WorldObject(50,60, "roadsign_priority_stop.png");
        dynamicWorldObject1.setZ(1);
        WorldObject dynamicWorldObject2 = new WorldObject(50,60, "2_crossroad_1.png");
        dynamicWorldObject2.setZ(4);
        List<WorldObject> fixObjects = List.of (
                fixWorldObject1,
                fixWorldObject2); ;
        List<WorldObject>  dynamicObjects = List.of(
                dynamicWorldObject1,
                dynamicWorldObject2);

        automatedCar = new AutomatedCar(200, 200, "car_2_red.png");
        automatedCar.setRotation((float)Math.PI /2);  // No rotation

        world = mock(World.class);
        when(world.getWorldObjects()).thenReturn(fixObjects);
        when(world.getDynamics()).thenReturn(dynamicObjects);

        displayWorld = new DisplayWorld(world, automatedCar);

    }


    /**
     * Check whether the class gets instatniatied when new DispLayWorld() called.
     */
    @Test
    public void classInstantiated() {
        assertNotNull(displayWorld);
    }

    @Test
    public void displayObjectsSortedRight()
    {
        assertEquals("roadsign_priority_stop.png", displayWorld.getDisplayObjects().get(0).getImageFileName());
        assertEquals("road_2lane_90right.png", displayWorld.getDisplayObjects().get(1).getImageFileName());
        assertEquals("2_crossroad_1.png", displayWorld.getDisplayObjects().get(2).getImageFileName());
        assertEquals("boundary.png", displayWorld.getDisplayObjects().get(3).getImageFileName());
    }
}
