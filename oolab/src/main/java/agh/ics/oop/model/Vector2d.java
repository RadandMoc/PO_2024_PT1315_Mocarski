package agh.ics.oop.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x,int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected boolean precedes(Vector2d other){
        return (x <= other.getX()) && (y <= other.getY());
    }

    protected boolean follows(Vector2d other){
        return (x >= other.getX()) && (y >= other.getY());
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(x+other.getX(),y+other.getY());
    }

    protected Vector2d subtract(Vector2d other){
        return new Vector2d(x-other.getX(),y-other.getY());
    }

    protected Vector2d upperRight(Vector2d other){
        return new Vector2d(max(x,other.getX()),max(y,other.getY()));
    }

    protected Vector2d lowerLeft(Vector2d other){
        return new Vector2d(min(x,other.getX()),min(y,other.getY()));
    }

    protected Vector2d opposite(){
        return new Vector2d(-x,-y);
    }

    @Override
    public boolean equals(Object other){
        if (other == null || getClass() != other.getClass())
            return false;
        Vector2d vector = (Vector2d) other;
        return (x == vector.getX()) && (y == vector.getY());
    }

    @Override
    public int hashCode(){
        return (37*x + y);
    }
}
