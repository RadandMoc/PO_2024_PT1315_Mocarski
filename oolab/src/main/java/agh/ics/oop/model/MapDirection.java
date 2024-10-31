package agh.ics.oop.model;

public enum MapDirection {
    NORTH(new Vector2d(0,1)),
    SOUTH(new Vector2d(0,-1)),
    WEST(new Vector2d(-1,0)),
    EAST(new Vector2d(1,0));

    private final Vector2d direction;

    MapDirection(Vector2d vector){
        direction = vector;
    }

    @Override
    public String toString(){
        return switch(this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
        };
    }

    protected MapDirection next(){
        return switch(this) {
            case NORTH -> MapDirection.EAST;
            case SOUTH -> MapDirection.WEST;
            case WEST -> MapDirection.NORTH;
            case EAST -> MapDirection.SOUTH;
        };
    }

    protected MapDirection previous(){
        return switch(this) {
            case NORTH -> MapDirection.WEST;
            case SOUTH -> MapDirection.EAST;
            case WEST -> MapDirection.SOUTH;
            case EAST -> MapDirection.NORTH;
        };
    }

    private Vector2d toUnitVector(){
        return direction;
    }
}
