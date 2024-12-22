package agh.ics.oop.model;

public enum MapDirection {
    N(new Vector2d(0, 1)),
    NE(new Vector2d(1, 1)),
    E(new Vector2d(1, 0)),
    SE(new Vector2d(1,-1)),
    S(new Vector2d(0, -1)),
    SW(new Vector2d(-1, -1)),
    W(new Vector2d(-1, 0)),
    NW(new Vector2d(-1, 1));

    private final Vector2d diretctionToVector;



    MapDirection(Vector2d vector) {
        diretctionToVector = vector;
    }

    @Override
    public String toString() {
        return switch (this){
            case N -> "Północ";
            case NE -> "Północny wschód";
            case E -> "Wschód";
            case SE -> "Południowy wschód";
            case S -> "Południe";
            case SW -> "Południowy zachód";
            case W -> "Zachód";
            case NW -> "Północny zachód";
        };
    }

    public MapDirection change(int rotate){
        return values()[(this.ordinal() + rotate) % values().length];
    }

    public Vector2d toUnitVector(){
        return diretctionToVector;
    }
}