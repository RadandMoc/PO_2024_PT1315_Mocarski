package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;

import java.util.HashMap;
import java.util.HashSet;

public class AvgNumOfChildStatistic implements Statistic{
    final HashMap<Vector2d, HashSet<Animal>> animals;

    public AvgNumOfChildStatistic(HashMap<Vector2d, HashSet<Animal>> animals){
        this.animals = animals;
    }

    @Override
    public String getValue() {
        double avgNumOfChild = animals.values().stream().flatMap(HashSet::stream).mapToInt(Animal::getNumOfChild).average().orElse(0.0);
        return "%f".formatted(avgNumOfChild);
    }
}
