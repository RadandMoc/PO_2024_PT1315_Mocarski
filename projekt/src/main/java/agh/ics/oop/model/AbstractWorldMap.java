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
    protected final EnergyLoss energyLoss;
    private final int energyFromPlant;
    protected final RandomPositionGenerator equatorPlantGenerator;
    protected final RandomPositionGenerator polesPlantGenerator;
    protected final Boundary equator;
    private final AnimalGenomesPopularityCalculator genomesListener = new AnimalGenomesPopularityCalculator();
    private final Comparator<Animal> animalComparator = new AnimalConflictComparator();

    public AbstractWorldMap(int width, int height, Vector2d leftDownBoundary, int plantEnergy, EnergyLoss energyLoss, int startNumOfPlants){
        this.width = width;
        this.height = height;
        Vector2d rightUpBoundary = new Vector2d(leftDownBoundary.x() + width - 1, leftDownBoundary.y() + height - 1);
        boundary = new Boundary(leftDownBoundary,rightUpBoundary);
        energyFromPlant = plantEnergy;
        this.energyLoss = energyLoss;
        polesPlantGenerator = new RandomPositionGenerator(boundary,0);
        equator = calculateEquator(leftDownBoundary, height, width);
        equatorPlantGenerator = new RandomPositionGenerator(equator,0);
        polesPlantGenerator.deleteRectangle(equator);
        generatePlants(startNumOfPlants);
    }

    public boolean isOccupied(Vector2d position){
        synchronized (animals) {
            synchronized (plants) {
                return animals.containsKey(position) || plants.containsKey(position);
            }
        }
    }

    public Optional<WorldElement> objectAt(Vector2d position){
        synchronized (animals){
            if (animals.containsKey(position) && !animals.get(position).isEmpty()) {
                return animals.get(position).stream()
                        .min(animalComparator)
                        .map(WorldElement.class::cast);
            }
        }

        synchronized (plants) {
            if (plants.containsKey(position)) {
                return Optional.of(plants.get(position));
            }
        }

        return Optional.empty();
    }

    public void place(WorldElement mapObj){
        if(mapObj instanceof Plant plant){
            plants.put(plant.position(),plant);
            if(equator.isVectorIn(plant.position()))
                equatorPlantGenerator.deletePositionToChoice(plant.position());
            else
                polesPlantGenerator.deletePositionToChoice(plant.position());
        } else if (mapObj instanceof Animal animal) {
            if(!animals.containsKey(animal.position()))
                animals.put(animal.position(),new HashSet<>());
            animals.get(animal.position()).add(animal);
        }
    }

    private void move(Animal animal){
        Vector2d prevPos = animal.position();
        animal.move(this);
        Vector2d currPos = animal.position();
        if (!prevPos.equals(currPos)){
            animals.get(prevPos).remove(animal);
            place(animal);
        }
    }

    public void movesAllAnimals() {
        synchronized (animals) {
            List<Animal> animalList = animals.values().stream()
                    .flatMap(Set::stream)
                    .toList();

            animalList.forEach(this::move);
        }
        mapChanged("Ruszono zwierzakami");
    }

    public void clearDeathAnimal() {
        synchronized (animals) {
            animals.forEach((key, animalsInSquare) -> animalsInSquare.removeIf(animal ->
                    !animal.ableToWalk(energyLoss.howManyEnergyToWalk(animal), genomesListener)
            ));
        }
        mapChanged("Zabijam zwierzaki");
    }

    public abstract void generatePlants(int numOfPlants);

    public abstract MoveResult animalMoveChanges(Animal animal);

    public void animalsConsume(StrongestAnimalFinder strongestAnimalFinder){
        synchronized (animals){
            for (var pos : animals.keySet()) {
                synchronized (plants) {
                    if (plants.containsKey(pos) && !animals.get(pos).isEmpty()) {
                        Animal winner = strongestAnimalFinder.findStrongestAnimal(animals.get(pos));
                        winner.eatGrass(energyFromPlant);
                        plants.remove(pos);
                        if (equator.isVectorIn(pos))
                            equatorPlantGenerator.acceptPositionToChoice(pos);
                        else
                            polesPlantGenerator.acceptPositionToChoice(pos);
                    }
                }
            }
        }
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, "Zwierzaki zjadły rośliny");
        }
    }

    public void breeding(int energyForAnimalsForBreeding, int breedingLoss, int actualTurn, MutateGenome mutateMethod, ReproductionStrategy repr){
        synchronized (animals) {
            for (var pos : animals.keySet()){
                var animalsReadyToBreeding = animals.get(pos).stream().
                    filter(animal -> animal.getEnergy() >= energyForAnimalsForBreeding).toList();
                var reproductionResults = repr.reproduce(animalsReadyToBreeding, breedingLoss);

                for (var result : reproductionResults) {
                    Animal child = new Animal(pos, breedingLoss * 2, actualTurn, mutateMethod, result.genome(), genomesListener);
                    place(child);
                    for (var parent : result.parents()) {
                        parent.addChild(child);
                    }
                }
            }
        }
    }

    public HashSet<Animal> generateRandomAnimals(int numOfAnimals,int genomeLength,int startEnergy){
        Random random = new Random();
        int x;
        int y;
        HashSet<Animal> animalsSet = new HashSet<>();
        for (int i = 0; i < numOfAnimals; i++) {
            x = random.nextInt(width) + boundary.lowerLeft().x();
            y = random.nextInt(height) + boundary.lowerLeft().y();
            List<Byte>genome = new ArrayList<>();
            for (int j = 0; j < genomeLength; j++) {
                genome.add((byte)random.nextInt(8));
            }
            Vector2d pos = new Vector2d(x,y);
            Animal animal = new Animal(pos,startEnergy,0,genome,genomesListener);
            this.place(animal);
            animalsSet.add(animal);
        }

        return animalsSet;
    }

    public Boundary getBoundary(){
        return boundary;
    }

    protected void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    public static Boundary calculateEquator(Vector2d leftDownBoundary, int height, int width){
        int maxHeight = leftDownBoundary.y()+height-1;
        Vector2d equatorLowerLeft = new Vector2d(leftDownBoundary.x(), (maxHeight)*2/5);
        Vector2d equatorUpperRight = new Vector2d(leftDownBoundary.x()+width-1, (maxHeight)*3/5);
        return new Boundary(equatorLowerLeft,equatorUpperRight);
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    public String theMostPopularGenome(){
        return genomesListener.getMostPopularGenome();
    }

    public abstract Iterator<Vector2d> plantsPreferredZone();

    public List<Vector2d> getAnimalsPositionsWithGenome(String genome){
        return genomesListener.getAnimalsPositionsWithGenome(genome);
    }

    // very dangerous and stupid code. It may be dangerous for you. Please consult with your doctor or pharmacist
    public List<Animal> getAnimals(){
        List<Animal> result;
        synchronized (animals) {
            result = animals.values().stream().flatMap(Collection::stream).toList();
        }
        return result;
    }
}
