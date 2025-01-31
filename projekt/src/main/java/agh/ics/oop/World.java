package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Application.launch(SimulationApp.class, args);
        /*
        GlobeMap map = new GlobeMap(11,11,new Vector2d(0,0),50,new ClassicalEnergyLoss(10),2);
        Simulation sim = new Simulation(map,10,500,150,100, 12,new RandomMutate(0,6),88, new AnimalReproduction());
        for (int i = 0; i < 99; i++) {
            sim.run();
        }

        */
    }
}
