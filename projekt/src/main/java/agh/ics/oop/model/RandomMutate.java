package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class RandomMutate extends AbstractMutateGenome {

    public RandomMutate(int minNumOfMutation, int maxNumOfMutation) {
        super(minNumOfMutation, maxNumOfMutation);
    }

    public RandomMutate(int minNumOfMutation, int maxNumOfMutation, int seedForRandom) {
        super(minNumOfMutation, maxNumOfMutation, seedForRandom);
    }

    @Override
    public List<Byte> mutate(List<Byte> genome) {
        int numOfMutations = getRandomMutationCount();
        List<Byte> mutatedGenome = new ArrayList<>(genome);

        List<Integer> shuffledIndices = getShuffledIndices(genome.size());
        for (int i = 0; i < min(numOfMutations, genome.size()); i++) {
            int indexToMutate = shuffledIndices.get(i);
            byte newGene = (byte) random.nextInt(8); // Losowa wartość genu w zakresie 0-7
            mutatedGenome.set(indexToMutate, newGene);
        }

        return mutatedGenome;
    }
}
