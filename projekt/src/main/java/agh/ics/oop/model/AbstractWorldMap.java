package agh.ics.oop.model;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap
{
    protected final HashMap<Vector2d, HashSet<Animal>> animals = new HashMap<>();
    protected final HashMap<Vector2d, Plant> plants = new HashMap<>();
    private final List<MapChangeListener> observers = new ArrayList<>();
    protected final int height;
    protected final int width;
    protected final Boundary boundary;
    private final int energyFromPlant;

    public AbstractWorldMap(int width, int height, int plantEnergy){
        this(width, height, new Vector2d(0,0), plantEnergy);
    }

    public AbstractWorldMap(int width, int height, Vector2d leftDownBoundary, int plantEnergy){
        this.width = width;
        this.height = height;
        Vector2d rightUpBoundary = new Vector2d(leftDownBoundary.getX() + width - 1, leftDownBoundary.getY() + height - 1);
        boundary = new Boundary(leftDownBoundary,rightUpBoundary);
        energyFromPlant = plantEnergy;
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
        animals.forEach((key, animalsInSquare) -> animalsInSquare.forEach(this::move));
        //pewnie przydałoby się jakies wywolanie listenera
    }

    public void clearDeathAnimal(EnergyLoss energyLoss) {
        animals.forEach((key, animalsInSquare) -> animalsInSquare.removeIf(animal ->
                !animal.ableToWalk(energyLoss.howManyEnergyToWalk(animal))
        ));
        // pewnie przydalby się update GUI
    }

    public abstract void generatePlants(int startedPlants);

    public abstract MoveResult animalMoveChanges(Vector2d animalPosition, MapDirection orientation);

    public void animalsConsume(StrongestAnimalFinder strongestAnimalFinder){
        for (var pos : animals.keySet()){
            if (plants.containsKey(pos) && !animals.get(pos).isEmpty()){
                Animal winner = strongestAnimalFinder.findStrongestAnimal(animals.get(pos));
                winner.changeEnergy(energyFromPlant);
                plants.remove(pos);
                // trzeba pewnie losowacza ogarnac
                // aktualizacja gui
            }

        }
    }


    public void breeding(int energyForAnimalsForBreeding, int startsEnergy, int actualTurn, MutateGenome mutateMethod, ReproductionStrategy repr){
        for (var pos : animals.keySet()){
            var animalsReadyToBreeding = animals.get(pos).stream().
                    filter(animal -> animal.getEnergy() >= energyForAnimalsForBreeding).toList();

            var genomeList = repr.reproduce(animalsReadyToBreeding, energyForAnimalsForBreeding);
            for (var genome : genomeList){
                place(new Animal(pos,startsEnergy, actualTurn, mutateMethod,genome));
            }
        }
    }


}
