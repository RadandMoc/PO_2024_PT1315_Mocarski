package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public abstract class AbstractWorldMap {
    protected final HashMap<Vector2d, HashSet<Animal>> animals = new HashMap<>();
    protected final HashMap<Vector2d, Plant> plants = new HashMap<>();
    private final List<MapChangeListener> observers = new ArrayList<>();
    private final int height;
    private final int width;

    public AbstractWorldMap(int height,int width){
        this.height = height;
        this.width = width;
    }

    public void place(WorldElement mapObj){
        if(mapObj instanceof Plant plant){
            plants.put(plant.getPosition(),plant);
        } else if (mapObj instanceof Animal animal) {
            if(!animals.containsKey(animal.getPosition()))
                animals.put(animal.getPosition(),new HashSet<>());
            animals.get(animal.getPosition()).add(animal);
        }
    }
}
