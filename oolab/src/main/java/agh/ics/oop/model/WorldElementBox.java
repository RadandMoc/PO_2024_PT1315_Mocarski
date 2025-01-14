package agh.ics.oop.model;

import javafx.scene.layout.VBox;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class WorldElementBox extends VBox {

    public WorldElementBox(WorldElement element) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(element.getResourceName())));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Label positionLabel = new Label(element.getTag());
        this.getChildren().addAll(imageView, positionLabel);
        this.setAlignment(Pos.CENTER);
    }
}
