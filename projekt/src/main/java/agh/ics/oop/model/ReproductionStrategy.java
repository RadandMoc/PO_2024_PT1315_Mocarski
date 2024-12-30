package agh.ics.oop.model;

import java.util.Collection;
import java.util.List;

public interface ReproductionStrategy {
     List<ReproductionResult> reproduce(Collection<Animal> animalCollection, int lossEnergyForBreeding);
}
