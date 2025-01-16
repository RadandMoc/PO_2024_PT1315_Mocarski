package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    public Animal(Vector2d position, int energy, int turnOfBirth, MutateGenome mutateMethod, List<Byte> parentsGenome){
        this.turnOfBirth = turnOfBirth;
        this.energy = energy;
        this.position = position;
        this.genome = mutateMethod.mutate(parentsGenome);
        this.orientation = MapDirection.generateRandomDirection();
        this.genomeIdx = new Random().nextInt(genome.size());
    }

    public Animal(Vector2d position, int energy, int turnOfBirth, List<Byte> genome){
        this.turnOfBirth = turnOfBirth;
        this.energy = energy;
        this.position = position;
        this.genome = genome;
        this.orientation = MapDirection.generateRandomDirection();
        this.genomeIdx = new Random().nextInt(genome.size());

    }

    public void move(AbstractWorldMap map){
        orientation = orientation.change(genome.get(genomeIdx % genome.size()));
        MoveResult consequences = map.animalMoveChanges(this);
        position = consequences.position();
        orientation = consequences.orientation();
        this.changeEnergy(-consequences.energy());
    }

    public boolean ableToWalk(int requiredEnergy){
        if (energy - requiredEnergy < 0){
            isDead = true;
            return false;
        }
        turnOfAnimal++;
        genomeIdx++;
        return true;
    }

    public void changeEnergy(int energy){
        this.energy += energy;
    }


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

    public void addChild(Animal animal){
        childs.add(animal);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public List<Byte> getPartOfGen(double percent, boolean fromRight){

        int idx = (int) (percent * genome.size());
        List<Byte> genomeSlice = new ArrayList<>();

        if (fromRight){
            for (int i = genome.size() - 1; i > genome.size() - 1 - idx; --i){
                genomeSlice.add(genome.get(i));
            }
            return genomeSlice;
        }

        for (int i = 0; i < idx; ++i){
            genomeSlice.add(genome.get(i));
        }
        return genomeSlice;
    }
}
