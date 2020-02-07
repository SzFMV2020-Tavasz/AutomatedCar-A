package hu.oe.nik.szfmv.automatedcar.model;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldObjectTest {

    WorldObject worldObject;

    @Before
    void init() {
        worldObject = new WorldObject(100, 100, "car_2_white.png");
    }

    @Test
    void addition() {
        assertEquals(100, worldObject.getX());
    }

}
