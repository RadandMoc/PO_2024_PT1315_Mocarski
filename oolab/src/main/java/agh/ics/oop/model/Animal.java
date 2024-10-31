package agh.ics.oop.model;

import agh.ics.oop.Simulation;

public class Animal {
    private MapDirection orientation;
    private Vector2d localization;

    public Animal(Vector2d localization){
        this.localization = localization;
        orientation = MapDirection.NORTH;
    }

    public Animal(){
        this(new Vector2d(2,2));
    }

    public Vector2d getLocalization(){
        return localization;
    }

    protected void setLocalization(Vector2d newLocalization){
        if(newLocalization != null &&
                newLocalization.upperRight(Simulation.upperRightMapPoint).equals(Simulation.upperRightMapPoint) &&
                newLocalization.lowerLeft(Simulation.lowerLeftMapPoint).equals(Simulation.lowerLeftMapPoint)){
            localization = newLocalization;
        }
    }

    @Override
    public String toString() {
        return String.format("Zwierzę jest na pozycji %s i zwrócone na %s",localization.toString(),orientation.toString());
    }

    protected boolean isAt(Vector2d position){
        return localization.equals(position);
    }

    protected void move(MoveDirection direction){
        switch (direction){
            case MoveDirection.RIGHT -> orientation = orientation.next();
            case MoveDirection.LEFT -> orientation = orientation.previous();
            case MoveDirection.BACKWARD -> setLocalization(localization.subtract(orientation.toUnitVector()));
            case MoveDirection.FORWARD -> setLocalization(localization.add(orientation.toUnitVector()));
        }
    }
}
