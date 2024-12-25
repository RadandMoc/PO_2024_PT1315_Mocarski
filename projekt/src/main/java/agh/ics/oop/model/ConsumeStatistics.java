package agh.ics.oop.model;

public record ConsumeStatistics(int energy, int turnOfLive, int numOfChild) {
    public int selectComponent(ConsumeStatComponent type){
        return switch (type) {
            case ENERGY -> energy;
            case LIFETIME -> turnOfLive;
            case NUMBER_OF_CHILDREN -> numOfChild;
        };
    }
}
