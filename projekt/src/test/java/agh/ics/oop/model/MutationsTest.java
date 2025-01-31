package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MutationsTest {
    @Test
    public void swapMutateTest() {
        // Given
        SwapMutate mutate = new SwapMutate(1, 1, 100);
        List<Byte> original = List.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7);
        List<Byte> afterChange;
        int notChangedValues = 0;
        // When
        afterChange = mutate.mutate(original);
        for (int i = 0; i < 7; i++) {
            if (Objects.equals(original.get(i), afterChange.get(i)))
                notChangedValues++;
            if (afterChange.get(i) == 0)
                fail();
        }
        // Then
        assertEquals(5, notChangedValues);
        assertTrue(afterChange.containsAll(original));
        assertTrue(original.containsAll(afterChange));
    }

    @Test
    public void manySwapMutateTest() {
        // Given
        SwapMutate mutate = new SwapMutate(10, 100);
        List<Byte> original = List.of((byte) 1, (byte) 2, (byte) 4, (byte) 6, (byte) 1, (byte) 2, (byte) 4, (byte) 6);
        List<Byte> afterChange;
        // When
        afterChange = mutate.mutate(original);
        for (int i = 0; i < 8; i++) {
            if (afterChange.get(i) == 0 || afterChange.get(i) == 3 || afterChange.get(i) == 5 || afterChange.get(i) == 7)
                fail();
        }
        // Then
        assertTrue(afterChange.containsAll(original));
        assertTrue(original.containsAll(afterChange));
    }

    @Test
    public void randomMutateTest() {
        // Given
        RandomMutate mutate = new RandomMutate(1, 1, 100);
        List<Byte> original = List.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7);
        List<Byte> afterChange;
        int notChangedValues = 0;
        // When
        afterChange = mutate.mutate(original);
        for (int i = 0; i < original.size(); i++) {
            if (afterChange.get(i) > 7 || afterChange.get(i) < 0)
                fail();
            if (Objects.equals(original.get(i), afterChange.get(i)))
                notChangedValues++;
        }
        // Then
        assertEquals(6, notChangedValues);
        assertEquals(7, afterChange.size());
        assertNotEquals(original, afterChange);
    }

    @Test
    public void manyRandomMutateTest() {
        // Given
        RandomMutate mutate = new RandomMutate(110, 200);
        List<Byte> original = new ArrayList<>();
        Random ran = new Random();
        for (int i = 0; i < 200; i++) {
            original.add((byte) ran.nextInt(8));
        }
        List<Byte> afterChange;
        // When
        afterChange = mutate.mutate(original);
        for (int i = 0; i < original.size(); i++) {
            if (afterChange.get(i) > 7 || afterChange.get(i) < 0)
                fail();
        }
        // Then
        assertEquals(200, afterChange.size());
    }
}
