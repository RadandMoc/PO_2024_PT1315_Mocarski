package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SimulationDataProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DeadAnimalsAverageAgeStatistic implements Statistic {
    private final SimulationDataProvider dataProvider;

    public DeadAnimalsAverageAgeStatistic(SimulationDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public String getValue() {
        Set<Animal> startingAnimals = dataProvider.getStartingAnimals();
        Set<Animal> deadAnimals = new HashSet<>();

        startingAnimals.forEach(animal -> {
            Set<Animal> allDescendants = animal.getAllDescendants();
            allDescendants.add(animal);

            allDescendants.stream()
                    .filter(Animal::getIsDead)
                    .forEach(deadAnimals::add);
        });

        if (deadAnimals.isEmpty()) {
            return "Średni wiek martwych zwierząt: 0";
        }

        double avgAge = deadAnimals.stream()
                .mapToInt(Animal::getLifeTime)
                .average()
                .orElse(0.0);

        return String.format("Średni wiek martwych zwierząt: %.2f", avgAge);
    }
}

