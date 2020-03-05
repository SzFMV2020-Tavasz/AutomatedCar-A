package hu.oe.nik.szfmv.automatedcar.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {

    @Test
    void failing_test() {
        System.out.println("This test shall deliberately fail!");
        assertEquals(42, 176240);
    }
}
