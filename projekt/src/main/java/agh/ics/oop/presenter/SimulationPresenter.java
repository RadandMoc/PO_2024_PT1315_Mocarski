package agh.ics.oop.presenter;
import agh.ics.oop.Simulation;
import agh.ics.oop.fabric.MutationFactory;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.converter.IntegerStringConverter;

import java.util.List;

public class SimulationPresenter {

    @FXML
    private Button but;
    @FXML
    private TextField numberField;
    @FXML
    private TextField height;
    @FXML
    private TextField width;
    @FXML
    private TextField x;
    @FXML
    private TextField y;
    @FXML
    private TextField energyFromPlant;
    @FXML
    private TextField genomeLength;
    @FXML
    private TextField energyForBeingFullStaffed;
    @FXML
    private TextField startingEnergy;
    @FXML
    private TextField numOfNewPlantsPerTurn;
    @FXML
    private TextField breadingEnergyLoss;

    @FXML
    private TextField startsNumOfPlants;

    @FXML
    private ComboBox<String> mapChoice;

    @FXML
    private ComboBox<MutationStrategy> mutationStrategy;

    @FXML
    private Label energyLossLabel;

    @FXML
    private Label energyLossPerMoveToPoleLabel;

    @FXML
    private TextField energyLoss;

    @FXML
    private TextField energyLossPerMoveToPole;

    @FXML
    private Integer minMutation;

    @FXML
    private Integer maxMutation;

    @FXML
    public void initialize() {
        mapChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Globe".equals(newValue)) {
                energyLossPerMoveToPoleLabel.setVisible(false);
                energyLossPerMoveToPole.setVisible(false);
            } else if ("Globe with pole".equals(newValue)) {
                energyLossPerMoveToPoleLabel.setVisible(true);
                energyLossPerMoveToPole.setVisible(true);
            }
        });

        // Wywołaj ręcznie przy inicjalizacji, aby ustawić widoczność na podstawie domyślnego wyboru
        mapChoice.getSelectionModel().select("Globe");
    }


    @FXML
    private void onSimulationStartClicked() {
        try {
            int heightValue = validateIntegerField(height);
            int widthValue = validateIntegerField(width);
            int xValue = validateIntegerField(x);
            int yValue = validateIntegerField(y);
            int energyFromPlantValue = validateIntegerField(energyFromPlant);
            int genomeLengthValue = validateIntegerField(genomeLength);
            int energyForBeingFullStaffedValue = validateIntegerField(energyForBeingFullStaffed);
            int startingEnergyValue = validateIntegerField(startingEnergy );
            int numOfNewPlantsPerTurnValue = validateIntegerField(numOfNewPlantsPerTurn );
            int breadingEnergyLossValue = validateIntegerField(breadingEnergyLoss);
            int energyLossValue = validateIntegerField(energyLoss);
            int energyLossPerMoveToPoleValue = validateIntegerField(energyLossPerMoveToPole);
            int startsNumOfPlantsValue = validateIntegerField(startsNumOfPlants);


            boolean isAllAboveZero = checkIfAboveZero(List.of(heightValue, widthValue, energyFromPlantValue,
                    genomeLengthValue, energyForBeingFullStaffedValue, startingEnergyValue,
                    numOfNewPlantsPerTurnValue, breadingEnergyLossValue, energyLossPerMoveToPoleValue,
                    startsNumOfPlantsValue));
            if (!isAllAboveZero){
                System.out.println("Please correct variable with value below or equal 0 ");
                return;
            }
            System.out.println("All values are valid. Starting simulation...");
            MutateGenome mutateGenome = null;

            try {
                mutateGenome = MutationFactory.createMutation(mutationStrategy.getValue(), minMutation, maxMutation,genomeLengthValue);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            AbstractWorldMap map = null;

            Vector2d leftDownBoundary = new Vector2d(xValue,yValue);
            switch (mapChoice.getSelectionModel().getSelectedItem()){
                case "Globe":
                    map = new GlobeMap(widthValue,heightValue, leftDownBoundary,energyFromPlantValue,new ClassicalEnergyLoss(energyLossValue),startsNumOfPlantsValue);
                break;
                case "Globe with pole":
                    map = new GlobeMap(widthValue,heightValue, leftDownBoundary,energyFromPlantValue,new PoleEnergyLoss(AbstractWorldMap.calculateEquator(leftDownBoundary,heightValue,widthValue),energyLossValue, energyLossPerMoveToPoleValue),startsNumOfPlantsValue);
                break;
                default:
                    throw new IllegalArgumentException("Nieznany typ: ");
            }



        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkIfAboveZero(List<Integer> integers) {
        return integers.stream().allMatch(x -> x>0);
    }

    private int validateIntegerField(TextField textField) {
        String value = textField.getText();
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Field cannot be empty.");
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Field must be a valid integer.");
        }
    }

}
