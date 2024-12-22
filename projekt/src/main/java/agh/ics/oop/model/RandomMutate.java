package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class RandomMutate extends AbstractMutateGenome {

    public RandomMutate(int minNumOfMutation, int maxNumOfMutation) {
        super(minNumOfMutation, maxNumOfMutation);
    }

    @Override
    public List<Byte> Mutate(List<Byte> genome) {
        int numOfMutations = getRandomMutationCount();
        List<Byte> mutatedGenome = new ArrayList<>(genome);

        List<Integer> shuffledIndices = getShuffledIndices(genome.size());
        for (int i = 0; i < numOfMutations; i++) {
            int indexToMutate = shuffledIndices.get(i);
            byte newGene = (byte) random.nextInt(8); // Losowa wartość genu w zakresie 0-7
            mutatedGenome.set(indexToMutate, newGene);
        }

        return mutatedGenome;
    }
}
