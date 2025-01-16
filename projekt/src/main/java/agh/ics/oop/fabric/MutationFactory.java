package agh.ics.oop.fabric;

import agh.ics.oop.model.MutateGenome;
import agh.ics.oop.model.MutationStrategy;
import agh.ics.oop.model.RandomMutate;
import agh.ics.oop.model.SwapMutate;

public class MutationFactory {
    public static MutateGenome createMutation(MutationStrategy type, int minMutation, int maxMutation) {
        switch (type) {
            case RandomMutate:
                return new RandomMutate(minMutation, maxMutation);
            case SwapMutate:
                return new SwapMutate(minMutation, maxMutation);
            default:
                throw new IllegalArgumentException("Nieznany typ: " + type);
        }

    }

}