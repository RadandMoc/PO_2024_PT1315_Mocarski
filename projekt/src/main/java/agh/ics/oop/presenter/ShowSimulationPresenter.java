package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.statistic.SimulationStatistics;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowSimulationPresenter implements MapChangeListener, SimTurnListener {

    @FXML
    private GridPane mapGrid;

    @FXML
    private VBox statisticsBox;

    @FXML
    private Button pauseButton;

    @FXML
    private Label moveDescriptionLabel;

    @FXML
    private final List<WorldElementBox> worldElementsBox = new ArrayList<>();

    private AbstractWorldMap worldMap = null;


    private final Object pauseLock = new Object();
    private volatile boolean paused = false;
    private Thread simulationThread;


    private static final int CELL_WIDTH = 30;
    private static final int CELL_HEIGHT = 30;

    @FXML
    private VBox animalsToSelectContainer;

    private AnimalsToSelect animalsToSelect;

    private Simulation simulation;

    private void drawMap() {
        clearGrid();
        Boundary boundary = worldMap.getBoundary();

        int minx =  boundary.lowerLeft().getX();
        int maxX =  boundary.upperRight().getX();
        drawColumn(minx,maxX);

        int minY =  boundary.lowerLeft().getY();
        int maxY =  boundary.upperRight().getY();
        drawRow(minY,maxY);
        addElements(minx, maxX, minY, maxY);
        mapGrid.setGridLinesVisible(true);
    }

    private void drawColumn(int minX, int maxX) {
        Label cornerLabel = new Label(" y/x ");
        GridPane.setHalignment(cornerLabel, HPos.CENTER);
        mapGrid.add(cornerLabel, 0, 0);

        for (int i = 0; i <= (maxX - minX + 1); i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for (int i = minX; i <= maxX; i++) {
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, i - minX + 1, 0);
        }
    }

    private void drawRow(int minY, int maxY) {

        for (int i = 0; i <= (maxY - minY + 1); i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        for (int i = maxY; i >= minY; i--) {
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, maxY - i + 1);
        }
    }
    public void addElements(int xMin, int xMax, int yMin, int yMax) {
        int worldElementIdx = 0;
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                Vector2d pos = new Vector2d(i, j);
                if (worldMap.isOccupied(pos)) {
                    WorldElement element = worldMap.objectAt(pos).orElse(null);
                    if (element != null) {
                        WorldElementBox box = setWorldElementsBox(worldElementIdx, element);
                        mapGrid.add(box.getVBox(), i - xMin + 1, yMax - j + 1);
                        ++worldElementIdx;
                    }
                }
            }
        }

        for (int j = worldElementIdx; j < worldElementsBox.size(); ++j){
            worldElementsBox.get(j).setNull();
        }

    }


    private WorldElementBox setWorldElementsBox(int idx, WorldElement element){
        if (idx < worldElementsBox.size()){
            WorldElementBox box = worldElementsBox.get(idx);
            element.updateWorldElementBox(box);
            return box;
        }

        WorldElementBox box = new WorldElementBox(element);
        worldElementsBox.add(box);
        return box;
    }



    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }


    public void setWorldMap(AbstractWorldMap map){
        worldMap = map;
    }

    @Override
    public void mapChanged(AbstractWorldMap worldMap, String message) {
        setWorldMap(worldMap);
        Platform.runLater(() -> {
            drawMap();
        });
    }

    @Override
    public void onNewTurnChange(Simulation sim) {
        Platform.runLater(() -> {
            List<String> statistics = sim.getShowStatistics().show();
            statisticsBox.getChildren().clear();
            for (String s : statistics) {
                Label label = new Label(s);
                statisticsBox.getChildren().add(label);
            }
        });
    }

    @FXML
    private void onPauseButtonClicked() {
        synchronized (pauseLock) {
            paused = !paused;

            if (!paused) {
                pauseLock.notifyAll();
                pauseButton.setText("Pause");
                animalsToSelectContainer.setVisible(false);
                animalsToSelectContainer.setManaged(false);

            } else {
                List<Animal> animals = worldMap.getAnimals();
                animalsToSelect = new AnimalsToSelect(animals);
                pauseButton.setText("Resume");

                animalsToSelectContainer.getChildren().setAll(animalsToSelect.getListView());
                animalsToSelectContainer.setVisible(true);
                animalsToSelectContainer.setManaged(true);


                animalsToSelect.getListView().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        showAnimalView(newVal);
                    }
                });

            }
        }
    }

    private void showAnimalView(Animal animal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showAnimalHistory.fxml"));
            Parent root = loader.load();

            AnimalViewPresenter presenter = loader.getController();
            presenter.setAnimal(animal);

            simulation.addObserver(presenter);


            Stage stage = new Stage();
            stage.setTitle("Animal Details");
            stage.setScene(new Scene(root));
            stage.show();

            stage.setOnHidden(event -> {
                simulation.removeObserver(presenter);
                System.out.println("Okno zostało zamknięte.");
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Object getPauseLock() {
        return pauseLock;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setSimulationThread(Thread simulationThread) {
        this.simulationThread = simulationThread;
    }

    public void setSimulation(Simulation sim){
        this.simulation = sim;
    }

}