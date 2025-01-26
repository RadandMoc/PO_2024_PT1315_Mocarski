package agh.ics.oop.model;

public record Vector2d(int x, int y) {

    @Override
    public String toString() {
        return "(%d,%d)".formatted(x, y);
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x() && y <= other.y();
    }

    public boolean follows(Vector2d other) {
        return x >= other.x() && y >= other.y();
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x(), y + other.y());
    }

    Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(x, other.x()), Math.max(y, other.y()));
    }

    Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(x, other.x()), Math.min(y, other.y()));
    }

    Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x(), y - other.y());
    }

    Vector2d opposite() {
        return new Vector2d(-x, -y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var other = (Vector2d) obj;
        return this.x == other.x() && this.y == other.y();
    }

}

