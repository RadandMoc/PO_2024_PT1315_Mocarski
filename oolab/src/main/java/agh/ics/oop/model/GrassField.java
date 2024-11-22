package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final Random random;
    /*  Zastanów się, jakie problemy wygenerowałaby tutaj jedna wspólna kolekcja.
    * słownik byłby problematyczny, bo trawa i zwierzę mogą być na tej samej pozycji, a słownik by to mocno utrudnił (nie mogą być dwa obiekty pod tymi samymi kluczami)
    * z kolei słownik jest tu najwygodniejszy, gdyż szybko dostaje się do wartości w danej lokalizacji (foreacha nie trzeba tyle stosować)  */

    public GrassField(int grassNo){
        this(grassNo,new Random().nextInt());
    }

    public GrassField(int grassNo, int randomSeed){
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
    public String toString(){
        // można by też to rozwiązać w ten sposób, że są prywatne pola z lewym dołem i prawą górą, ALE
        // każdy ruch zwierzęcia musiałby sprawdzać pozycję przed ruchem i sprawdzić czy jest na obrzeżach
        // jeśli tak, to w zależności od ruchu jaki by wykonał byłyby następujące opcje ruchu:
        // 1. jeśli poszedł by rozszerzając mapę, powinno być zaktualizowane (powiększone parametry)
        // 2. jeśli do wewnątrz obwodu należy zaktualizować całą listę jakiąś metodą czołgową.
        // symulacja nie musi printować mapy co ruch, więc żeby nie pogorszyć programu potrzebny byłby bool
        // do zapisania informacji, czy tostring musi mieć aktualizację.
        // a więc to jest skomplikowane i niewiele zmieni.
        // a do trawy nie wiem czy nie będzie coś jej żarło i nie będzie znikać, więc też mi się nie chciało
        Vector2d lowLeft = null;
        Vector2d upRight = null;
        Vector2d key;
        for(Map.Entry<Vector2d, Grass> item : grass.entrySet()){
            if(lowLeft != null){
                key = item.getKey();
                upRight = upRight.upperRight(key);
                lowLeft = lowLeft.lowerLeft(key);
            }
            else{
                lowLeft = item.getKey();
                upRight = lowLeft;
            }
        }
        for(Map.Entry<Vector2d, Animal> item : animals.entrySet()){
            if(lowLeft != null){
                key = item.getKey();
                upRight = upRight.upperRight(key);
                lowLeft = lowLeft.lowerLeft(key);
            }
            else{
                lowLeft = item.getKey();
                upRight = lowLeft;
            }
        }
        return visualizer.draw(lowLeft,upRight);
    }

    @Override
    public List<WorldElement> getElements(){
        List<WorldElement>result = super.getElements();
        result.addAll(grass.values());
        return result;
    }
}
