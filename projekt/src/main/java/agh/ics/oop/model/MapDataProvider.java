package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapDataProvider implements SimulationDataProvider, SimTurnListener  {
    private final AbstractWorldMap map;

    private List<Animal> currentAnimals;
    private List<Plant> currentPlants;

    public MapDataProvider(AbstractWorldMap map) {
        this.map = map;

    }

    @Override
    public Stream<Animal> getAllAnimalsStream() {
        return currentAnimals.stream();
    }

    @Override
    public Stream<Plant> getAllPlantsStream() {
        return currentPlants.stream();
    }

    @Override
    public int getMapSize() {
        return map.height * map.width;
    }


    @Override
    public String getMostPopularGenome() {
        return map.theMostPopularGenome();
    }

    public float getAverageLifeTime() {
        return map.getAverageLifeTime();
    }

    @Override
    public void onNewTurnChange(Simulation sim) {
        synchronized (map.animals) {
            currentAnimals = map.animals.values().stream()
                    .flatMap(Set::stream)
                    .collect(Collectors.toList());
        }
        synchronized (map.plants) {
            currentPlants = new ArrayList<>(map.plants.values());
        }
    }
}