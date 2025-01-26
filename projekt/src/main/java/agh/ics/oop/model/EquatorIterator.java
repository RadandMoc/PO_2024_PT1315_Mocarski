package agh.ics.oop.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EquatorIterator implements Iterator<Vector2d> {
        private final int startX;
        private final int endX;
        private final int endY;

        private int currentX;
        private int currentY;

    public EquatorIterator(Boundary boundary) {
        this.startX = boundary.lowerLeft().x();
        this.endX = boundary.upperRight().x();
        int startY = boundary.lowerLeft().y();
        this.endY = boundary.upperRight().y();
        this.currentX = startX;
        this.currentY = startY;
    }

    @Override
    public boolean hasNext() {
        return currentY <= endY;
    }

    @Override
    public Vector2d next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the equator.");
        }

        Vector2d current = new Vector2d(currentX, currentY);
        currentX++;

        if (currentX > endX) {
            currentX = startX;
            currentY++;
        }

        return current;
    }
}
