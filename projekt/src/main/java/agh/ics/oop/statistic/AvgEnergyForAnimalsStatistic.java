package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;

import java.util.HashMap;
import java.util.HashSet;

public class AvgEnergyForAnimalsStatistic implements Statistic{
    private final HashMap<Vector2d, HashSet<Animal>> animals;

    public AvgEnergyForAnimalsStatistic(HashMap<Vector2d, HashSet<Animal>> animals) {
        this.animals = animals;
    }

    @Override
    public String getValue() {
        double avgEnergy = animals.values().stream()
                .flatMap(HashSet::stream)
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0.0);

        return String.format("Średnia energia zwierząt: %.2f", avgEnergy);
    }
}
