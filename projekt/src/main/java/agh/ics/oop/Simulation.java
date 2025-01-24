package agh.ics.oop;

import agh.ics.oop.fabric.StatisticFabric;
import agh.ics.oop.model.*;
import agh.ics.oop.statistic.SimulationStatistics;

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

    public Simulation(AbstractWorldMap selectedMap, int startingAnimals, int startingEnergy,
                      int energyToBeingFullStuffed, int breadingEnergyLoss, int lenOfGenome,
                      MutateGenome selectedMutatation, int numOfNewPlantsPerTurn, ReproductionStrategy typeOfReproduction ){
        map = selectedMap;
        // selectedMap.generatePlants(startedPlants);
        // energia zapewniana przez zjedzenie jednej rośliny -> GUI tworzymy dobra mape
        // liczba roślin wyrastająca każdego dnia -> GUI tworzymy dobra mape
        // selectedMap.generateAnimals(startedAnimal, startingEnergy, lenOfGenome);
        // energia konieczna, by uznać zwierzaka za najedzonego -> GUI
        // energia rodziców zużywana by stworzyć potomka -> GUI
        // int minNumOfMutation,
        // wariant zachowania zwierzaków -> stworzę interfejs
        map.generateRandomAnimals(startingAnimals,lenOfGenome,startingEnergy);
        this.energyToBeingFullStuffed = energyToBeingFullStuffed;
        this.startingEnergy = startingEnergy;
        this.numOfNewPlantsPerTurn = numOfNewPlantsPerTurn;
        this.breadingEnergyLoss = breadingEnergyLoss;
        typeOfMutation = selectedMutatation;
        this.typeOfReproduction = typeOfReproduction;
        showStatistics = map.setStatistics();
    }

    public ShowStatistics getShowStatistics() {
        return showStatistics;
    }

    public void addObserver(SimTurnListener turnListener){
        this.observers.add(turnListener);
    }

    public void run(){
        map.clearDeathAnimal();
        map.movesAllAnimals();
        map.animalsConsume(strongestAnimalFinder);
        map.breeding(energyToBeingFullStuffed,breadingEnergyLoss,currentTurn,typeOfMutation,typeOfReproduction);
        map.generatePlants(numOfNewPlantsPerTurn);
        currentTurn++;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
