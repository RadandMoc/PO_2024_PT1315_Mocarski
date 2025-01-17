package agh.ics.oop.model;

public class PoleEnergyLoss implements EnergyLoss {
    private final Boundary equator;
    private final int energyLoss;
    private final int energyLossAddition;

    public PoleEnergyLoss(Boundary equator, int energyLoss, int energyLossAddition){
        this.equator = equator;
        this.energyLoss = energyLoss;
        this.energyLossAddition = energyLossAddition;
    }

    @Override
    public int howManyEnergyToWalk(Animal animal) {
        return energyLoss + energyLossAddition * howManyRowsFromEquator(animal.getPosition());
    }

    private int howManyRowsFromEquator(Vector2d position){
        if (position.follows(equator.lowerLeft()) && position.precedes(equator.upperRight())) { return 0; }
        if (position.follows(new Vector2d(equator.lowerLeft().getX(), equator.upperRight().getY()))){
            return position.getY() - equator.upperRight().getY();
        }
        return equator.lowerLeft().getY() - position.getY();
    }
}
