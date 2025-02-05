package agh.ics.oop.fabric; // factory; fabric to nawet nie jest fabryka

import agh.ics.oop.model.MutateGenome;
import agh.ics.oop.model.MutationStrategy;
import agh.ics.oop.model.RandomMutate;
import agh.ics.oop.model.SwapMutate;

public class MutationFactory {
    public static MutateGenome createMutation(MutationStrategy type, int minMutation, int maxMutation) {
        return switch (type) {
            case RandomMutate -> new RandomMutate(minMutation, maxMutation);
            case SwapMutate -> new SwapMutate(minMutation, maxMutation);
        };

    }

}