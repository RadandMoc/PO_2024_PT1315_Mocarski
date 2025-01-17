package agh.ics.oop.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AnimalGenomesListener {
    private final HashMap<String,Integer> activeGenomes = new HashMap<>();
    private String popularGenome;
    private int popularity = 0;
    private boolean needActualization = false;

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
    }

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
    }

    public String theMostPopularGenome(){
        if(needActualization){
            String maxKey = null;
            int maxValue = Integer.MIN_VALUE;
            for (Map.Entry<String, Integer> entry : activeGenomes.entrySet()) {
                if (entry.getValue() > maxValue) {
                    maxValue = entry.getValue();
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
}
