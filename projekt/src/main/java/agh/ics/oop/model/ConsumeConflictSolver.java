package agh.ics.oop.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.Random;

public class ConsumeConflictSolver implements StrongestAnimalFinder, Comparator<Animal> {
    @Override
    public Animal findStrongestAnimal(Collection<Animal> animals) {
        return animals.stream()
                .max(this)
                .orElse(null);
    }

    @Override
    public int compare(Animal o1, Animal o2) {
        int compare = Integer.compare(o2.getEnergy(), o1.getEnergy());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(o2.getLifeTime(), o1.getLifeTime());
        if (compare != 0) {
            return compare;
        }

        compare = Integer.compare(o2.getNumOfChild(), o1.getNumOfChild());
        if (compare != 0) {
            return compare;
        }

        return new Random().nextBoolean() ? 1 : -1;
    }
}
