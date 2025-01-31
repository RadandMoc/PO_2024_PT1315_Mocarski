package agh.ics.oop.statistic;

import agh.ics.oop.model.ShowStatistics;

import java.util.List;

public class SimulationStatistics implements ShowStatistics {
    final List<Statistic> statisticList;

    public SimulationStatistics(List<Statistic> statisticList) {
        this.statisticList = statisticList;
    }

    @Override
    public List<String> show() {
        return statisticList.stream()
                .map(Statistic::getValue)
                .toList();
    }
}
