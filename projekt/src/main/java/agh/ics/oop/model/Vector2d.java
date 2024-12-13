package agh.ics.oop.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getY() { return y; }

    public int getX() { return x; }

    @Override
    public String toString() { return "(%d,%d)".formatted(x,y); }

    public boolean precedes(Vector2d other) {return x <= other.getX() && y <= other.getY();}

    public boolean follows(Vector2d other){
        return x >= other.getX() && y >= other.getY();
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(x + other.getX(), y + other.getY());
    }

    Vector2d upperRight(Vector2d other){
        return new Vector2d(  Math.max(x, other.getX()) , Math.max(y, other.getY()));
    }

    Vector2d lowerLeft(Vector2d other){
        return new Vector2d( Math.min(x, other.getX()) , Math.min(y, other.getY()));
    }

    Vector2d subtract(Vector2d other){ return new Vector2d(x - other.getX(), y - other.getY());}

    Vector2d opposite(){
        return new Vector2d(-x , -y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }

        var other = (Vector2d) obj;

        return this.x == other.getX() && this.y == other.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }
}

