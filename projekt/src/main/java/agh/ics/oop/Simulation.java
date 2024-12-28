package agh.ics.oop;

import agh.ics.oop.model.*;

public class Simulation {
    private final AbstractWorldMap map;
    private final MutateGenome typeOfMutation;
    private final StrongestAnimalFinder strongestAnimalFinder = new ConsumeConflictSolver();
    private int currentTurn = 0;

    public Simulation(AbstractWorldMap selectedMap, int startedPlants, int startedAnimal, int startingEnergy,
                      int energyToBeingFullStuffed, int breadingEnergyLoss, int lenOfGenome, MutateGenome selectedMutatation, EnergyLoss energyLossMethod ){
        map = selectedMap;
        // selectedMap.generatePlants(startedPlants);
        // energia zapewniana przez zjedzenie jednej rośliny -> GUI tworzymy dobra mape
        // liczba roślin wyrastająca każdego dnia -> GUI tworzymy dobra mape
        // selectedMap.generateAnimals(startedAnimal, startingEnergy, lenOfGenome);
        // energia konieczna, by uznać zwierzaka za najedzonego -> GUI
        // energia rodziców zużywana by stworzyć potomka -> GUI
        // int minNumOfMutation,
        // wariant zachowania zwierzaków -> stworzę interfejs
        typeOfMutation = selectedMutatation;
    }

    public void run(){
        map.clearDeathAnimal();
        map.movesAllAnimals();
        map.animalsConsume(strongestAnimalFinder);
        //map.breeding();
        //map.generateNewPlants()
        currentTurn++;
    }
}
