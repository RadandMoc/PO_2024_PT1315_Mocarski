package agh.ics.oop.model;

import java.util.*;

public abstract class AbstractWorldMap
{
    protected final HashMap<Vector2d, HashSet<Animal>> animals = new HashMap<>();
    protected final HashMap<Vector2d, Plant> plants = new HashMap<>();
    private final List<MapChangeListener> observers = new ArrayList<>();
    protected final int height;
    protected final int width;
    protected final Boundary boundary;

    public AbstractWorldMap(int width, int height){
        this(width, height, new Vector2d(0,0));
    }

    public AbstractWorldMap(int width, int height, Vector2d leftDownBoundary){
        this.width = width;
        this.height = height;
        Vector2d rightUpBoundary = new Vector2d(leftDownBoundary.getX() + width - 1, leftDownBoundary.getY() + height - 1);
        boundary = new Boundary(leftDownBoundary,rightUpBoundary);
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

    public void move(Animal animal){
        Vector2d prevPos = animal.getPosition();
        animal.move(this);
        Vector2d currPos = animal.getPosition();
        if (!prevPos.equals(currPos)){
            animals.get(prevPos).remove(animal);
            place(animal);
        }
    }

    public void movesAllAnimals() {
        animals.entrySet().stream()
                .forEach(entry -> {
                    HashSet<Animal> animalsInSquare = entry.getValue();
                    animalsInSquare.forEach(this::move);
                });
        //pewnie przydałoby się jakies wywolanie listenera
    }

    public void clearDeathAnimal(EnergyLoss energyLoss) {
        animals.entrySet().forEach(entry -> {
            HashSet<Animal> animalsInSquare = entry.getValue();
            animalsInSquare.removeIf(animal ->
                    !animal.ableToWalk(energyLoss.howManyEnergyToWalk(animal))
            );
        });
        // pewnie przydalby się update GUI
    }

    public abstract void generatePlants(int startedPlants);

    public abstract MoveResult animalMoveChanges(Vector2d animalPosition, MapDirection orientation);
}
