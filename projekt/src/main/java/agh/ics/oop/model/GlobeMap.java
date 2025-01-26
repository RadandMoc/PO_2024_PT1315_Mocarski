package agh.ics.oop.model;
import java.util.*;

    public class GlobeMap extends AbstractWorldMap{

    public GlobeMap(int width, int height, Vector2d leftDownBoundary, int plantEnergy, EnergyLoss energyLoss, int startNumOfPlants) {
        super(width, height, leftDownBoundary, plantEnergy, energyLoss,startNumOfPlants);
    }

    @Override
    public Iterator<Vector2d> plantsPreferredZone() {
        return new EquatorIterator(equator);
    }

    @Override
    public void generatePlants(int numOfPlants) {
        Random random = new Random();
        int center = 0, poles = 0;
        double decision;
        for (int i = 0; i < numOfPlants; i++) {
            decision = random.nextDouble();
            if(decision<0.8){
                center++;
            } else {
                poles++;
            }
        }
        polesPlantGenerator.setHowManyGenerate(poles);
        equatorPlantGenerator.setHowManyGenerate(center);
        try{
            for(Vector2d pos: equatorPlantGenerator){
                plants.put(pos,new Plant(pos));
            }
        }
        catch (ToMuchValuesToGenerateException ignored){}
        try{
            for(Vector2d pos:polesPlantGenerator){
                plants.put(pos,new Plant(pos));
            }
        }
        catch (ToMuchValuesToGenerateException ignored){
        }
    }

    @Override
    public MoveResult animalMoveChanges(Animal animal) {
        Vector2d animalPosition = animal.position();
        MapDirection orientation = animal.getOrientation();
        Vector2d preMove = animalPosition.add(orientation.toUnitVector());
        int energyLost = energyLoss.howManyEnergyToWalk(animal);
        if (preMove.y() > boundary.upperRight().y() || preMove.y() < boundary.lowerLeft().y())
        {
            return new MoveResult(animalPosition, orientation.change(4),energyLoss.howManyEnergyToWalk(animal));
        }
        if (preMove.x() > boundary.upperRight().x())
        {
            return new MoveResult(new Vector2d(boundary.lowerLeft().x(),preMove.y()), orientation, energyLost);
        }
        else if (preMove.x() < boundary.lowerLeft().x())
        {
            return new MoveResult(new Vector2d(boundary.upperRight().x(),preMove.y()), orientation, energyLost);
        }
        return new MoveResult(preMove, orientation, energyLost);
    }
}
