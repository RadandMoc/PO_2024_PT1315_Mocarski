package agh.ics.oop.fabric;

import agh.ics.oop.model.MutateGenome;
import agh.ics.oop.model.MutationStrategy;
import agh.ics.oop.model.RandomMutate;
import agh.ics.oop.model.SwapMutate;

public class MutationFactory {
    public static MutateGenome createMutation(MutationStrategy type, int minMutation, int maxMutation, int lenOfGenome) {
        return switch (type) {
            case RandomMutate -> {
                if (maxMutation > lenOfGenome)
                    throw new IllegalArgumentException("Dlugosc genomu nie moze byc krotsza od maksymalnej liczby losowych mutacji");
                yield new RandomMutate(minMutation, maxMutation);
            }
            case SwapMutate -> new SwapMutate(minMutation, maxMutation);
        };

    }

}