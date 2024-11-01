package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Simulation {
    public static final Vector2d upperRightMapPoint = new Vector2d(4,4);
    public static final Vector2d lowerLeftMapPoint = new Vector2d(0,0);
    private List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moves = new ArrayList<>();

    public Simulation(List<Vector2d> animalsPositions, List<MoveDirection> moves){
        List<Animal> animalsToAdd = new ArrayList<>();
        for (Vector2d position : animalsPositions){
            animalsToAdd.add(new Animal(position));
        }
        setAnimals(animalsToAdd);
        setMoves(moves);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        if(animals != null)
            this.animals = animals;
    }

    public List<MoveDirection> getMoves() {
        return moves;
    }

    protected void setMoves(List<MoveDirection> moves) {
        if(moves != null)
            this.moves = moves;
    }

    public void run(){
        List<Animal> localAnimals = getAnimals();
        int numOfAnimals = localAnimals.size();
        int lastAnimalIdx = 0;
        Animal animal;
        for(MoveDirection move : getMoves()){
            animal = localAnimals.get(lastAnimalIdx);
            animal.move(move);
            System.out.printf("%d: %s%n",lastAnimalIdx,animal.toString());
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
        return (getAnimals().equals(sim.getAnimals())) && getMoves().equals(sim.getMoves());
    }

    @Override
    public int hashCode(){
        return Objects.hash(animals,moves);
    }
}
