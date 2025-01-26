package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class AbstractMutateGenome implements MutateGenome {
    protected final int minMutation;
    protected final int maxMutation;
    protected final Random random;

    public AbstractMutateGenome(int minNumOfMutation, int maxNumOfMutation){
        this(minNumOfMutation,maxNumOfMutation, new Random().nextInt());
    }

    public AbstractMutateGenome(int minNumOfMutation, int maxNumOfMutation, int seedForRandom)  {
        if (maxNumOfMutation < minNumOfMutation){
            throw new IllegalArgumentException("maxNumOfMutation cannot be less than minNumOfMutation");
        }
        this.minMutation = minNumOfMutation;
        this.maxMutation = maxNumOfMutation;
        this.random = new Random(seedForRandom);
    }

    protected int getRandomMutationCount() {
        return random.nextInt(maxMutation - minMutation + 1) + minMutation;
    }

    protected List<Integer> getShuffledIndices(int size) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
        return indices;
    }
}
