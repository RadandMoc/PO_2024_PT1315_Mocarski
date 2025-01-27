package agh.ics.oop.presenter;
import agh.ics.oop.Simulation;
import agh.ics.oop.fabric.MutationFactory;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.Math.min;

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

    @FXML
    private Button saveConfigButton;

    @FXML
    private Button loadConfigButton;

    @FXML
    private Spinner<Integer> minMutationSpinner;

    @FXML
    private Spinner<Integer> maxMutationSpinner;

    @FXML
    private CheckBox wantLogsCbx;

    private int sim_counter = 0;

    private ConcurrentMap<Stage, Thread> simulationThreads = new ConcurrentHashMap<>();

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

        mutationStrategy.getSelectionModel().select(MutationStrategy.RandomMutate);

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
                sim.addObserver(presenter);

                presenter.setSimulation(sim);

                Stage newStage = new Stage();
                newStage.setTitle("Simulation %d".formatted(sim_counter++));
                newStage.setScene(new Scene(root, 800, 600));
                newStage.show();

                Thread simulationThread = new Thread(() -> {
                    for(int i = 0; i < 100000; i++){
                        if (Thread.currentThread().isInterrupted()) {
                            return;
                        }

                        synchronized(presenter.getPauseLock()) {
                            while (presenter.isPaused()) {
                                try {
                                    presenter.getPauseLock().wait();
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }
                        }

                        try {
                            sim.run();
                            Thread.sleep(presenter.getTimeDelayBetweenTurns());
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    Platform.runLater(newStage::close);
                });

                presenter.setSimulationThread(simulationThread);

                simulationThreads.put(newStage, simulationThread);

                newStage.setOnCloseRequest(event -> {
                    Thread thread = simulationThreads.remove(newStage);
                    if (thread != null) {
                        thread.interrupt();
                    }
                });

                simulationThread.start();

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

            int startsNumOfPlantsValue = validateIntegerField(numOfPlants);
            int startingAnimalsValue = validateIntegerField(startingAnimals);


            boolean isAllAboveZero = checkIfAboveZero(List.of(heightValue, widthValue, energyFromPlantValue,
                    energyForBeingFullStaffedValue, startingEnergyValue,
                    numOfNewPlantsPerTurnValue, breadingEnergyLossValue,
                    startsNumOfPlantsValue, startingAnimalsValue));
            if (!isAllAboveZero){
                showError("Please correct variable with value below or equal 0 ");
                return;
            }
            if(genomeLengthValue<2){
                showError("Please entry genome length value upper than 1");
                return;
            }

            int energyLossPerMoveToPoleValue = 0;
            if (energyLossPerMoveToPole.isVisible()){
                energyLossPerMoveToPoleValue = validateIntegerField(energyLossPerMoveToPole);
                if (energyLossPerMoveToPoleValue <= 0){
                    return;
                }
            }
            MutateGenome mutateGenome = null;

            try {
                mutateGenome = MutationFactory.createMutation(mutationStrategy.getValue(), minMutationSpinner.getValue(), maxMutationSpinner.getValue());
            }
            catch (IllegalArgumentException e){
                mutateGenome = MutationFactory.createMutation(mutationStrategy.getValue(), minMutationSpinner.getValue(), min(maxMutationSpinner.getValue(),genomeLengthValue));
                showError(e.getMessage());
            }

            AbstractWorldMap map = null;

            Vector2d leftDownBoundary = new Vector2d(xValue,yValue);
            map = switch (mapChoice.getSelectionModel().getSelectedItem()) {
                case "Globe" -> new GlobeMap(widthValue, heightValue, leftDownBoundary, energyFromPlantValue,
                        new ClassicalEnergyLoss(energyLossValue), startsNumOfPlantsValue);
                case "Globe with pole" -> new GlobeMap(widthValue, heightValue, leftDownBoundary, energyFromPlantValue,
                        new PoleEnergyLoss(AbstractWorldMap.calculateEquator(leftDownBoundary, heightValue, widthValue),
                                energyLossValue, energyLossPerMoveToPoleValue), startsNumOfPlantsValue);
                default -> throw new IllegalArgumentException("Nieznany typ: ");
            };

            final AbstractWorldMap finalMap = map;

            final Simulation sim = new Simulation(map, startingAnimalsValue, startingEnergyValue,
                    energyForBeingFullStaffedValue, breadingEnergyLossValue, genomeLengthValue,
                    mutateGenome, numOfNewPlantsPerTurnValue,  new ClassicAnimalReproduction(), wantLogsCbx.isSelected());
                new Thread(() -> {
                openNewWindow(finalMap, sim);
            }).start();



        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            showError(e.getMessage());
        }
    }

    @FXML
    private void onSaveConfigClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Config Files", "*.config"));
        Stage stage = (Stage) saveConfigButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        int energyLossPerMoveToPoleValue = 0;
        if (!energyLossPerMoveToPole.getText().trim().isEmpty()) {
            energyLossPerMoveToPoleValue = validateIntegerField(energyLossPerMoveToPole);
        }
        if (file != null) {
            try {
                PresenterConfigurationSave config = new PresenterConfigurationSave(
                        validateIntegerField(height),
                        validateIntegerField(width),
                        validateIntegerField(x),
                        validateIntegerField(y),
                        validateIntegerField(energyFromPlant),
                        validateIntegerField(genomeLength),
                        validateIntegerField(energyForBeingFullStaffed),
                        validateIntegerField(startingEnergy),
                        validateIntegerField(numOfNewPlantsPerTurn),
                        validateIntegerField(breadingEnergyLoss),
                        validateIntegerField(energyLoss),
                        energyLossPerMoveToPoleValue,
                        validateIntegerField(numOfPlants),
                        validateIntegerField(startingAnimals),
                        mapChoice.getValue(),
                        mutationStrategy.getValue(),
                        (int) minMutationSpinner.getValue(),
                        (int) maxMutationSpinner.getValue(),
                        wantLogsCbx.isSelected()
                );
                config.save(file.getPath());
            } catch (IOException e) {
                showError("Error saving configuration: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                showError("Invalid input: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onLoadConfigClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Config Files", "*.config"));
        Stage stage = (Stage) loadConfigButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                PresenterConfigurationSave config = PresenterConfigurationSave.load(file.getPath());
                height.setText(String.valueOf(config.heightValue()));
                width.setText(String.valueOf(config.widthValue()));
                x.setText(String.valueOf(config.xValue()));
                y.setText(String.valueOf(config.yValue()));
                energyFromPlant.setText(String.valueOf(config.energyFromPlantValue()));
                genomeLength.setText(String.valueOf(config.genomeLengthValue()));
                energyForBeingFullStaffed.setText(String.valueOf(config.energyForBeingFullStaffedValue()));
                startingEnergy.setText(String.valueOf(config.startingEnergyValue()));
                numOfNewPlantsPerTurn.setText(String.valueOf(config.numOfNewPlantsPerTurnValue()));
                breadingEnergyLoss.setText(String.valueOf(config.breadingEnergyLossValue()));
                energyLoss.setText(String.valueOf(config.energyLossValue()));
                energyLossPerMoveToPole.setText(String.valueOf(config.energyLossPerMoveToPoleValue()));
                numOfPlants.setText(String.valueOf(config.startsNumOfPlantsValue()));
                startingAnimals.setText(String.valueOf(config.startingAnimalsValue()));
                mapChoice.setValue(config.mapChoice());
                mutationStrategy.setValue(config.mutationStrategy());
                minMutationSpinner.getValueFactory().setValue(config.minMutation());
                maxMutationSpinner.getValueFactory().setValue(config.maxMutation());
                wantLogsCbx.setSelected(config.saveStatistics());
            } catch (IOException | ClassNotFoundException e) {
                showError("Error loading configuration: " + e.getMessage());
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
