package agh.ics.oop.model;
import java.util.*;

    public class GlobeMap extends AbstractWorldMap{
    private final int prefferdZoneDownHeight;
    private final int prefferdZoneUpHeight;
    private final static double prefferedPlanZoneAreaPercent = 0.2f;


    public GlobeMap(int width, int height, Vector2d leftDownBoundary, int plantEnergy, EnergyLoss energyLoss) {
        super(width, height, leftDownBoundary, plantEnergy, energyLoss);
        int equator_height =  (int) Math.round(height * prefferedPlanZoneAreaPercent);
        prefferdZoneDownHeight = (height - equator_height) / 2 + leftDownBoundary.getY();
        prefferdZoneUpHeight = prefferdZoneDownHeight + equator_height - 1;
    }

    @Override
    public void generatePlants(int startedPlants) {
        double p = 0.2f;
        Random rand = new Random();
        double choice = rand.nextDouble();
        if (choice > 0.2){
            // generate from preffered zone
        }
        // generate from other zone

    }


    @Override
    public MoveResult animalMoveChanges(Animal animal) {
        Vector2d animalPosition = animal.getPosition();
        MapDirection orientation = animal.getOrientation();
        Vector2d preMove = animalPosition.add(orientation.toUnitVector());
        int energyLost = energyLoss.howManyEnergyToWalk(animal);
        if (preMove.getY() > boundary.upperRight().getY() || preMove.getY() < boundary.lowerLeft().getY())
        {
            return new MoveResult(animalPosition, orientation.change(4),energyLoss.howManyEnergyToWalk(animal));
        }
        if (preMove.getX() > boundary.upperRight().getX())
        {
            return new MoveResult(new Vector2d(boundary.lowerLeft().getX(),preMove.getY()), orientation, energyLost);
        }
        else if (preMove.getX() < boundary.lowerLeft().getX())
        {
            return new MoveResult(new Vector2d(boundary.upperRight().getX(),preMove.getY()), orientation, energyLost);
        }
        return new MoveResult(preMove, orientation, energyLost);

    }


}
