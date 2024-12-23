package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class SwapMutate extends AbstractMutateGenome {

    public SwapMutate(int minNumOfMutation, int maxNumOfMutation) {
        super(minNumOfMutation, maxNumOfMutation);
    }

    @Override
    public List<Byte> Mutate(List<Byte> genome) {
        int numOfMutations = getRandomMutationCount();
        List<Byte> mutatedGenome = new ArrayList<>(genome);

        for (int i = 0; i < numOfMutations; i++) {
            List<Integer> shuffledIndices = getShuffledIndices(genome.size());
            int index1 = shuffledIndices.get(0);
            int index2 = shuffledIndices.get(1);
// Podmienić na listę swapów. Szczegóły w issue na git
            Byte temp = mutatedGenome.get(index1);
            mutatedGenome.set(index1, mutatedGenome.get(index2));
            mutatedGenome.set(index2, temp);
        }

        return mutatedGenome;
    }
}
