package agh.ics.oop;

import agh.ics.oop.model.AbstractWorldMap;
import agh.ics.oop.model.EnergyLoss;
import agh.ics.oop.model.MutateGenome;

public class Simulation {
    private final AbstractWorldMap map;
    private final MutateGenome typeOfMutation;
    private final EnergyLoss energyLoss;
    private final int plantEnergy;

    public Simulation(AbstractWorldMap selectedMap, int startedPlants, int startedAnimal, int startingEnergy,
                      int plantEnergy ,int energyToBeingFullStuffed, int breadingEnergyLoss, int lenOfGenome, MutateGenome selectedMutatation, EnergyLoss energyLossMethod ){
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
        energyLoss = energyLossMethod;
        this.plantEnergy = plantEnergy;
    }

    public void run(){
        map.clearDeathAnimal(energyLoss);
        map.movesAllAnimals();
        map.animalsConsume(plantEnergy);
        //map.breeding()
        //map.generateNewPlants()

    }
}
