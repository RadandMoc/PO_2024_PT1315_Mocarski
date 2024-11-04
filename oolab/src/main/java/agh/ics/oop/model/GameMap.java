package agh.ics.oop.model;

public class GameMap {
    public final Vector2d upperRightMapPoint;
    public final Vector2d lowerLeftMapPoint;

    public GameMap(Vector2d lowerLeft, Vector2d upperRight){
        upperRightMapPoint = upperRight;
        lowerLeftMapPoint = lowerLeft;
    }

    public GameMap(){
        this(new Vector2d(0,0), new Vector2d(4,4));
    }

    protected boolean isPointInMap(Vector2d point){
        return point != null &&
                point.precedes(upperRightMapPoint) &&
                point.follows(lowerLeftMapPoint);
    }
}
