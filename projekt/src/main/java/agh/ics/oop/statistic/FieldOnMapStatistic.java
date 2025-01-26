package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Plant;
import agh.ics.oop.model.SimulationDataProvider;
import agh.ics.oop.model.Vector2d;

import java.util.Set;
import java.util.stream.Collectors;

public class FieldOnMapStatistic implements Statistic {
    private final SimulationDataProvider dataProvider;

    public FieldOnMapStatistic(SimulationDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    private int getFreePlaces() {
        Set<Vector2d> occupiedPlacesByPlants = dataProvider.getAllPlantsStream()
                .map(Plant::position)
                .collect(Collectors.toSet());

        Set<Vector2d> occupiedPlaces = dataProvider.getAllAnimalsStream()
                .map(Animal::position)
                .collect(Collectors.toSet());

        occupiedPlaces.addAll(occupiedPlacesByPlants);

        return dataProvider.getMapSize() - occupiedPlaces.size();
    }

    @Override
    public String getValue() {
        long plantCount = dataProvider.getAllPlantsStream().count();
        long animalCount = dataProvider.getAllAnimalsStream().count();

        return "Wszystkie rośliny: %d\nWszystkie zwierzęta: %d\nWolne miejsca: %d".formatted(plantCount, animalCount, getFreePlaces());
    }
}