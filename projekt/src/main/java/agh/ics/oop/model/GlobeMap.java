package agh.ics.oop.model;
import java.util.*;

    public class GlobeMap extends AbstractWorldMap{
    private final int prefferdZoneDownHeight;
    private final int prefferdZoneUpHeight;
    private final static double prefferedPlanZoneAreaPercent = 0.2f;


    public GlobeMap(int width, int height, Vector2d leftDownBoundary, int plantEnergy) {
        super(width, height, leftDownBoundary, plantEnergy);
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
    public MoveResult animalMoveChanges(Vector2d animalPosition, MapDirection orientation) {
        return null;
    }


}
