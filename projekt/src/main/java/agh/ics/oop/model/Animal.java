package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Animal implements WorldElement{
    private MapDirection orientation; // Nie wiemy, czy ma być zawsze domyślnie na północ
    private Vector2d position;
    private int energy; // Być może statyczna ilość energii byłaby lepsza. Chwilowo dajemy możliwość symulacji wyboru ile energii powinny mieć zwierzęta domyślnie (zakładamy że tam będzie stała)
    private final List<Byte> genome; // Może enum zamiast Byte. Trochę średnio pasuje MapDirection, bo brak obrotu ma opis północ, co nie jest zgodne. Jeśli enum to raczej jakiś nowy (chyba).
    private int turnOfAnimal = 0; // Num of turns of animal when was/is alive
    private int genomeIdx;
    private final int turnOfBirth;
    private boolean isDead = false;
    private final List<Animal> childs = new ArrayList<>();
    private int eatenGrass = 0;

    public Animal(Vector2d position, int energy, int turnOfBirth, MutateGenome mutateMethod, List<Byte> parentsGenome, AnimalGenomesPopularityCalculator genomesListener){
        this.turnOfBirth = turnOfBirth;
        this.energy = energy;
        this.position = position;
        this.genome = mutateMethod.mutate(parentsGenome);
        this.orientation = MapDirection.generateRandomDirection();
        this.genomeIdx = new Random().nextInt(genome.size());
        if(genomesListener != null)
            genomesListener.newAnimal(this);
    }

    public Animal(Vector2d position, int energy, int turnOfBirth, List<Byte> genome, MapDirection orientation, int genomeIdx, AnimalGenomesPopularityCalculator genomesListener){
        this.turnOfBirth = turnOfBirth;
        this.energy = energy;
        this.position = position;
        this.genome = genome;
        this.orientation = orientation;
        this.genomeIdx = genomeIdx;
        if(genomesListener != null)
            genomesListener.newAnimal(this);
    }
    public Animal(Vector2d position, int energy, int turnOfBirth, List<Byte> genome, AnimalGenomesPopularityCalculator genomesListener){
        this(position,energy,turnOfBirth,genome,MapDirection.generateRandomDirection(),new Random().nextInt(genome.size()),genomesListener);
    }

    public void move(AbstractWorldMap map){
        orientation = orientation.change(genome.get(genomeIdx % genome.size()));
        MoveResult consequences = map.animalMoveChanges(this);
        position = consequences.position();
        orientation = consequences.orientation();
        this.changeEnergy(-consequences.energy());
    }

    public boolean ableToWalk(int requiredEnergy, AnimalGenomesPopularityCalculator genomesListener){
        if (energy - requiredEnergy < 0){
            isDead = true;
            if(genomesListener != null)
                genomesListener.deleteAnimal(this);
            return false;
        }
        turnOfAnimal++;
        genomeIdx++;
        return true;
    }

    public void changeEnergy(int energy){
        this.energy += energy;
    }

    public void eatGrass(int energy){
        this.energy += energy;
        eatenGrass++;
    }

    public boolean getIsDead() {return isDead; }

    public int getEnergy() {
        return energy;
    }

    public int getLifeTime(){
        return turnOfAnimal;
    }

    public int getNumOfChild(){
        return childs.size();
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public int getSizeOfGenome() {return this.genome.size();}

    public void addChild(Animal animal){
        childs.add(animal);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void updateWorldElementBox(WorldElementBox fieldBox) {
        fieldBox.UpdateForAnimal(energy);
    }

    public List<Byte> getPartOfGen(int pointOfSlice, boolean fromRight){
        List<Byte> genomeSlice = new ArrayList<>();

        if (fromRight){
            for (int i = genome.size() - 1; i > genome.size() - 1 - pointOfSlice; --i){
                genomeSlice.add(genome.get(i));
            }
            return genomeSlice.reversed();
        }

        for (int i = 0; i < pointOfSlice; ++i){
            genomeSlice.add(genome.get(i));
        }
        return genomeSlice;
    }

    public int getEatenGrass() {
        return eatenGrass;
    }

    public int getNumOfChildrens(){
        return childs.size();
    }

    public int getGenomeIdx() {
        return genomeIdx % genome.size();
    }

    public int getTurnOfDeath() {
        return turnOfAnimal + turnOfBirth;
    }


    public String getGenome(){
        return genome.stream().map("%d"::formatted).collect(Collectors.joining());
    }

    public List<Animal> getAllDescendants(){
        List<Animal> descendants = new ArrayList<>();
        for (Animal child : childs){
            descendants.add(child);
            descendants.addAll(child.getAllDescendants());
        }
        return descendants;
    }

    @Override
    public String toString() {
        return "(%d,%d) with energy %d".formatted(position.getX(),position.getY(),energy);
    }
}
