package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);

    @Override
    public void move(Animal animal, MoveDirection direction) {
        final Vector2d startPosition = animal.getPosition();
        animal.move(direction,this);
        if (place(animal))
            animals.remove(startPosition);
    }

    @Override
    public boolean place(Animal animal) {
        if(animal != null && canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(),animal);
            return true;
        }
        return false;
    }
}
