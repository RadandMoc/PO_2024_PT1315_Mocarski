package agh.ics.oop.model;

import java.util.*;

public class AnimalGenomesPopularityCalculator {
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
        }else{
            List<Animal> animals = new ArrayList<>();
            animals.add(animal);
            activeAnimals.put(genome, animals);
            if(1 > popularity && !needActualization){
                popularGenome = genome;
                popularity = 1;
            }
        }
    }

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

    public String getMostPopularGenome(){
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

    public List<Vector2d> getAnimalsPositionsWithGenome(String genome){
        List<Vector2d> positions = new ArrayList<>();
        if(activeAnimals.containsKey(genome)){
            for(Animal animal : activeAnimals.get(genome)){
                positions.add(animal.position());
            }
        }
        return positions;
    }
}
