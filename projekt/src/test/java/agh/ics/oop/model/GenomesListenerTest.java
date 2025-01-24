package agh.ics.oop.model;

import agh.ics.oop.statistic.AnimalGenomesStatistic;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GenomesListenerTest {
    @Test
    public void popularGenomes(){
        //given
        AnimalGenomesStatistic listener = new AnimalGenomesStatistic();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen3 = List.of((byte)3,(byte)2,(byte)1);
        String answer = "";
        //when
        listener.newGenome(gen1);
        listener.newGenome(gen2);
        listener.newGenome(gen2);
        listener.newGenome(gen1);
        listener.newGenome(gen3);
        listener.newGenome(gen3);
        listener.newGenome(gen2);
        listener.newGenome(gen1);
        answer = listener.theMostPopularGenome();
        //then
        assertEquals("123",answer);
    }

    @Test
    public void popularSecondGenomes(){
        //given
        AnimalGenomesStatistic listener = new AnimalGenomesStatistic();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)3,(byte)2,(byte)1);
        String answer = "";
        //when
        listener.newGenome(gen1);
        listener.newGenome(gen2);
        listener.newGenome(gen2);
        listener.newGenome(gen1);
        listener.newGenome(gen2);
        listener.newGenome(gen1);
        answer = listener.theMostPopularGenome();
        //then
        assertEquals("321",answer);
    }

    @Test
    public void popularGenomeWithDelete(){
        //given
        AnimalGenomesStatistic listener = new AnimalGenomesStatistic();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen3 = List.of((byte)3,(byte)2,(byte)1);
        String answer = "";
        //when
        listener.newGenome(gen1);
        listener.newGenome(gen1);
        listener.newGenome(gen1);
        listener.newGenome(gen1);
        listener.newGenome(gen3);
        listener.newGenome(gen3);
        listener.newGenome(gen3);
        listener.newGenome(gen3);
        listener.newGenome(gen1);
        listener.newGenome(gen1);
        listener.deleteGenome(gen2);
        listener.deleteGenome(gen2);
        listener.deleteGenome(gen2);
        answer = listener.theMostPopularGenome();
        //then
        assertEquals("321",answer);
    }

    @Test
    public void popularGenomeWithSingleDelete(){
        //given
        AnimalGenomesStatistic listener = new AnimalGenomesStatistic();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen3 = List.of((byte)3,(byte)2,(byte)1);
        String answer = "";
        //when
        listener.newGenome(gen1);
        listener.newGenome(gen1);
        listener.newGenome(gen1);
        listener.newGenome(gen1);
        listener.newGenome(gen3);
        listener.newGenome(gen3);
        listener.newGenome(gen3);
        listener.newGenome(gen3);
        listener.newGenome(gen1);
        listener.newGenome(gen1);
        listener.deleteGenome(gen2);
        answer = listener.theMostPopularGenome();
        //then
        assertEquals("123",answer);
    }

    @Test
    public void singleUsedGenomes(){
        //given
        AnimalGenomesStatistic listener = new AnimalGenomesStatistic();
        List<Byte> gen1 = List.of((byte)1,(byte)2,(byte)3);
        List<Byte> gen2 = List.of((byte)2,(byte)2,(byte)2);
        List<Byte> gen3 = List.of((byte)3,(byte)2,(byte)1);
        String answer = "";
        //when
        listener.newGenome(gen1);
        listener.newGenome(gen2);
        listener.newGenome(gen3);
        listener.deleteGenome(gen1);
        answer = listener.theMostPopularGenome();
        //then
        assertNotEquals("123",answer);
        assertTrue(Objects.equals(answer, "222") || Objects.equals(answer, "321"));
    }
}
