package agh.ics.oop.fabric;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Plant;
import agh.ics.oop.model.ShowStatistics;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.statistic.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class StatisticFabric {

    public static ShowStatistics CreateClassicalStatistics(int places, HashMap<Vector2d, Plant> plants, HashMap<Vector2d, HashSet<Animal>> animals, HashSet<Animal> firstAnimals) {
        AvgEnergyForAnimalsStatistic avgEnergyForAnimalsStatistic = new AvgEnergyForAnimalsStatistic(animals);
        AvgNumOfChildStatistic avgNumOfChildStatistic = new AvgNumOfChildStatistic(animals);
        DeadAnimalsAverageAgeStatistic deadAnimalsAverageAgeStatistic = new DeadAnimalsAverageAgeStatistic(firstAnimals);
        FieldOnMapStatistic fieldOnMapStatistic = new FieldOnMapStatistic(places, plants, animals);

        return new SimulationStatistics(List.of(avgEnergyForAnimalsStatistic,
                avgNumOfChildStatistic,
                deadAnimalsAverageAgeStatistic,
                fieldOnMapStatistic));
    }
}
