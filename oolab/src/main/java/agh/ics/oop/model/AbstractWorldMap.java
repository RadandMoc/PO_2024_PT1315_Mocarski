package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();

    public void addObserver(MapChangeListener observer){
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer){
        observers.remove(observer);
    }

    protected void mapChanged(String message){
        for(MapChangeListener item : observers){
            item.mapChanged(this,message);
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        final Vector2d prevPos = animal.getPosition();
        animal.move(direction, this);
        final Vector2d newPos = animal.getPosition();
        if (!prevPos.equals(newPos)){
            animals.put(newPos, animal);
            animals.remove(prevPos);
            mapChanged("Poruszono zwierzę z pozycji %s na pozycję %s ".formatted(prevPos, newPos));
        }
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if(canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(),animal);
            mapChanged("Dodano zwierzę na pozycji: "+animal.getPosition());
            return;
        }
        throw new IncorrectPositionException(animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position != null) && (!animals.containsKey(position));
    }

    @Override
    public List<WorldElement> getElements(){
        return new ArrayList<>(animals.values());
    }

    @Override
    public abstract Boundary getCurrentBounds();

    @Override
    public String toString(){
        Boundary bound = getCurrentBounds();
        return visualizer.draw(bound.lowerLeft(),bound.upperRight());
    }
}
