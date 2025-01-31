package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReproductionTest {
    @Test
    public void ClassicAnimalReproductionTest() {
        // Given
        ClassicAnimalReproduction repType = new ClassicAnimalReproduction(123);
        Animal animal1 = new Animal(new Vector2d(0, 0), 200, 0, List.of((byte) 0, (byte) 1, (byte) 2, (byte) 3), null);
        Animal animal2 = new Animal(new Vector2d(0, 0), 200, 1, List.of((byte) 0, (byte) 1, (byte) 2, (byte) 3), null);
        // When
        List<ReproductionResult> kids = repType.reproduce(List.of(animal1, animal2), 100);
        // Then
        assertEquals(1, kids.size());
        assertEquals(2, kids.getFirst().parents().size());
        assertEquals(4, kids.getFirst().genome().size());
        assertEquals((byte) 0, kids.getFirst().genome().get(0));
        assertEquals((byte) 1, kids.getFirst().genome().get(1));
        assertEquals((byte) 2, kids.getFirst().genome().get(2));
        assertEquals((byte) 3, kids.getFirst().genome().get(3));
        assertEquals(100, animal1.getEnergy());
        assertEquals(100, animal2.getEnergy());
    }

    @Test
    public void ClassicAnimalReproductionWithStrongerAnimalTest() {
        // Given
        ClassicAnimalReproduction repType = new ClassicAnimalReproduction(123);
        Animal animal1 = new Animal(new Vector2d(0, 0), 450, 0, List.of((byte) 0, (byte) 1, (byte) 2, (byte) 3), null);
        Animal animal2 = new Animal(new Vector2d(0, 0), 150, 1, List.of((byte) 4, (byte) 5, (byte) 6, (byte) 7), null);
        // When
        List<ReproductionResult> kids = repType.reproduce(List.of(animal2, animal1), 100);
        // Then
        assertEquals(1, kids.size());
        assertEquals(2, kids.getFirst().parents().size());
        assertEquals(animal1, kids.getFirst().parents().getFirst());
        assertEquals(animal2, kids.getFirst().parents().getLast());
        assertEquals(4, kids.getFirst().genome().size());
        assertEquals((byte) 4, kids.getFirst().genome().get(0));
        assertEquals((byte) 1, kids.getFirst().genome().get(1));
        assertEquals((byte) 2, kids.getFirst().genome().get(2));
        assertEquals((byte) 3, kids.getFirst().genome().get(3));
        assertEquals(350, animal1.getEnergy());
        assertEquals(50, animal2.getEnergy());
    }

    @Test
    public void ClassicAnimalReproductionWithEpicAnimalTest() {
        // Given
        ClassicAnimalReproduction repType = new ClassicAnimalReproduction(123);
        Animal animal1 = new Animal(new Vector2d(0, 0), 99999999, 0, List.of((byte) 0, (byte) 1, (byte) 2, (byte) 3), null);
        Animal animal2 = new Animal(new Vector2d(0, 0), 150, 1, List.of((byte) 4, (byte) 5, (byte) 6, (byte) 7), null);
        // When
        List<ReproductionResult> kids = repType.reproduce(List.of(animal1, animal2), 100);
        // Then
        assertEquals(1, kids.size());
        assertEquals(2, kids.getFirst().parents().size());
        assertEquals(animal1, kids.getFirst().parents().getFirst());
        assertEquals(animal2, kids.getFirst().parents().getLast());
        assertEquals(4, kids.getFirst().genome().size());
        assertEquals((byte) 4, kids.getFirst().genome().get(0));
        assertEquals((byte) 1, kids.getFirst().genome().get(1));
        assertEquals((byte) 2, kids.getFirst().genome().get(2));
        assertEquals((byte) 3, kids.getFirst().genome().get(3));
        assertEquals(99999899, animal1.getEnergy());
        assertEquals(50, animal2.getEnergy());
    }
}
