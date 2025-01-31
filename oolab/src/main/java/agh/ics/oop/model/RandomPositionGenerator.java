package agh.ics.oop.model;

import agh.ics.oop.model.Vector2d;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final int grassCount;
    private final Random random;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        this(maxWidth, maxHeight, grassCount, new Random().nextInt());
    }

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount, int randomSeed) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.grassCount = grassCount;
        this.random = new Random(randomSeed);

        if (grassCount > maxWidth * maxHeight) {
            throw new IllegalArgumentException("Number of grass positions exceeds the total number of available positions.");
        }
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new RandomPositionIterator();
    }

    private class RandomPositionIterator implements Iterator<Vector2d> {
        private final Set<Vector2d> generatedPositions = new HashSet<>();
        private int generatedCount = 0;

        @Override
        public boolean hasNext() {
            return generatedCount < grassCount;
        }

        @Override
        public Vector2d next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more positions to generate.");
            }

            Vector2d position;
            long index = random.nextLong((long) (maxHeight + 1) * (maxWidth + 1) - generatedCount);
            long checkingIndex = 0;
            for (int i = 0; i <= maxHeight; i++) {
                for (int j = 0; j <= maxWidth; j++) {
                    position = new Vector2d(i, j);
                    if (!generatedPositions.contains(position)) {
                        if (checkingIndex == index) {
                            generatedPositions.add(position);
                            generatedCount++;
                            return position;
                        }
                        checkingIndex += 1;
                    }
                }
            }

            /* Powyższe rozwiązanie spełnia wymagania bonusu, ale widzę tutaj inne, lepsze rozwiązanie (którego nie chce mi się implementować żeby nie tracić czasu)
             * mogę posiadać listę indexów po przerzutowaniu na jeden wymiar.
             * Po wylosowaniu indexu sprawdzić, które indexy z tych co były i powiększyć odpowiednio wylosowany index.
             * Następnie w forze od wysokości sprawdzasz, czy będzie w danej szerokości (czy wynik będzie dla danego i z powyższych forów)
             * Jeśli tam będzie, to checkingIndex powiększamy o maxWidth+1 i omijamy całkowicie tego for j ... aż dojdziemy do odpowiedniego i.
             *
             * Niezależnie od wersji lepszej czy gorszej, każda z nich ma złożoność O(n). Iteracja nie wykrozczy poza pola jednostkowe symulowanej figury (prostokąta)
             *  */

            throw new IllegalStateException();
        }
    }
}
