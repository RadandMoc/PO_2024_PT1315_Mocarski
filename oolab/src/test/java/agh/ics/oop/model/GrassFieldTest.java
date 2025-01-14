package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GrassFieldTest {
    @Test
    public void getOrderedAnimalsTest(){
        // given
        GrassField map = new GrassField(10);
        Animal animal1 = new Animal(new Vector2d(2, 3));
        Animal animal2 = new Animal(new Vector2d(1, 5));
        Animal animal3 = new Animal(new Vector2d(2, 1));
        Animal animal4 = new Animal(new Vector2d(1, 2));
        try {
            map.place(animal1);
            map.place(animal2);
            map.place(animal3);
            map.place(animal4);
        }
        catch (IncorrectPositionException ignored){
            fail();
        }
         // when
        List<Animal> orderedAnimals = map.getOrderedAnimals();

        // then
        assertEquals(animal4, orderedAnimals.get(0));
        assertEquals(animal2, orderedAnimals.get(1));
        assertEquals(animal3, orderedAnimals.get(2));
        assertEquals(animal1, orderedAnimals.get(3));
    }
}
