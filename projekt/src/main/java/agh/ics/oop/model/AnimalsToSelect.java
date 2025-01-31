package agh.ics.oop.model;


import java.util.List;

import javafx.scene.control.ListView;

public class AnimalsToSelect {
    private final ListView<Animal> listView = new ListView<>();


    public AnimalsToSelect(List<Animal> animals) {
        listView.getItems().addAll(animals);
        listView.setPrefSize(200, 300);
    }

    public ListView<Animal> getListView() {
        return listView;
    }

}
