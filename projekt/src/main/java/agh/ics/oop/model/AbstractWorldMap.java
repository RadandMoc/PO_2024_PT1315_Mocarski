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
    protected final EnergyLoss energyLoss;
    private final int energyFromPlant;
    protected final RandomPositionGenerator equatorPlantGenerator;
    protected final RandomPositionGenerator polesPlantGenerator;
    protected final Boundary equator;

    public AbstractWorldMap(int width, int height, int plantEnergy, EnergyLoss energyLoss,int startNumOfPlants){
        this(width, height, new Vector2d(0,0), plantEnergy, energyLoss,startNumOfPlants);
    }

    public AbstractWorldMap(int width, int height, Vector2d leftDownBoundary, int plantEnergy, EnergyLoss energyLoss, int startNumOfPlants){
        this.width = width;
        this.height = height;
        Vector2d rightUpBoundary = new Vector2d(leftDownBoundary.getX() + width - 1, leftDownBoundary.getY() + height - 1);
        boundary = new Boundary(leftDownBoundary,rightUpBoundary);
        energyFromPlant = plantEnergy;
        this.energyLoss = energyLoss;
        int maxHeight = leftDownBoundary.getY()+height-1;
        polesPlantGenerator = new RandomPositionGenerator(boundary,0);
        Vector2d equatorLowerLeft = new Vector2d(leftDownBoundary.getX(), (int)((maxHeight)*2/5)+1);
        Vector2d equatorUpperRight = new Vector2d(leftDownBoundary.getX()+width-1,(int)((maxHeight)*3/5));
        equator = new Boundary(equatorLowerLeft,equatorUpperRight);
        equatorPlantGenerator = new RandomPositionGenerator(equator,0);
        polesPlantGenerator.deleteRectangle(equator);
        generatePlants(startNumOfPlants);
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
        List<Animal> animalList = animals.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.toList());

        animalList.forEach(this::move);

        //Wywołanie listenera po zakończeniu ruchów
    }

    public void clearDeathAnimal() {
        animals.forEach((key, animalsInSquare) -> animalsInSquare.removeIf(animal ->
                !animal.ableToWalk(energyLoss.howManyEnergyToWalk(animal))
        ));
        // pewnie przydalby się update GUI
    }

    public abstract void generatePlants(int numOfPlants);

    public abstract MoveResult animalMoveChanges(Animal animal);

    public void animalsConsume(StrongestAnimalFinder strongestAnimalFinder){
        for (var pos : animals.keySet()){
            if (plants.containsKey(pos) && !animals.get(pos).isEmpty()){
                Animal winner = strongestAnimalFinder.findStrongestAnimal(animals.get(pos));
                winner.changeEnergy(energyFromPlant);
                plants.remove(pos);
                if (equator.isVectorIn(pos))
                    equatorPlantGenerator.acceptPositionToChoice(pos);
                else
                    polesPlantGenerator.acceptPositionToChoice(pos);
                // aktualizacja gui
            }

        }
    }

    /* wywalić do symulacji? albo zwierzęcia jako static?*/
    public void breeding(int energyForAnimalsForBreeding, int breedingLoss, int actualTurn, MutateGenome mutateMethod, ReproductionStrategy repr){
        for (var pos : animals.keySet()){
            var animalsReadyToBreeding = animals.get(pos).stream().
                    filter(animal -> animal.getEnergy() >= energyForAnimalsForBreeding).toList();

            var reproductionResults = repr.reproduce(animalsReadyToBreeding, breedingLoss);

            for (var result : reproductionResults){
                Animal child = new Animal(pos, breedingLoss * 2, actualTurn, mutateMethod, result.genome());
                place(child);
                for (var parent : result.parents()){
                    parent.addChild(child);
                }
            }
        }
    }

    public void generateRandomAnimals(int numOfAnimals,int genomeLength,int startEnergy){
        Random random = new Random();
        int x;
        int y;
        for (int i = 0; i < numOfAnimals; i++) {
            x = random.nextInt(width) + boundary.lowerLeft().getX();
            y = random.nextInt(height) + boundary.lowerLeft().getY();
            List<Byte>genome = new ArrayList<>();
            for (int j = 0; j < genomeLength; j++) {
                genome.add((byte)random.nextInt(8));
            }
            Vector2d pos = new Vector2d(x,y);
            Animal animal = new Animal(pos,startEnergy,0,genome);
            this.place(animal);
        }
    }
}
