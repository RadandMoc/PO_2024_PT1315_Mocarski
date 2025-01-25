package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.SimTurnListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimalViewPresenter implements SimTurnListener {
    Animal animal;
    @FXML
    Label genome;
    @FXML
    Label activateGenome;
    @FXML
    Label energy;
    @FXML
    Label eatenPlants;
    @FXML
    Label children;
    @FXML
    Label descendants;
    @FXML
    Label lifetime;
    @FXML
    Label turnOfDeath;

    @FXML
    ImageView animalImage = new ImageView();
    {
        animalImage.setImage(new Image(getClass().getResourceAsStream("/animal.png")));
    }

    @Override
    public void onNewTurnChange(Simulation sim) {
        Platform.runLater(this::setLabels);
    }

    private void setLabels() {
        String animalGenome = animal.getGenome();

        int genomeIdx = animal.getGenomeIdx();
        genome.setText("genom zwierzaka %s".formatted(animal.getGenome()));
        activateGenome.setText("indeks %d, wartosc %c".formatted(genomeIdx, animalGenome.charAt(genomeIdx)));
        energy.setText("energia zwierzaka %d".formatted(animal.getEnergy()));
        eatenPlants.setText("zjedzone rosliny %d".formatted(animal.getEatenGrass()));
        children.setText("liczba dzieci %d".formatted(animal.getNumOfChild()));
        descendants.setText("liczba potomków %d".formatted(animal.getAllDescendants().size()));
        lifetime.setText("dlugosc zycia %d".formatted(animal.getLifeTime()));
        turnOfDeath.setText("Tura śmierci: zwierze zyje");
        if (animal.getIsDead()){
            turnOfDeath.setText("Tura śmierci: %d".formatted(animal.getTurnOfDeath()));
        }
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
