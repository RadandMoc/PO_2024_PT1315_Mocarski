package agh.ics.oop.model;

public record Plant(Vector2d position) implements WorldElement {

    @Override
    public void updateWorldElementBox(WorldElementBox fieldBox) {
        fieldBox.UpdateForPlant();
    }

    @Override
    public String toString() {
        return "*";
    }

}
