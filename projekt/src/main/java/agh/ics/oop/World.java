package agh.ics.oop;

import agh.ics.oop.model.*;

public class World {
    public static void main(String[] args)
    {
        GlobeMap map = new GlobeMap(10,10,new Vector2d(0,0),50,new ClassicalEnergyLoss(10),2);
        Simulation sim = new Simulation(map,10,500,350,12,new RandomMutate(0,6),3, new AnimalReproduction());
        for (int i = 0; i < 99; i++) {
            sim.run();
        }
    }
}
