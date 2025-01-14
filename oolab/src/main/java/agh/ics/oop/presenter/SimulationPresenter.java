package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static agh.ics.oop.OptionsParser.whereMove;

public class SimulationPresenter {

    @FXML
    private TextField movesTextField;

    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);
    private final List<Stage> mapPresenterStages = new ArrayList<>();
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setOnCloseRequest(event -> closeAllMapPresenterStages());
    }
    @FXML
    private void onSimulationStartClicked(){
        String moveList = movesTextField.getText();
        List<MoveDirection> directions = whereMove(moveList.split(" "));
        List<Vector2d> positions = List.of(new Vector2d(2,2),new Vector2d(3,4));
        AbstractWorldMap map = new GrassField(16);
        map.addObserver((worldMap, message) -> {
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println(currentTime + " " + message);
        });
        threadPool.submit(() -> Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/map.fxml"));
                Parent root = loader.load();
                MapPresenter presenter = loader.getController();
                map.addObserver(presenter);
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root, 850, 550));
                newStage.setTitle(moveList);
                mapPresenterStages.add(newStage);
                newStage.setOnCloseRequest(event -> {
                    mapPresenterStages.remove(newStage);
                    newStage.close();
                });
                newStage.show();
                Simulation simulation = new Simulation(positions, directions, map);
                new Thread(simulation).start();
            } catch (IOException ignored) { }
        }));
    }

    private void closeAllMapPresenterStages() {
        for (Stage stage : mapPresenterStages) {
            if (stage.isShowing()) {
                stage.close();
            }
        }
        mapPresenterStages.clear();
        threadPool.shutdownNow();
    }

    public void shutdownThreadPool(){
        threadPool.shutdown();
    }
}
