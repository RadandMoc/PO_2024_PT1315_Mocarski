package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Simulation {
    private final List<Animal> animals = new ArrayList<>();
    private final List<MoveDirection> moves = new ArrayList<>();
    private final WorldMap map = new RectangularMap(5,5);

    public Simulation(List<Vector2d> animalsPositions, List<MoveDirection> moves){
        List<Animal> animalsToAdd = new ArrayList<>();
        for (Vector2d position : animalsPositions){
            Animal pet = new Animal(position);
            animalsToAdd.add(pet);
            map.place(pet);
        }
        setAnimals(animalsToAdd);
        this.moves.addAll(moves);
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

    public void run(){
        List<Animal> localAnimals = animals;
        int numOfAnimals = localAnimals.size();
        int lastAnimalIdx = 0;
        Animal animal;
        for(MoveDirection move: getMoves()){
            animal = localAnimals.get(lastAnimalIdx);
            map.move(animal,move);
            System.out.println(map);
            lastAnimalIdx = (lastAnimalIdx+1)%numOfAnimals;
        }
        this.moves.clear();
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
