package agh.ics.oop.model;

import java.util.Objects;

public class Animal {
    private MapDirection orientation;
    private Vector2d localization;
    private GameMap map; // Już tłumaczę dlaczego tu, a nie np w simulation. Może się okazać,
    // że każde zwierze ma inaczej ograniczony obszar (np. jest w oborze na obszarze od 5,5 do 7,7,
    // lub jest w kurniku 2,2 do 4,3 itp.) Chciałem dać nazwę klasy map, ale ta jest zarezerwowana dla klasy javowej.

    public MapDirection getOrientation() {
        return orientation;
    }

    protected void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }

    public Vector2d getLocalization(){
        return localization;
    }

    protected void setLocalization(Vector2d newLocalization){
        if(map.isPointInMap(newLocalization)){
            localization = newLocalization;
        }
    }

    public Animal(Vector2d localization){
        this.localization = localization;
        orientation = MapDirection.NORTH;
        map = new GameMap();
    }

    public Animal(){
        this(new Vector2d(2,2));
    }

    @Override
    public String toString() {
        return String.format("Zwierzę jest na pozycji %s i zwrócone na %s",localization.toString(),orientation.toString());
    }

    protected boolean isAt(Vector2d position){
        return localization.equals(position);
    }

    public void move(MoveDirection direction){
        switch (direction){
            case MoveDirection.RIGHT -> setOrientation(orientation.next());
            case MoveDirection.LEFT -> setOrientation(orientation.previous());
            case MoveDirection.BACKWARD -> setLocalization(localization.subtract(orientation.toUnitVector()));
            case MoveDirection.FORWARD -> setLocalization(localization.add(orientation.toUnitVector()));
        }
    }

    @Override
    public boolean equals(Object other){
        if(this==other) return true;
        if (other == null || getClass() != other.getClass())
            return false;
        Animal animal = (Animal) other;
        return (orientation.toUnitVector().equals(animal.orientation.toUnitVector()) && localization.equals(((Animal) other).localization));
    }

    @Override
    public int hashCode(){
        return Objects.hash(orientation,localization);
    }
}
