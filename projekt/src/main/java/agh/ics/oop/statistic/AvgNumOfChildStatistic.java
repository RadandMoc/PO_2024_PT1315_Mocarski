package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SimulationDataProvider;

public class AvgNumOfChildStatistic implements Statistic {
    private final SimulationDataProvider dataProvider;

    public AvgNumOfChildStatistic(SimulationDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public String getValue() {
        double avgNumOfChild = dataProvider.getAllAnimalsStream()
                .mapToInt(Animal::getNumOfChild)
                .average()
                .orElse(0.0);

        return String.format("Średnia liczba dzieci: %.2f", avgNumOfChild);
    }
}

