package agh.ics.oop.presenter;

import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.WorldMap;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationPresenter {
    private WorldMap map;

    private void setWorldMap(WorldMap map){
        this.map = map;
    }

    private void initializeSimulation(){
        SimulationApp simulationApp = new SimulationApp();
        try {
            simulationApp.start(new Stage());
        } catch (IOException ignored) {

        }
    }

    private void drawMap(){

    }
}
