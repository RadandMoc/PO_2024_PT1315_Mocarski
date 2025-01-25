package agh.ics.oop.statistic;

import agh.ics.oop.model.Animal;

import java.util.*;

public class AnimalGenomesStatistic implements Statistic {
    private final HashMap<String,List<Animal>> activeAnimals = new HashMap<>();
    private String popularGenome = "";
    private int popularity = 0;
    private boolean needActualization = false;

    public void newAnimal(Animal animal){
        String genome = animal.getGenome();
        if(activeAnimals.containsKey(genome)){
            activeAnimals.get(genome).add(animal);
            int val =  activeAnimals.get(genome).size();
            if(val > popularity && !needActualization){
                popularGenome = genome;
                popularity = val;
            }
        }else
        {
            List<Animal> animals = new ArrayList<>();
            animals.add(animal);
            activeAnimals.put(genome, animals);
            if(1 > popularity && !needActualization){
                popularGenome = genome;
                popularity = 1;
            }
        }
    }
/*
    public void newGenome(List<Byte> genome){
        StringBuilder genomeInStr = new StringBuilder();
        for (int item : genome){
            genomeInStr.append(item);
        }
        String gen = genomeInStr.toString();
        if(activeGenomes.containsKey(gen)){
            int val = activeGenomes.get(gen) + 1;
            activeGenomes.put(gen, val);
            if(val > popularity && !needActualization){
                popularGenome = gen;
                popularity = val;
            }
        }else
        {
            activeGenomes.put(gen, 1);
            if(1 > popularity && !needActualization){
                popularGenome = gen;
                popularity = 1;
            }
        }
    }*/

    public void deleteAnimal(Animal animal){
        String genome = animal.getGenome();
        if(activeAnimals.containsKey(genome)){
            int numOfGenomes = activeAnimals.get(genome).size();
            if(numOfGenomes>1){
                activeAnimals.get(genome).remove(animal);
                if (Objects.equals(popularGenome, genome) && !needActualization){
                    needActualization = true;
                }
            }
            else{
                activeAnimals.remove(genome);
                if (Objects.equals(popularGenome, genome) && !needActualization){
                    for(Map.Entry<String, List<Animal>> entry : activeAnimals.entrySet()){
                        popularGenome = entry.getKey();
                        break;
                    }
                    if(Objects.equals(popularGenome, genome)){
                        popularGenome = "";
                        popularity = 0;
                    }
                }
            }
        }else
            throw new IllegalStateException("There is no genome with this type.");
    }
/*
    public void deleteGenome(List<Byte> genome){
        StringBuilder genomeInStr = new StringBuilder();
        for (int item : genome){
            genomeInStr.append(item);
        }
        String gen = genomeInStr.toString();
        if(activeGenomes.containsKey(gen)){
            int numOfGenomes = activeGenomes.get(gen);
            if(numOfGenomes>1){
                activeGenomes.put(gen, activeGenomes.get(gen) - 1);
                if (Objects.equals(popularGenome, gen) && !needActualization){
                    needActualization = true;
                }
            }
            else{
                activeGenomes.remove(gen);
                if (Objects.equals(popularGenome, gen) && !needActualization){
                    for(Map.Entry<String, Integer> entry : activeGenomes.entrySet()){
                        popularGenome = entry.getKey();
                        break;
                    }
                }
            }
        }else
            throw new IllegalStateException("There is no genome with this type.");
    }*/

    public String theMostPopularGenome(){
        if(needActualization){
            String maxKey = "";
            int maxValue = Integer.MIN_VALUE;
            for (Map.Entry<String, List<Animal>> entry : activeAnimals.entrySet()) {
                if (entry.getValue().size() > maxValue) {
                    maxValue = entry.getValue().size();
                    maxKey = entry.getKey();
                }
            }
            needActualization = false;
            popularity = maxValue;
            popularGenome = maxKey;
            return maxKey;
        }
        return popularGenome;
    }

    @Override
    public String getValue() {
        return theMostPopularGenome();
    }
}
