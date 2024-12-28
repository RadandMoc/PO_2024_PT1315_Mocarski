package agh.ics.oop.model;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int minWidth;
    private final int minHeight;
    private final int maxWidth;
    private final int maxHeight;
    private int howManyGenerate;
    private final Random random;
    private final RandomPositionIterator iterator;
    private long generatedBeforeThisIteration = 0;

    public RandomPositionGenerator(int minWidth, int minHeight, int maxWidth, int maxHeight, int howManyGenerate) throws ToMuchValuesToGenerateException {
        this(minWidth, minHeight, maxWidth, maxHeight, howManyGenerate, new Random().nextInt());
    }

    public RandomPositionGenerator(int minWidth, int minHeight, int maxWidth, int maxHeight, int howManyGenerate, int randomSeed) throws ToMuchValuesToGenerateException {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.howManyGenerate = howManyGenerate;
        this.random = new Random(randomSeed);
        iterator = new RandomPositionIterator();

        if (howManyGenerate > ((long)(maxWidth+1-minWidth)) * ((long)(maxHeight+1-minHeight))) {
            throw new ToMuchValuesToGenerateException("Number of object positions exceeds the total number of available positions.",((int)(howManyGenerate-(((long)(maxWidth+1-minWidth)) * ((long)(maxHeight+1-minHeight))))));
        }
    }

    public void setHowManyGenerate(int num){
        howManyGenerate = num;
    }

    public void acceptPositionToChoice(Vector2d pos){
        long width = (long)maxWidth - minWidth + 1;
        long thisHeight = (long)pos.getY() - minHeight;
        if(iterator.deleteGeneratedPos(width*thisHeight+pos.getX())){
            generatedBeforeThisIteration--;
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
        private final long height = (long)maxHeight - (long)minHeight + 1;
        private final long width = (long)maxWidth - (long)minWidth + 1;
        private final long surfaceArea = height*width;

        private boolean deleteGeneratedPos(long pos){
            if(generatedPositions.contains(pos)){
                generatedPositions.remove(pos);
                generatedCount--;
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
            if(generatedCount == surfaceArea)
                throw new ToMuchValuesToGenerateException("Number of object positions exceeds the total number of available positions.",((int)(-surfaceArea+howManyGenerate+generatedBeforeThisIteration)));

            long index = random.nextLong(surfaceArea - generatedCount);
            long a=0,b;

            do{
                b = a;
                a = generatedPositions.headSet(index + b, true).size();
            }while(a>b);
            index += a;

            int x = (int) ((index % width)+minWidth);
            int y = (int) ((index / width)+minHeight);
            generatedPositions.add(index);
            generatedCount++;
            return new Vector2d(x,y);
        }
    }
}
