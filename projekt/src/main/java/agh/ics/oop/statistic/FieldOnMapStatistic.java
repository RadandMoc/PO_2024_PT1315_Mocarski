package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Plant;
import agh.ics.oop.model.Vector2d;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FieldOnMapStatistic implements Statistic{
    private final HashMap<Vector2d, Plant> plants;
    private final HashMap<Vector2d, HashSet<Animal>> animals;
    private final int places;

    public FieldOnMapStatistic(int places, HashMap<Vector2d, Plant> plants, HashMap<Vector2d, HashSet<Animal>> animals){
        this.places = places;
        this.plants = plants;
        this.animals = animals;
    }

    private int getFreePlaces() {
        Set<Vector2d> occupiedPlaces = new HashSet<>(animals.keySet());
        occupiedPlaces.addAll(plants.keySet());
        return places - occupiedPlaces.size();
    }

    @Override
    public String getValue() {
        return "all plants %d \n %d all animals \n %d free places".formatted(plants.size(), animals.size(), getFreePlaces());
    }
}
