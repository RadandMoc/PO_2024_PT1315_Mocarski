package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MapPresenter implements MapChangeListener {
    private WorldMap map = null;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label infoLabel;

    private static final int CELL_WIDTH = 24;
    private static final int CELL_HEIGHT = 24;


    public void setMap(WorldMap map){
        this.map = map;
    }

    private void drawMap() {
        Boundary boundary = map.getCurrentBounds();
        int minx =  boundary.lowerLeft().getX();
        int maxX =  boundary.upperRight().getX();
        int minY =  boundary.lowerLeft().getY();
        int maxY =  boundary.upperRight().getY();

        clearGrid();
        drawUpperLeft();
        drawColumnNums(minx,maxX);
        drawRowNums(minY,maxY);
        drawMapObjects(minx, maxX, minY, maxY);
        mapGrid.setGridLinesVisible(true);
    }

    private void drawUpperLeft(){
        Label cornerLabel = new Label(" y/x ");
        GridPane.setHalignment(cornerLabel, HPos.CENTER);
        mapGrid.add(cornerLabel, 0, 0);
    }

    private void drawColumnNums(int min, int max) {
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        for (int i = min; i <= max; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, i - min + 1, 0);
        }
    }

    private void drawRowNums(int min, int max) {
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        for (int i = min; i <= max; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, max - i + 1);
        }
    }

    public void drawMapObjects(int xMin, int xMax, int yMin, int yMax) {
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                Vector2d pos = new Vector2d(i, j);
                Label cellLbl;
                if (map.isOccupied(pos)) {
                    cellLbl = new Label(map.objectAt(pos).toString());
                } else {
                    cellLbl = new Label(" ");
                }

                GridPane.setHalignment(cellLbl, HPos.CENTER);
                GridPane.setValignment(cellLbl, VPos.CENTER);

                mapGrid.add(cellLbl, i - xMin + 1, yMax - j + 1);
            }
        }
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        setMap(worldMap);
        Platform.runLater(() -> {
            drawMap();
            infoLabel.setText(message);
        });
    }
}