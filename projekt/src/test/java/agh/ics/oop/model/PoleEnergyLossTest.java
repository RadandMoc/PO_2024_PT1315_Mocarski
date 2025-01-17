package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PoleEnergyLossTest {
    @Test
    public void testEnergy(){
        // given
        Animal animal1 = new Animal(new Vector2d(6,6), 50, 10, List.of((byte)0,(byte)1,(byte)2,(byte)3));
        Animal animal2 = new Animal(new Vector2d(3,3), 50, 10, List.of((byte)0,(byte)1,(byte)2,(byte)3));
        Animal animal3 = new Animal(new Vector2d(-3,-3), 50, 10, List.of((byte)0,(byte)1,(byte)2,(byte)3));


        PoleEnergyLoss energyLossMethod = new PoleEnergyLoss(new Boundary(new Vector2d(0,0), new Vector2d(4,4)),10,1);


        // when
        int energyLoss1 = energyLossMethod.howManyEnergyToWalk(animal1);
        int energyLoss2 = energyLossMethod.howManyEnergyToWalk(animal2);
        int energyLoss3 = energyLossMethod.howManyEnergyToWalk(animal3);

        // then

        assertEquals(12, energyLoss1);
        assertEquals(10, energyLoss2);
        assertEquals(13, energyLoss3);


    }


}