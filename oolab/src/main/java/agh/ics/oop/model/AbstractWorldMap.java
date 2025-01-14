package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;
import javafx.scene.control.skin.TextInputControlSkin;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();
    private final int mapId;
    private static int counter = 0;

    protected AbstractWorldMap(){
        synchronized (AbstractWorldMap.class){
            mapId = ++counter;
        }
    }

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
        final var prevDirection = animal.getOrientation();
        animal.move(direction, this);
        final Vector2d newPos = animal.getPosition();
        final var newDirection = animal.getOrientation();
        if (!prevPos.equals(newPos)){
            animals.put(newPos, animal);
            animals.remove(prevPos);
            mapChanged("Poruszono zwierzę z pozycji %s na pozycję %s ".formatted(prevPos, newPos));
        }
        else if(newDirection != prevDirection)
            mapChanged("Zwierzę na pozycji %s obróciło się w stronę %s".formatted(prevPos, newDirection));
        else
            mapChanged("Zwierzę na pozycji %s jest zablokowane".formatted(prevPos));
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
    public Optional<WorldElement> objectAt(Vector2d position) {
        return Optional.ofNullable(animals.get(position));
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
    public int getId(){
        return mapId;
    }

    @Override
    public String toString(){
        Boundary bound = getCurrentBounds();
        return visualizer.draw(bound.lowerLeft(),bound.upperRight());
    }

    @Override
    public List<Animal> getOrderedAnimals() {
        return animals.values().stream()
                .sorted(Comparator.comparing((Animal a) -> a.getPosition().getX())
                        .thenComparing(a -> a.getPosition().getY()))
                .collect(Collectors.toList());
    }

    /*@Override
    public List<Animal> getOrderedAnimals() {
        List<Animal> sortedAnimals = new ArrayList<>(animals.values());
        sortedAnimals.sort(Comparator.comparing((Animal a) -> a.getPosition().getX())
                .thenComparing(a -> a.getPosition().getY()));
        return sortedAnimals;
    }*/
}
