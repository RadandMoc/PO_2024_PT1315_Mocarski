package agh.ics.oop;

import agh.ics.oop.fabric.StatisticFabric;
import agh.ics.oop.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final AbstractWorldMap map;
    private final MutateGenome typeOfMutation;
    private final StrongestAnimalFinder strongestAnimalFinder = new ConsumeConflictSolver();
    private int currentTurn = 0;
    private final int energyToBeingFullStuffed;
    private final int startingEnergy;
    private final int numOfNewPlantsPerTurn;
    private final int breadingEnergyLoss;
    private final ReproductionStrategy typeOfReproduction;
    private final List<SimTurnListener> observers = new ArrayList<>();
    private final ShowStatistics showStatistics;
    private final boolean saveStatistics;
    private final String fileName;

    public Simulation(AbstractWorldMap selectedMap, int startingAnimals, int startingEnergy,
                      int energyToBeingFullStuffed, int breadingEnergyLoss, int lenOfGenome,
                      MutateGenome selectedMutatation, int numOfNewPlantsPerTurn, ReproductionStrategy typeOfReproduction, boolean saveStatistics) {
        map = selectedMap;
        // selectedMap.generatePlants(startedPlants);
        // energia zapewniana przez zjedzenie jednej rośliny -> GUI tworzymy dobra mape
        // liczba roślin wyrastająca każdego dnia -> GUI tworzymy dobra mape
        // selectedMap.generateAnimals(startedAnimal, startingEnergy, lenOfGenome);
        // energia konieczna, by uznać zwierzaka za najedzonego -> GUI
        // energia rodziców zużywana by stworzyć potomka -> GUI
        // int minNumOfMutation,
        // wariant zachowania zwierzaków -> stworzę interfejs
        map.generateRandomAnimals(startingAnimals, lenOfGenome, startingEnergy);
        this.energyToBeingFullStuffed = energyToBeingFullStuffed;
        this.startingEnergy = startingEnergy;
        this.numOfNewPlantsPerTurn = numOfNewPlantsPerTurn;
        this.breadingEnergyLoss = breadingEnergyLoss;
        typeOfMutation = selectedMutatation;
        this.typeOfReproduction = typeOfReproduction;
        MapDataProvider dataProvider = new MapDataProvider(map);
        addObserver(dataProvider);
        this.showStatistics = StatisticFabric.CreateClassicalStatistics(dataProvider);
        this.saveStatistics = saveStatistics;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
        this.fileName = "save_No" + LocalDateTime.now().format(formatter) + ".csv";
    }

    public ShowStatistics getShowStatistics() {
        return showStatistics;
    }

    public void addObserver(SimTurnListener turnListener) {
        this.observers.add(turnListener);
    }

    public void removeObserver(SimTurnListener turnListener) {
        this.observers.remove(turnListener);
    }

    public void notifyObserver() { // public? liczba pojedyncza?
        for (var observer : observers) {
            observer.onNewTurnChange(this);
        }
    }

    private void saveStatistics() {
        List<String> statistics = showStatistics.show();
        StringBuilder sb = new StringBuilder();
        sb.append(currentTurn).append(": ");
        for (int i = 0; i < statistics.size(); i++) {
            String sanitizedStatistic = statistics.get(i).replace("\n", ": ");
            sb.append(sanitizedStatistic);
            if (i < statistics.size() - 1) {
                sb.append(": ");
            }
        }
        sb.append(System.lineSeparator());

        String filePath = "src/main/resources/simulationsSaves/" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws InterruptedException { // czy to throws to dobry wybór?
        map.clearDeathAnimal();
        map.movesAllAnimals();
        map.animalsConsume(strongestAnimalFinder);
        map.breeding(energyToBeingFullStuffed, breadingEnergyLoss, currentTurn, typeOfMutation, typeOfReproduction);
        map.generatePlants(numOfNewPlantsPerTurn);
        currentTurn++;
        notifyObserver();
        if (saveStatistics) {
            saveStatistics();
        }
    }
}
