package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST

    @Override
    public String toString(){
        return switch(this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
        };
    }

    private MapDirection next(){
        return switch(this) {
            case NORTH -> MapDirection.EAST;
            case SOUTH -> MapDirection.WEST;
            case WEST -> MapDirection.NORTH;
            case EAST -> MapDirection.SOUTH;
        };
    }

    private MapDirection previous(){
        return switch(this) {
            case NORTH -> MapDirection.WEST;
            case SOUTH -> MapDirection.EAST;
            case WEST -> MapDirection.SOUTH;
            case EAST -> MapDirection.NORTH;
        };
    }

    private Vector2d toUnitVector(){
        return switch(this) {
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case WEST -> new Vector2d(-1,0);
            case EAST -> new Vector2d(1,0);
        };
    }
}
