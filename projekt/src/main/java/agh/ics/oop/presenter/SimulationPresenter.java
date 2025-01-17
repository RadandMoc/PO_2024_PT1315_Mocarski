package agh.ics.oop.presenter;
import agh.ics.oop.Simulation;
import agh.ics.oop.fabric.MutationFactory;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
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
    private TextField startingAnimals;

    @FXML
    private TextField numOfPlants;

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

    private int sim_counter = 0;

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

    private void openNewWindow(AbstractWorldMap map, Simulation sim) {
        Platform.runLater(() -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("showSimulation.fxml"));
                Parent root = loader.load();

                ShowSimulationPresenter presenter = loader.getController();
                map.addObserver(presenter);

                Stage newStage = new Stage();
                newStage.setTitle("Simulation %d".formatted(sim_counter++));
                newStage.setScene(new Scene(root, 800, 600));
                newStage.show();

                new Thread(() -> {
                    for(int i = 0; i < 10; i++){
                        sim.run();
                    }
                    Platform.runLater(newStage::close);
                }).start();

            } catch (IOException ignored) {

            }
        });
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
            int startsNumOfPlantsValue = validateIntegerField(numOfPlants);
            int startingAnimalsValue = validateIntegerField(startingAnimals);


            boolean isAllAboveZero = checkIfAboveZero(List.of(heightValue, widthValue, energyFromPlantValue,
                    genomeLengthValue, energyForBeingFullStaffedValue, startingEnergyValue,
                    numOfNewPlantsPerTurnValue, breadingEnergyLossValue, energyLossPerMoveToPoleValue,
                    startsNumOfPlantsValue, startingAnimalsValue));
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
                    map = new GlobeMap(widthValue,heightValue, leftDownBoundary,energyFromPlantValue,
                            new ClassicalEnergyLoss(energyLossValue),startsNumOfPlantsValue);
                break;
                case "Globe with pole":
                    map = new GlobeMap(widthValue,heightValue, leftDownBoundary,energyFromPlantValue,
                            new PoleEnergyLoss(AbstractWorldMap.calculateEquator(leftDownBoundary,heightValue,widthValue),
                            energyLossValue, energyLossPerMoveToPoleValue),startsNumOfPlantsValue);
                break;
                default:
                    throw new IllegalArgumentException("Nieznany typ: ");
            }

            final AbstractWorldMap finalMap = map;

            final Simulation sim = new Simulation(map, startingAnimalsValue, startingEnergyValue,
                    energyForBeingFullStaffedValue, breadingEnergyLossValue, genomeLengthValue,
                    mutateGenome, numOfNewPlantsPerTurnValue,  new ClassicAnimalReproduction());

            new Thread(() -> {
                openNewWindow(finalMap, sim);
            }).start();



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
