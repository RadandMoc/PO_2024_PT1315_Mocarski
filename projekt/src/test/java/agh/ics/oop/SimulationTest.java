package agh.ics.oop;

import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.lang.ref.SoftReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {

    @Test
    public void equalsForSameVectors() {

        // Given
        Vector2d vec = new Vector2d(243, -271);
        Vector2d copy;
        // When
        copy = vec;
        // Then
        assertEquals(vec, copy);


    }
}
