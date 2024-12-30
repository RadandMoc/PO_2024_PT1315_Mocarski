package agh.ics.oop.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.Random;

public class ConsumeConflictSolver implements StrongestAnimalFinder {
    private final Comparator<Animal> comparator = new AnimalConflictComparator();

    @Override
    public Animal findStrongestAnimal(Collection<Animal> animals) {
        return animals.stream()
                .min(comparator)
                .orElse(null);
    }

}
