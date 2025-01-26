package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Plant;
import agh.ics.oop.model.SimulationDataProvider;

public class FieldOnMapStatistic implements Statistic {
    private final SimulationDataProvider dataProvider;

    public FieldOnMapStatistic(SimulationDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    private int getFreePlaces() {
        long occupiedPlaces = dataProvider.getAllAnimalsStream()
                .map(Animal::position)
                .distinct()
                .count() +
                dataProvider.getAllPlantsStream()
                        .map(Plant::position)
                        .distinct()
                        .count();

        return dataProvider.getMapSize() - (int) occupiedPlaces;
    }

    @Override
    public String getValue() {
        long plantCount = dataProvider.getAllPlantsStream().count();
        long animalCount = dataProvider.getAllAnimalsStream().count();

        return "Wszystkie rośliny: %d\nWszystkie zwierzęta: %d\nWolne miejsca: %d".formatted(plantCount, animalCount, getFreePlaces());
    }
}