package agh.ics.oop.model;

import java.util.Objects;

public class Animal {
    private MapDirection orientation;
    private Vector2d localization;

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
        if(newLocalization != null)
            localization = newLocalization;
    }

    public Animal(Vector2d localization){
        this.localization = localization;
        orientation = MapDirection.NORTH;
    }

    public Animal(){
        this(new Vector2d(2,2));
    }

    @Override
    public String toString() {
        return switch (this.orientation){
            case NORTH -> "^";
            case SOUTH -> "v";
            case WEST -> "<";
            case EAST -> ">";
        };
    }

    protected boolean isAt(Vector2d position){
        return localization.equals(position);
    }

    public void move(MoveDirection direction, MoveValidator validator){
        Vector2d potentialMove = null;
        switch (direction){
            case MoveDirection.RIGHT -> setOrientation(orientation.next());
            case MoveDirection.LEFT -> setOrientation(orientation.previous());
            case MoveDirection.BACKWARD -> potentialMove = localization.subtract(orientation.toUnitVector());
            case MoveDirection.FORWARD -> potentialMove = localization.add(orientation.toUnitVector());
        }
        if(validator.canMoveTo(potentialMove))
            setLocalization(potentialMove);
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
