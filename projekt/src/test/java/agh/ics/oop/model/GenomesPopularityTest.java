package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GenomesPopularityTest {
    @Test
    public void popularGenomes(){
        //given
        AnimalGenomesPopularityCalculator listener = new AnimalGenomesPopularityCalculator();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen3 = List.of((byte)3,(byte)2,(byte)1);
        Vector2d v = new Vector2d(2,2);
        String answer = "";
        //when
        Animal animal1 = new Animal(v,10,1,gen1,listener);
        Animal animal2 = new Animal(v,10,1,gen2,listener);
        Animal bin = new Animal(v,10,1,gen2,listener);
        bin = new Animal(v,10,1,gen1,listener);
        Animal animal3 = new Animal(v,10,1,gen3,listener);
        bin = new Animal(v,10,1,gen3,listener);
        bin = new Animal(v,10,1,gen2,listener);
        bin = new Animal(v,10,1,gen1,listener);
        answer = listener.getMostPopularGenome();
        //then
        assertEquals("123",answer);
    }

    @Test
    public void popularSecondGenomes(){
        //given
        AnimalGenomesPopularityCalculator listener = new AnimalGenomesPopularityCalculator();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)3,(byte)2,(byte)1);
        Vector2d v = new Vector2d(2,2);
        Animal animal1 = new Animal(v,10,1,gen1,null);
        Animal animal2 = new Animal(v,10,1,gen2,null);
        String answer = "";
        //when
        listener.newAnimal(animal1);
        listener.newAnimal(animal2);
        listener.newAnimal(animal2);
        listener.newAnimal(animal1);
        listener.newAnimal(animal2);
        listener.newAnimal(animal1);
        answer = listener.getMostPopularGenome();
        //then
        assertEquals("321",answer);
    }

    @Test
    public void popularGenomeWithDelete(){
        //given
        AnimalGenomesPopularityCalculator listener = new AnimalGenomesPopularityCalculator();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen3 = List.of((byte)3,(byte)2,(byte)1);
        Vector2d v = new Vector2d(2,2);
        Animal animal1 = new Animal(v,10,1,gen1,null);
        Animal animal2 = new Animal(v,10,1,gen2,null);
        Animal animal3 = new Animal(v,10,1,gen3,null);
        String answer = "";
        //when
        listener.newAnimal(animal1);
        listener.newAnimal(animal1);
        listener.newAnimal(animal2);
        listener.newAnimal(animal2);
        listener.newAnimal(animal3);
        listener.newAnimal(animal3);
        listener.newAnimal(animal3);
        listener.newAnimal(animal3);
        listener.newAnimal(animal1);
        listener.newAnimal(animal1);
        listener.deleteAnimal(animal2);
        listener.deleteAnimal(animal1);
        listener.deleteAnimal(animal2);
        answer = listener.getMostPopularGenome();
        //then
        assertEquals("321",answer);
    }

    @Test
    public void popularGenomeWithSingleDelete(){
        //given
        AnimalGenomesPopularityCalculator listener = new AnimalGenomesPopularityCalculator();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen3 = List.of((byte)3,(byte)2,(byte)1);
        Vector2d v = new Vector2d(2,2);
        Animal animal1 = new Animal(v,10,1,gen1,null);
        Animal animal2 = new Animal(v,10,1,gen2,null);
        Animal animal3 = new Animal(v,10,1,gen3,null);
        String answer = "";
        //when
        listener.newAnimal(animal1);
        listener.newAnimal(animal1);
        listener.newAnimal(animal1);
        listener.newAnimal(animal1);
        listener.newAnimal(animal3);
        listener.newAnimal(animal3);
        listener.newAnimal(animal3);
        listener.newAnimal(animal3);
        listener.newAnimal(animal1);
        listener.newAnimal(animal1);
        listener.deleteAnimal(animal2);
        answer = listener.getMostPopularGenome();
        //then
        assertEquals("123",answer);
    }

    @Test
    public void singleUsedGenomes(){
        //given
        AnimalGenomesPopularityCalculator listener = new AnimalGenomesPopularityCalculator();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)2,(byte)2,(byte)2);
        List<Byte> gen3 = List.of((byte)3,(byte)2,(byte)1);
        Vector2d v = new Vector2d(2,2);
        Animal animal1 = new Animal(v,10,1,gen1,null);
        Animal animal2 = new Animal(v,10,1,gen2,null);
        Animal animal3 = new Animal(v,10,1,gen3,null);
        String answer = "";
        //when
        listener.newAnimal(animal1);
        listener.newAnimal(animal2);
        listener.newAnimal(animal3);
        listener.deleteAnimal(animal1);
        answer = listener.getMostPopularGenome();
        //then
        assertNotEquals("123",answer);
        assertTrue(Objects.equals(answer, "222") || Objects.equals(answer, "321"));
    }
}
