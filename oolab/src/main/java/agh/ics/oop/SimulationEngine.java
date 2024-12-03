package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final ArrayList<Simulation> sims = new ArrayList<>();
    private final ArrayList<Thread> threads = new ArrayList<>();
    private final ExecutorService threadsInPool = Executors.newFixedThreadPool(4);

    public SimulationEngine(List<Simulation> sims){
        this.sims.addAll(sims);
    }

    public void runSync(){
        for (Simulation sim : sims){
            sim.run();
        }
    }

    public void runAsync(){
        for (Simulation sim : sims){
            Thread simThread = new Thread(sim);
            simThread.start();
            threads.add(simThread);
        }
    }

    public void awaitSimulationsEnd(){
        try {
            for (Thread thread : threads) {
                thread.join(); // Czekamy na zakończenie bieżącego wątku
            }
            threadsInPool.shutdown();
            if (!threadsInPool.awaitTermination(10, TimeUnit.SECONDS)) {
                threadsInPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runAsyncInThreadPool(){
        for (Simulation sim : sims) {
            threadsInPool.submit(sim);
        }
    }
}
