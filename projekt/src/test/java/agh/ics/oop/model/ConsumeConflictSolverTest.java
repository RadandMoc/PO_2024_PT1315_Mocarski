package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsumeConflictSolverTest {

    @Test
    public void consumeConflictSolverTest(){
        // given
        Animal animal1 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        Animal animal3 = new Animal(new Vector2d(5, 5), 40, 0, List.of((byte) 1),null);
        Animal animal2 = new Animal(new Vector2d(5, 5), 50, 0, List.of((byte) 1),null);

        List<Animal> animalList = List.of(
                animal1,
                animal2,
                animal3
        );

        ConsumeConflictSolver con = new ConsumeConflictSolver();

        // then
        assertEquals(60, con.findStrongestAnimal(animalList).getEnergy());
    }

    @Test
    public void consumeConflictSolverTieWithEnergyTest(){
        // given
        Animal animal1 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        Animal animal2 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        Animal animal3 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        animal1.ableToWalk(20,null);
        animal1.ableToWalk(20,null);
        animal2.ableToWalk(20,null);

        List<Animal> animalList = List.of(
                animal1,
                animal2,
                animal3

        );

        ConsumeConflictSolver con = new ConsumeConflictSolver();

        // then
        assertEquals(animal1, con.findStrongestAnimal(animalList));
    }


    @Test
    public void consumeConflictSolverTieWithEnergyTieWithLifetimeTest(){
        // given
        Animal animal1 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        Animal animal2 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        Animal animal3 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        animal1.addChild(new Animal(new Vector2d(5, 5), 100, 0, List.of((byte) 1),null));

        List<Animal> animalList = List.of(
                animal1,
                animal2,
                animal3

        );
        ConsumeConflictSolver con = new ConsumeConflictSolver();

        // then
        assertEquals(animal1, con.findStrongestAnimal(animalList));
    }



    @Test
    public void consumeConflictSolverTieWithEnergyTieWithLifeTimeTieWithNumOfChildTest(){
        // given
        Animal animal1 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        Animal animal2 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        Animal animal3 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        Animal animal4 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);
        Animal animal5 = new Animal(new Vector2d(5, 5), 60, 0, List.of((byte) 1),null);


        List<Animal> animalList = List.of(
                animal1,
                animal2,
                animal3,
                animal4,
                animal5

        );

        ConsumeConflictSolver con = new ConsumeConflictSolver();

        // when
        boolean isOneTheBest = true;
        for (int i = 0; i < 100; i++){
            if (isOneTheBest) {
                isOneTheBest = animal1 == con.findStrongestAnimal(animalList);
            }
            else{
                break;
            }
        }

        // then
        assertFalse(isOneTheBest);
    }
}