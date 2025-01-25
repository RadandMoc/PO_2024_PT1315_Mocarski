package agh.ics.oop.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapDataProvider implements SimulationDataProvider  {
    private final AbstractWorldMap map;
    private final Set<Animal> startingAnimals;

    public MapDataProvider(AbstractWorldMap map, Set<Animal> startingAnimals) {
        this.map = map;
        this.startingAnimals = startingAnimals;
    }

    @Override
    public Stream<Animal> getAllAnimalsStream() {
        return map.animals.values().stream()
                .flatMap(Set::stream);
    }

    @Override
    public Stream<Plant> getAllPlantsStream() {
        return map.plants.values().stream();
    }

    @Override
    public int getMapSize() {
        return map.height * map.width;
    }

    @Override
    public Set<Animal> getStartingAnimals() {
        return startingAnimals;
    }

    @Override
    public String getMostPopularGenome() {
        return map.theMostPopularGenome();
    }
}
