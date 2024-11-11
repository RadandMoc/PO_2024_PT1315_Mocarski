package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Simulation<T, P> {
    private final List<T> objects = new ArrayList<>();
    private final List<MoveDirection> moves = new ArrayList<>();
    private final WorldMap<T, P> map;

    public Simulation(WorldMap<T, P> map, List<P> initialPositions, List<MoveDirection> moves){
        List<Animal> animalsToAdd = new ArrayList<>();
        for (Vector2d position : initialPositions){
            Animal pet = new Animal(position);
            if(map.place(pet))
                animalsToAdd.add(pet);
        }
        setObjects(animalsToAdd);
        this.moves.addAll(moves);
    }

    protected List<Animal> getObjects() {
        return new ArrayList<>(objects);
    }

    // setAnimals musi być public, bo inaczej test krzyczy
    public void setObjects(List<Animal> objects) {
        if(objects != null) {
            this.objects.clear();
            this.objects.addAll(objects);
        }
    }

    protected List<MoveDirection> getMoves() {
        return new ArrayList<>(moves);
    }

    public void run(){
        List<Animal> localAnimals = objects;
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
        return (objects.equals(sim.objects)) && getMoves().equals(sim.getMoves());
    }

    @Override
    public int hashCode(){
        return Objects.hash(objects,moves);
    }
}
