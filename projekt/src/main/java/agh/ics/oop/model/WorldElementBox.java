package agh.ics.oop.model;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class WorldElementBox {
    private static final double IMAGE_WIDTH = 20;
    private static final double IMAGE_HEIGHT = 20;

    private static final Map<String, Image> imageCache = new HashMap<>();

    private final VBox vBox;

    @FXML
    private Label energyLabel;
    private final ImageView imageView;




    public WorldElementBox(WorldElement element) {
        imageView = new ImageView();
        imageView.setFitWidth(IMAGE_WIDTH);
        imageView.setFitHeight(IMAGE_HEIGHT);

        vBox = new VBox(5);
        vBox.getChildren().addAll(imageView, energyLabel);
        vBox.setAlignment(Pos.CENTER);
        element.updateWorldElementBox(this);
    }


    private Image loadImage(String imagePath) {
        if (imageCache.containsKey(imagePath)) {
            return imageCache.get(imagePath);
        }

        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream == null) {
            System.err.println("Nie można znaleźć obrazka: " + imagePath);
            throw new IllegalArgumentException("Nie można znaleźć obrazka: " + imagePath);
        }
        Image image = new Image(imageStream);
        imageCache.put(imagePath, image);
        return image;
    }


    public VBox getVBox() {
        return vBox;
    }

    public void UpdateForAnimal(int energy){
        imageView.setImage(loadImage("/animal.png"));
        energyLabel.setText("%d".formatted(energy));

    }

    public void UpdateForPlant(){
        loadImage("/plant.png");
        energyLabel.setText("");
    }


    public void setNull(){
        imageView.setImage(null);
        energyLabel.setText("");
    }


}