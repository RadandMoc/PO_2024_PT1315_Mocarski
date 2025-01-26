package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SimulationDataProvider;

public class AvgEnergyForAnimalsStatistic implements Statistic {
    private final SimulationDataProvider dataProvider;

    public AvgEnergyForAnimalsStatistic(SimulationDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public String getValue() {
        double avgEnergy = dataProvider.getAllAnimalsStream()
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0.0);

        return String.format("Średnia energia zwierząt: %.2f", avgEnergy);
    }
}

