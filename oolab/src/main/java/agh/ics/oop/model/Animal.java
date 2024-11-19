package agh.ics.oop.model;

import java.util.Objects;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;

    public MapDirection getOrientation() {
        return orientation;
    }

    protected void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }

    @Override
    public Vector2d getPosition(){
        return position;
    }

    private void setPosition(Vector2d newPosition){
        if(newPosition != null)
            position = newPosition;
    }

    public Animal(Vector2d position){
        this.position = position;
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
        return this.position.equals(position);
    }

    public void move(MoveDirection direction, MoveValidator validator){
        Vector2d potentialMove = null;
        switch (direction){
            case MoveDirection.RIGHT -> setOrientation(orientation.next());
            case MoveDirection.LEFT -> setOrientation(orientation.previous());
            case MoveDirection.BACKWARD -> potentialMove = position.subtract(orientation.toUnitVector());
            case MoveDirection.FORWARD -> potentialMove = position.add(orientation.toUnitVector());
        }
        if(validator.canMoveTo(potentialMove))
            setPosition(potentialMove);
    }

    @Override
    public boolean equals(Object other){
        if(this==other) return true;
        if (other == null || getClass() != other.getClass())
            return false;
        Animal animal = (Animal) other;
        return (orientation.toUnitVector().equals(animal.orientation.toUnitVector()) && position.equals(((Animal) other).position));
    }

    @Override
    public int hashCode(){
        return Objects.hash(orientation, position);
    }
}
