package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.statistic.SimulationStatistics;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class ShowSimulationPresenter implements MapChangeListener, SimTurnListener {

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label moveDescriptionLabel;

    @FXML
    private List<WorldElementBox> worldElementsBox = new ArrayList<>();

    private AbstractWorldMap worldMap = null;


    private static final int CELL_WIDTH = 30;
    private static final int CELL_HEIGHT = 30;

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
        });
    }


}