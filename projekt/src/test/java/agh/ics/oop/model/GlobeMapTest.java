package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobeMapTest {


    @Test
    public void moveTestForGlobeMap(){
        // given
        GlobeMap map = new GlobeMap(20,20,new Vector2d(0,0),20, new ClassicalEnergyLoss(50),0);
        final List<Byte> directions1 = List.of(
                (byte) 5
        );
        final List<Byte> directions2 = List.of(
                (byte) 0
        );
        final List<Byte> directions3 = List.of(
                (byte) 3
        );
        final List<Byte> directions4 = List.of(
                (byte) 7
        );
        Animal a1 = new Animal(new Vector2d(0,0), 100, 0,directions1,MapDirection.N,0);
        Animal a2 = new Animal(new Vector2d(19,19), 100, 0,directions2,MapDirection.N,0);
        Animal a3 = new Animal(new Vector2d(19,0), 100, 0,directions3,MapDirection.N,0);
        Animal a4 = new Animal(new Vector2d(0,19), 100, 0,directions4,MapDirection.N,0);

        map.place(a1);
        map.place(a2);
        map.place(a3);
        map.place(a4);

        // when
        map.movesAllAnimals();

        // then
        assertEquals(new Vector2d(0,0), a1.getPosition());
        assertEquals(MapDirection.NE, a1.getOrientation());
        assertEquals(new Vector2d(19,19), a2.getPosition());
        assertEquals(MapDirection.S, a2.getOrientation());
        assertEquals(new Vector2d(19,0), a3.getPosition());
        assertEquals(MapDirection.NW, a3.getOrientation());
        assertEquals(new Vector2d(0,19), a4.getPosition());
        assertEquals(MapDirection.SE, a4.getOrientation());
    }

    @Test
    public void moveTestIfAnimalGoToAnotherSideGlobeMap(){
        // given
        GlobeMap map = new GlobeMap(20,20,new Vector2d(0,0),20, new ClassicalEnergyLoss(50),0);
        final List<Byte> directions1 = List.of(
                (byte) 6
        );
        final List<Byte> directions2 = List.of(
                (byte) 2
        );
        final List<Byte> directions3 = List.of(
                (byte) 7
        );
        final List<Byte> directions4 = List.of(
                (byte) 3
        );

        Animal a1 = new Animal(new Vector2d(0,10), 100, 0,directions1,MapDirection.N,0);
        Animal a2 = new Animal(new Vector2d(19,10), 100, 0,directions2,MapDirection.N,0);
        Animal a3 = new Animal(new Vector2d(0,10), 100, 0,directions3,MapDirection.N,0);
        Animal a4 = new Animal(new Vector2d(19,10), 100, 0,directions4,MapDirection.N,0);


        map.place(a1);
        map.place(a2);
        map.place(a3);
        map.place(a4);

        // when
        map.movesAllAnimals();

        // then
        assertEquals(new Vector2d(19,10), a1.getPosition());
        assertEquals(new Vector2d(0,10), a2.getPosition());
        assertEquals(new Vector2d(19,11), a3.getPosition());
        assertEquals(new Vector2d(0,9), a4.getPosition());
    }

    @Test
    public void moveTestMoveWithinBoundariesGlobeMap(){
        // given
        final GlobeMap map = new GlobeMap(20,20,new Vector2d(0,0),20, new ClassicalEnergyLoss(50),0);
        final List<Byte> directions1 = Arrays.asList(
                (byte)6, (byte)6, (byte)6, (byte)6, (byte)6
        );
        Animal a1 = new Animal(new Vector2d(10,10), 100, 0,directions1,MapDirection.N,0);
        map.place(a1);

        // when
        map.movesAllAnimals();
        // then
        assertEquals(new Vector2d(9,10), a1.getPosition());
    }

    @Test
    public void consumeTest(){
        // given
        GlobeMap map = new GlobeMap(20,20,new Vector2d(0,0),20, new ClassicalEnergyLoss(20),0);
        Vector2d pos1 = new Vector2d(5,5);
        Vector2d pos2 = new Vector2d(3,1);
        Vector2d pos3 = new Vector2d(7,1);

        Animal animal1 = new Animal(pos1, 100, 0, new ArrayList<>(),MapDirection.N,0);
        Animal animal2 = new Animal(pos2, 100, 0, new ArrayList<>(),MapDirection.N,0);
        Animal animal3 = new Animal(pos3, 100, 0, new ArrayList<>(),MapDirection.N,0);

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        Plant plant1 = new Plant(pos1);
        Plant plant2 = new Plant(pos2);
        Plant plant3 = new Plant(pos3);

        map.place(plant1);
        map.place(plant2);
        map.place(plant3);

        // when
        map.animalsConsume(new ConsumeConflictSolver());

        //then
        assertEquals(120,animal1.getEnergy());
        assertEquals(120,animal2.getEnergy());
        assertEquals(120,animal3.getEnergy());
        assertTrue(map.plants.isEmpty());
    }
}