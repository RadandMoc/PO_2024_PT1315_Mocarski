package agh.ics.oop.model;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int minWidth;
    private final int minHeight;
    private final int maxHeight;
    private final int width;
    private int howManyGenerate;
    private final Random random;
    private final RandomPositionIterator iterator;
    private long generatedBeforeThisIteration = 0;

    public RandomPositionGenerator(Boundary boundary, int howManyGenerate) {
        this(boundary.lowerLeft(), boundary.upperRight(), howManyGenerate);
    }

    public RandomPositionGenerator(Vector2d lowerLeft, Vector2d upperRight, int howManyGenerate) throws ToMuchValuesToGenerateException {
        this(lowerLeft.x(), lowerLeft.y(), upperRight.x(), upperRight.y(), howManyGenerate);
    }

    public RandomPositionGenerator(int minWidth, int minHeight, int maxWidth, int maxHeight, int howManyGenerate) throws ToMuchValuesToGenerateException {
        this(minWidth, minHeight, maxWidth, maxHeight, howManyGenerate, new Random().nextInt());
    }

    public RandomPositionGenerator(int minWidth, int minHeight, int maxWidth, int maxHeight, int howManyGenerate, int randomSeed) throws ToMuchValuesToGenerateException {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.howManyGenerate = howManyGenerate;
        this.random = new Random(randomSeed);
        this.width = maxWidth - minWidth + 1;
        iterator = new RandomPositionIterator();

        if (howManyGenerate > ((long) width) * ((long) (maxHeight + 1 - minHeight))) {
            throw new ToMuchValuesToGenerateException("Number of object positions exceeds the total number of available positions.", ((int) (howManyGenerate - (((long) (maxWidth + 1 - minWidth)) * ((long) (maxHeight + 1 - minHeight))))));
        }
    }

    public void setHowManyGenerate(int num) {
        howManyGenerate = num;
    }

    private Vector2d translatePosition(long pos) {
        int x = (int) (pos % width);
        int y = (int) (pos / width);
        return new Vector2d(x + minWidth, y + minHeight);
    }

    private long translatePosition(Vector2d pos) {
        return ((long) pos.y() - minHeight) * width + ((long) pos.x() - minWidth);
    }

    public void acceptPositionToChoice(Vector2d pos) {
        if (iterator.deleteGeneratedPos(translatePosition(pos))) {
            generatedBeforeThisIteration--;
        } else {
            throw new IllegalArgumentException("Position was not generated before.");
        }
    }

    public void deleteRectangle(Boundary rect) {
        for (int i = rect.lowerLeft().x(); i <= rect.upperRight().x(); i++) {
            for (int j = rect.lowerLeft().y(); j <= rect.upperRight().y(); j++) {
                deletePositionToChoice(new Vector2d(i, j));
            }
        }
    }

    public void deletePositionToChoice(Vector2d pos) {
        if (iterator.addGeneratedPos(translatePosition(pos))) {
            generatedBeforeThisIteration++;
        }
    }

    @Override
    public Iterator<Vector2d> iterator() {
        generatedBeforeThisIteration = iterator.generatedCount;
        return iterator;
    }

    private class RandomPositionIterator implements Iterator<Vector2d> {
        private final TreeSet<Long> generatedPositions = new TreeSet<>();
        private long generatedCount = 0;
        private final long surfaceArea = ((long) maxHeight - (long) minHeight + 1) * width;

        private boolean deleteGeneratedPos(long pos) {
            if (generatedPositions.contains(pos)) {
                generatedPositions.remove(pos);
                generatedCount--;
                return true;
            }
            return false;
        }

        private boolean addGeneratedPos(long pos) {
            if (!generatedPositions.contains(pos)) {
                generatedPositions.add(pos);
                generatedCount++;
                return true;
            }
            return false;
        }

        @Override
        public boolean hasNext() {
            return generatedCount < howManyGenerate + generatedBeforeThisIteration;
        }

        @Override
        public Vector2d next() {
            if (generatedCount == surfaceArea)
                throw new ToMuchValuesToGenerateException("Number of object positions exceeds the total number of available positions.", ((int) (-surfaceArea + howManyGenerate + generatedBeforeThisIteration)));

            long index = random.nextLong(surfaceArea - generatedCount);
            long a = 0, b;

            do {
                b = a;
                a = generatedPositions.headSet(index + b, true).size();
            } while (a > b);
            index += a;

            Vector2d res = translatePosition(index);
            generatedPositions.add(index);
            generatedCount++;
            return res;
        }
    }
}
