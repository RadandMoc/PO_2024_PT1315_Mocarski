package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class SwapMutate extends AbstractMutateGenome {

    public SwapMutate(int minNumOfMutation, int maxNumOfMutation) {
        super(minNumOfMutation, maxNumOfMutation);
    }

    public SwapMutate(int minNumOfMutation, int maxNumOfMutation, int seedForRandom){
        super(minNumOfMutation,maxNumOfMutation,seedForRandom);
    }

    @Override
    public List<Byte> mutate(List<Byte> genome) {
        int numOfMutations = getRandomMutationCount();
        List<Byte> mutatedGenome = new ArrayList<>(genome);
        List<Integer> shuffledIndices = getShuffledIndices(genome.size());
        int mutationsPerformed = 0;

        while (mutationsPerformed < numOfMutations) {
            for (int i = 0; i < shuffledIndices.size() - 1; i += 2) {
                if (mutationsPerformed >= numOfMutations) {
                    break;
                }
                int index1 = shuffledIndices.get(i);
                int index2 = shuffledIndices.get(i + 1);

                Byte temp = mutatedGenome.get(index1);
                mutatedGenome.set(index1, mutatedGenome.get(index2));
                mutatedGenome.set(index2, temp);

                mutationsPerformed++;
            }

            if (mutationsPerformed < numOfMutations) {
                shuffledIndices = getShuffledIndices(genome.size());
            }
        }

        return mutatedGenome;
    }


}
