package agh.ics.oop.model;

public record Boundary(Vector2d lowerLeft, Vector2d upperRight){
    public boolean isVectorIn(Vector2d vec){
        return vec.follows(lowerLeft) && vec.precedes(upperRight);
    }
}
