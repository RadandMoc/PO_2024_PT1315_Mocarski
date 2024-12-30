package agh.ics.oop.model;
import java.util.*;

    public class GlobeMap extends AbstractWorldMap{
    private final int prefferdZoneDownHeight;
    private final int prefferdZoneUpHeight;
    private final static double prefferedPlanZoneAreaPercent = 0.2f;


    public GlobeMap(int width, int height, Vector2d leftDownBoundary, int plantEnergy, EnergyLoss energyLoss, int startNumOfPlants) {
        super(width, height, leftDownBoundary, plantEnergy, energyLoss,startNumOfPlants);
        int equator_height =  (int) Math.round(height * prefferedPlanZoneAreaPercent);
        prefferdZoneDownHeight = (height - equator_height) / 2 + leftDownBoundary.getY();
        prefferdZoneUpHeight = prefferdZoneDownHeight + equator_height - 1;
    }

    @Override
    public void generatePlants(int numOfPlants) {
        Random random = new Random();
        int north = 0, center = 0, south = 0;
        double decision;
        for (int i = 0; i < numOfPlants; i++) {
            decision = random.nextDouble();
            if(decision<0.8){
                center++;
            } else if (decision<0.9) {
                north++;
            } else {
                south++;
            }
        }
        southPlantGenerator.setHowManyGenerate(south);
        centerPlantGenerator.setHowManyGenerate(center);
        try{
            for(Vector2d pos:centerPlantGenerator){
                plants.put(pos,new Plant(pos));
            }
        }
        catch (ToMuchValuesToGenerateException ignored){}
        try{
            for(Vector2d pos:southPlantGenerator){
                plants.put(pos,new Plant(pos));
            }
        }
        catch (ToMuchValuesToGenerateException e){
            north += e.getErrorValue();
        }
        northPlantGenerator.setHowManyGenerate(north);
        try {
            for(Vector2d pos:northPlantGenerator){
                plants.put(pos,new Plant(pos));
            }
        }
        catch (ToMuchValuesToGenerateException e){
            southPlantGenerator.setHowManyGenerate(e.getErrorValue());
            try{
                for(Vector2d pos:southPlantGenerator){
                    plants.put(pos,new Plant(pos));
                }
            }
            catch (ToMuchValuesToGenerateException ignored){}
        }
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
