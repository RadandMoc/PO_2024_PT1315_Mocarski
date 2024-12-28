package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomPositionGeneratorTests {
    @Test
    public void GeneratingVectorsTest(){
        // Given
        RandomPositionGenerator generator = new RandomPositionGenerator(0,0,4,4,5);
        List<Vector2d> res = new ArrayList<>();
        // When
        for(Vector2d item : generator)
            res.add(item);
        // Then
        assertEquals(5,res.size());
    }

    @Test
    public void GeneratingVectorsWithDeleteTest(){
        // Given
        RandomPositionGenerator generator = new RandomPositionGenerator(0,0,4,4,25);
        List<Vector2d> res = new ArrayList<>();
        // When
        for(Vector2d item : generator)
            res.add(item);
        generator.acceptPositionToChoice(res.getFirst());
        generator.setHowManyGenerate(1);
        for(Vector2d item : generator)
            res.add(item);
        // Then
        assertEquals(26,res.size());
    }


}
