package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement{
    private MapDirection orientation = MapDirection.N; // Nie wiemy, czy ma być zawsze domyślnie na północ
    private Vector2d position;
    private int energy; // Być może statyczna ilość energii byłaby lepsza. Chwilowo dajemy możliwość symulacji wyboru ile energii powinny mieć zwierzęta domyślnie (zakładamy że tam będzie stała)
    private final List<Byte> genome; // Może enum zamiast Byte. Trochę średnio pasuje MapDirection, bo brak obrotu ma opis północ, co nie jest zgodne. Jeśli enum to raczej jakiś nowy (chyba).
    private int turnOfAnimal = 0; // Num of turns of animal when was/is alive
    private final int turnOfBirth;
    private boolean isDead = false;
    private final List<Animal> childs = new ArrayList<>();
    private int eatenGrass = 0;

    public Animal(Vector2d position, int energy, int turnOfBirth,MutateGenome mutateMethod, List<Byte> parentsGenome){
        this.turnOfBirth = turnOfBirth;
        this.energy = energy;
        this.position = position;
        this.genome = mutateMethod.Mutate(parentsGenome);
    }

    public void move(AbstractWorldMap map){
        orientation.change(genome.get(turnOfAnimal%genome.size()));
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
        return true;
    }

    public void changeEnergy(int energy){
        this.energy += energy;
    }

    public int selectComponent(ConsumeStatComponent type){
        return switch (type) {
            case ENERGY -> energy;
            case LIFETIME -> turnOfAnimal;
            case NUMBER_OF_CHILDREN -> childs.size();
        };
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
