package agh.ics.oop.model;

import java.util.Set;
import java.util.stream.Stream;

public interface SimulationDataProvider {
    Stream<Animal> getAllAnimalsStream();
    Stream<Plant> getAllPlantsStream();
    int getMapSize();
    Set<Animal> getStartingAnimals();
}
