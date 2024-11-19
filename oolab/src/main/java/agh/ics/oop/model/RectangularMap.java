package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements WorldMap {
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final int height;
    private final int width;
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final MapVisualizer visualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height){
        this(width,height,new Vector2d(0,0));
    }

    public RectangularMap(int width, int height, Vector2d lowerLeftPoint){
        this.height = height;
        this.width = width;
        lowerLeft = lowerLeftPoint;
        upperRight = new Vector2d(lowerLeft.getX() + width -1, lowerLeft.getY() + height -1);
    }

    @Override
    public boolean place(Animal animal) {
        if(animal != null && canMoveTo(animal.getLocalization())) {
            animals.put(animal.getLocalization(),animal);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        final Vector2d startPosition = animal.getLocalization();
        animal.move(direction,this);
        if (place(animal))
            animals.remove(startPosition);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) { // sprawdzić, czy miejsce nie jest zajmowane przez trawę
        return (position != null && position.precedes(upperRight) && position.follows(lowerLeft) && (!isOccupied(position)));
    }

    @Override
    public String toString(){
        return visualizer.draw(lowerLeft,upperRight);
    }
}
