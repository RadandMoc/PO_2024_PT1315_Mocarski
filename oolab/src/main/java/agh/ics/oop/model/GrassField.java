package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        super();
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
    public Optional<WorldElement> objectAt(Vector2d position) {
        return super.objectAt(position).or(() -> Optional.ofNullable(grass.get(position)));
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
        for(Vector2d item : animals.keySet()){
            if(lowLeft != null){
                upRight = upRight.upperRight(item);
                lowLeft = lowLeft.lowerLeft(item);
            }
            else{
                lowLeft = item;
                upRight = item;
            }
        }
        return new Boundary(lowLeft,upRight);
    }

    @Override
    public List<Animal> getOrderedAnimals() {
        return animals.values().stream()
                .sorted(Comparator.comparing((Animal a) -> a.getPosition().getX())
                        .thenComparing(a -> a.getPosition().getY()))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorldElement> getElements() {
        return Stream.concat(super.getElements().stream(), grass.values().stream())
                .collect(Collectors.toList());
    }
}
