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

    private void move(Animal animal){
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

    public void animalsConsume(int energy){
        for (var pos : animals.keySet()){
            if (plants.containsKey(pos)){
                Animal winner = bestByEnergy(animals.get(pos));
                winner.changeEnergy(energy);
                plants.remove(pos);
                // trzeba pewnie losowacza ogarnac
                // aktualizacja gui
            }

        }
    }

    private Set<Animal> bestComponent(Set<Animal> animalsOnField, ConsumeStatComponent choice){
        int bestComponent = Integer.MIN_VALUE;
        Set<Animal> bestAnimals = new HashSet<>();

        for (Animal animal : animalsOnField) {
            int component = animal.selectComponent(choice);
            if (component > bestComponent) {
                bestComponent = component;
                bestAnimals.clear();
                bestAnimals.add(animal);
            } else if (component == bestComponent) {
                bestAnimals.add(animal);
            }
        }

        return bestAnimals;
    }

    private Animal bestByEnergy(Set<Animal> animalsOnField){
        var bestAnimals = bestComponent(animalsOnField, ConsumeStatComponent.ENERGY);
        if (bestAnimals.size() == 1) {return bestAnimals.iterator().next();}
        return bestByLifetime(bestAnimals);
    }

    private Animal bestByLifetime(Set<Animal> animalsOnField){
        var bestAnimals = bestComponent(animalsOnField, ConsumeStatComponent.LIFETIME);
        if (bestAnimals.size() == 1) {return bestAnimals.iterator().next();}
        return bestByNumOfChild(bestAnimals);
    }

    private Animal bestByNumOfChild(Set<Animal> animalsOnField){
        var bestAnimals = bestComponent(animalsOnField, ConsumeStatComponent.NUMBER_OF_CHILDREN);
        if (bestAnimals.size() == 1) {return bestAnimals.iterator().next();}

        List<Animal> list = new ArrayList<>(bestAnimals);
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
