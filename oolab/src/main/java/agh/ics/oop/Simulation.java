package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Simulation {
    private final List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moves = new ArrayList<>();
    // Stosuję ArrayList, ponieważ przewiduję, że metody te będą głównie odczytywane.
    // Zmiany będą dokonywane przede wszystkim na obiektach, a referencje nie będą się zmieniać
    // A skoro przechowywane wartości są często odczytywane i rzadko zmieniane, ArrayList będzie najlepsze

    public Simulation(List<Vector2d> animalsPositions, List<MoveDirection> moves){
        List<Animal> animalsToAdd = new ArrayList<>();
        for (Vector2d position : animalsPositions){
            animalsToAdd.add(new Animal(position));
        }
        setAnimals(animalsToAdd);
        setMoves(moves);
    }

    protected List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    // setAnimals musi być public, bo inaczej test krzyczy
    public void setAnimals(List<Animal> animals) {
        if(animals != null) {
            this.animals.clear();
            this.animals.addAll(animals);
        }
    }

    protected List<MoveDirection> getMoves() {
        return new ArrayList<>(moves);
    }

    protected void setMoves(List<MoveDirection> moves) {
        if(moves != null)
            this.moves = moves;
    }

    public void run(){
        List<Animal> localAnimals = animals;
        int numOfAnimals = localAnimals.size();
        int lastAnimalIdx = 0;
        Animal animal;
        for(MoveDirection move : getMoves()){
            animal = localAnimals.get(lastAnimalIdx);
            animal.move(move);
            System.out.printf("%d: %s%n",lastAnimalIdx,animal);
            lastAnimalIdx = (lastAnimalIdx+1)%numOfAnimals;
        }
        setMoves(new ArrayList<>());
    }

    @Override
    public boolean equals(Object other){
        if(this==other) return true;
        if (other == null || getClass() != other.getClass())
            return false;
        Simulation sim = (Simulation) other;
        return (animals.equals(sim.animals)) && getMoves().equals(sim.getMoves());
    }

    @Override
    public int hashCode(){
        return Objects.hash(animals,moves);
    }
}
