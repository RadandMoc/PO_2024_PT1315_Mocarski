package agh.ics.oop.statistic;

import agh.ics.oop.model.SimulationDataProvider;

public class AnimalGenomesStatistic implements Statistic {
    private final SimulationDataProvider dataProvider;

    public AnimalGenomesStatistic(SimulationDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public String getValue() {
        return "Najpopularniejszy genom: " + dataProvider.getMostPopularGenome();
    }
}
