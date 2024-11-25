package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final Random random;
    /*  Zastanów się, jakie problemy wygenerowałaby tutaj jedna wspólna kolekcja.
    * słownik byłby problematyczny, bo trawa i zwierzę mogą być na tej samej pozycji, a słownik by to mocno utrudnił (nie mogą być dwa obiekty pod tymi samymi kluczami)
    * z kolei słownik jest tu najwygodniejszy, gdyż szybko dostaje się do wartości w danej lokalizacji (foreacha nie trzeba tyle stosować)  */

    public GrassField(int grassNo){
        this(grassNo,new Random().nextInt());
    }

    GrassField(int grassNo, int randomSeed){
        random = new Random(randomSeed);
        int limit = (int) Math.floor(Math.sqrt(grassNo * 10)+1);
        RandomPositionGenerator generator = new RandomPositionGenerator(limit, limit, grassNo, randomSeed);

        for (Vector2d position : generator) {
            grass.put(position, new Grass(position));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grass.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement elem = super.objectAt(position);
        if(elem==null)
            elem = grass.get(position);
        return elem;
    }

    @Override
    public Boundary getCurrentBounds(){
        Vector2d lowLeft = null;
        Vector2d upRight = null;
        for(Vector2d item : grass.keySet()){
            if(lowLeft != null){
                upRight = upRight.upperRight(item);
                lowLeft = lowLeft.lowerLeft(item);
            }
            else{
                lowLeft = item;
                upRight = item;
            }
        }
        Boundary bound = super.getCurrentBounds();
        lowLeft = lowLeft.lowerLeft(bound.lowerLeft());
        upRight = upRight.upperRight(bound.upperRight());
        return new Boundary(lowLeft,upRight);
    }

    @Override
    public List<WorldElement> getElements(){
        List<WorldElement>result = super.getElements();
        result.addAll(grass.values());
        return result;
    }
}
