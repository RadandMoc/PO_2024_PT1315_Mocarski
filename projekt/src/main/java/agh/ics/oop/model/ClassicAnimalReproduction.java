package agh.ics.oop.model;

import java.util.*;
import java.util.stream.Collectors;

public class ClassicAnimalReproduction implements ReproductionStrategy{
    private Comparator<Animal> comparator = new AnimalConflictComparator();
    private final Random random;

    public ClassicAnimalReproduction(int seedForRandom){
        random = new Random(seedForRandom);
    }

    public ClassicAnimalReproduction(){
        this(new Random().nextInt());
    }

    @Override
    public List<ReproductionResult> reproduce(Collection<Animal> animalCollection, int energyForBreeding) {
        if (animalCollection.size() < 2) {
            return new ArrayList<>();
        }

        List<ReproductionResult> reproductionResults = new ArrayList<>();
        List<Animal> sortedAnimals = animalCollection.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        if (comparator.compare(sortedAnimals.getFirst(), sortedAnimals.getLast()) >= 0){
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
        boolean isFromRight = random.nextBoolean();
        double percentOfGene = (double) animal1.getEnergy() / (animal1.getEnergy() + animal2.getEnergy());
        int genomeLength = animal1.getSizeOfGenome();
        int pointOfSlice = (int)(percentOfGene * genomeLength);
        List<Byte> animal1Gene = animal1.getPartOfGen(pointOfSlice, isFromRight);
        List<Byte> animal2Gene = animal2.getPartOfGen(genomeLength-pointOfSlice, !isFromRight);

        if (isFromRight) {
            animal2Gene.addAll(animal1Gene);
            return animal2Gene;
        }

        animal1Gene.addAll(animal2Gene);
        return animal1Gene;
    }
}
