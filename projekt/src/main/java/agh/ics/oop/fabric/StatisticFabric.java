package agh.ics.oop.fabric;

import agh.ics.oop.model.*;
import agh.ics.oop.statistic.*;
import com.sun.javafx.collections.MappingChange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class StatisticFabric {

    public static ShowStatistics CreateClassicalStatistics(MapDataProvider dataProvider) {
        AnimalGenomesStatistic animalGenomesStatistic = new AnimalGenomesStatistic(dataProvider);
        AvgEnergyForAnimalsStatistic avgEnergyForAnimalsStatistic = new AvgEnergyForAnimalsStatistic(dataProvider);
        AvgNumOfChildStatistic avgNumOfChildStatistic = new AvgNumOfChildStatistic(dataProvider);
        DeadAnimalsAverageAgeStatistic deadAnimalsAverageAgeStatistic = new DeadAnimalsAverageAgeStatistic(dataProvider);
        FieldOnMapStatistic fieldOnMapStatistic = new FieldOnMapStatistic(dataProvider);

        return new SimulationStatistics(List.of(avgEnergyForAnimalsStatistic,
                avgNumOfChildStatistic,
                deadAnimalsAverageAgeStatistic,
                fieldOnMapStatistic,
                animalGenomesStatistic));
    }
}
