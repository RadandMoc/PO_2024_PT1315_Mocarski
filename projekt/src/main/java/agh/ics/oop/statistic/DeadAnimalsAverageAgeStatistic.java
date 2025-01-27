package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SimulationDataProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DeadAnimalsAverageAgeStatistic implements Statistic {
    private final SimulationDataProvider dataProvider;

    public DeadAnimalsAverageAgeStatistic(SimulationDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public String getValue() {
        return String.format("Średni wiek martwych zwierząt: %.2f", dataProvider.getAverageLifeTime());
    }
}

