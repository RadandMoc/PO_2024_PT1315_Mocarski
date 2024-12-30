package agh.ics.oop.model;

import java.util.*;
import java.util.stream.Collectors;

public class AnimalReproduction implements ReproductionStrategy{
    Comparator<Animal> comparator = new AnimalConflictComparator();


    @Override
    public List<ReproductionResult> reproduce(Collection<Animal> animalCollection, int energyForBreeding) {
        if (animalCollection.size() < 2) {
            return new ArrayList<>();
        }

        List<ReproductionResult> reproductionResults = new ArrayList<>();
        List<Animal> sortedAnimals = animalCollection.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        if (comparator.compare(sortedAnimals.get(0), sortedAnimals.get(sortedAnimals.size() - 1)) >= 0){
            sortedAnimals = sortedAnimals.reversed();
        }

        for (int i = 0; i < sortedAnimals.size() - 1; i += 2) {
            Animal animal1 = sortedAnimals.get(i);
            Animal animal2 = sortedAnimals.get(i + 1);
            List<Byte> childGenome = breed(animal1, animal2);
            reproductionResults.add(new ReproductionResult(childGenome,List.of(animal1,animal2)));
            animal1.changeEnergy(-energyForBreeding);
            animal2.changeEnergy(-energyForBreeding);
        }

        return reproductionResults;
    }

    private List<Byte> breed(Animal animal1, Animal animal2){
        Random rand = new Random();
        boolean isFromRight = rand.nextBoolean();
        double percentOfGene = (double) animal1.getEnergy() / (animal1.getEnergy() + animal2.getEnergy());
        List<Byte> animal1Gene = animal1.getPartOfGen(percentOfGene, isFromRight);
        List<Byte> animal2Gene = animal1.getPartOfGen(1-percentOfGene, !isFromRight);

        if (isFromRight) {
            animal2Gene.addAll(animal1Gene);
            return animal2Gene;
        }

        animal1Gene.addAll(animal2Gene);
        return animal1Gene;
    }
}
