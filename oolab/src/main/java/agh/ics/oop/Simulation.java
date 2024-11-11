package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Simulation<T, P> {
    private final List<T> objects = new ArrayList<>();
    private final List<MoveDirection> moves;
    private final WorldMap<T, P> map;

    public Simulation(List<T> listOfObjects, List<MoveDirection> moves, WorldMap<T, P> map){
        this.moves = moves;
        this.map = map;

        for (T obj: listOfObjects){
            if (map.place(obj))
                objects.add(obj);
        }
    }

    // setAnimals musi być public, bo inaczej test krzyczy
    public void setObjects(List<T> objects) {
        if(objects != null) {
            this.objects.clear();
            this.objects.addAll(objects);
        }
    }

    protected List<MoveDirection> getMoves() {
        return new ArrayList<>(moves);
    }

    public void run(){
        List<T> localObjects = objects;
        int numOfObj = localObjects.size();
        int lastAnimalIdx = 0;
        T animal;
        for(MoveDirection move: getMoves()){
            animal = localObjects.get(lastAnimalIdx);
            map.move(animal,move);
            System.out.println(map);
            lastAnimalIdx = (lastAnimalIdx+1)%numOfObj;
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
