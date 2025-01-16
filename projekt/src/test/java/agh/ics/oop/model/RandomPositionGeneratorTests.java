package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RandomPositionGeneratorTests {
    @Test
    public void GeneratingVectorsTest(){
        // Given
        RandomPositionGenerator generator = null;
        try{
            generator = new RandomPositionGenerator(0,0,4,4,5);
        }
        catch (ToMuchValuesToGenerateException e){
            fail();
        }
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
        RandomPositionGenerator generator = null;
        try{
            generator = new RandomPositionGenerator(0,0,4,4,25);
        }
        catch (ToMuchValuesToGenerateException e){
            fail();
        }
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
        assertEquals(res.getFirst(),res.getLast());
    }

    @Test
    public void GeneratingMoreVectorsThanPossibleTest(){
        // Given
        RandomPositionGenerator generator = null;
        int howManyLess = 0;
        boolean throwException = false;
        //when
        try{
            generator = new RandomPositionGenerator(0,0,4,4,26);
        }
        catch (ToMuchValuesToGenerateException e){
            throwException = true;
            howManyLess = e.getErrorValue();
        }
        //then
        assertTrue(throwException);
        assertEquals(1,howManyLess);
    }

    @Test
    public void GeneratingMoreVectorsThanPossibleSecondTimeTest(){
        // Given
        RandomPositionGenerator generator = null;
        int howManyLess = 0;
        boolean throwExceptionInCreating = false;
        boolean throwExceptionInRun = false;
        List<Vector2d> res = new ArrayList<>();
        Set<Vector2d> uniq;
        //when
        try{
            generator = new RandomPositionGenerator(0,0,4,4,15);
        }
        catch (ToMuchValuesToGenerateException e){
            throwExceptionInCreating = true;
            howManyLess = e.getErrorValue();
        }
        for (int i = 0; i < 2; i++) {
            try{
                for(Vector2d item:generator){
                    res.add(item);
                }
            } catch (ToMuchValuesToGenerateException e){
                throwExceptionInRun = true;
                howManyLess = e.getErrorValue();
            }
        }
        uniq = new HashSet<>(res);
        //then
        assertFalse(throwExceptionInCreating);
        assertTrue(throwExceptionInRun);
        assertEquals(5,howManyLess);
        assertEquals(25,res.size());
        assertEquals(uniq.size(),res.size());
    }

    @Test
    public void GenerateVectorsAtOnlyPoles(){
        // Given
        RandomPositionGenerator generator = null;
        try{
            generator = new RandomPositionGenerator(0,0,4,4,25);
        }
        catch (ToMuchValuesToGenerateException e){
            fail();
        }
        List<Vector2d> res = new ArrayList<>();
        int howManyLess=0;
        // When
        generator.deleteRectangle(new Boundary(new Vector2d(2,0), new Vector2d(2,4)));
        try{
            for(Vector2d item : generator)
                res.add(item);
        } catch (ToMuchValuesToGenerateException e){
            howManyLess = e.getErrorValue();
        }
        // Then
        assertEquals(5,howManyLess);
        assertEquals(20,res.size());
    }
}
