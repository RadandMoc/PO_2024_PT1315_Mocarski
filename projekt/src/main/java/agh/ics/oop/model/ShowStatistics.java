package agh.ics.oop.model;

import agh.ics.oop.statistic.Statistic;

import java.io.Serializable;
import java.util.List;

public interface ShowStatistics extends Serializable {
    List<String> show();
}
