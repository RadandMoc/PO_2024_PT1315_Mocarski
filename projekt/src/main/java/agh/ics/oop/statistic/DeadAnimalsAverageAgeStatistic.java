package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeadAnimalsAverageAgeStatistic implements Statistic{

    private final HashSet<Animal> startingAnimals;

    public DeadAnimalsAverageAgeStatistic(HashSet<Animal> startingAnimals) {
        this.startingAnimals = startingAnimals;
    }

    @Override
    public String getValue() {
        HashSet<Animal> deadAnimals = new HashSet<>();

        startingAnimals.forEach(animal -> {
            List<Animal> allDescendants = animal.getAllDescendants();
            allDescendants.add(animal);

            allDescendants.stream()
                    .filter(Animal::getIsDead)
                    .forEach(deadAnimals::add);
        });

        if (deadAnimals.isEmpty()) {
            return "średni wiek = 0";
        }

        double avgAge = deadAnimals.stream()
                .mapToInt(Animal::getLifeTime)
                .average()
                .orElse(0.0);
        return String.format("Średni wiek martwego zwierzaka: %.2f", avgAge);

    }


}
