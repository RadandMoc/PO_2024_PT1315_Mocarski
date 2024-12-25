package agh.ics.oop.model;

public class ClassicalEnergyLoss implements EnergyLoss {
    private final int energyLoss;

    public ClassicalEnergyLoss(int energyLoss) {
        this.energyLoss = energyLoss;
    }

    @Override
    public int howManyEnergyToWalk(Animal animal) {
        return energyLoss;
    }

}
