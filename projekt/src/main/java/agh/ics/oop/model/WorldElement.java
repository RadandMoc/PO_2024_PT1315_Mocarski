package agh.ics.oop.model;

public interface WorldElement {
    Vector2d position();

    void updateWorldElementBox(WorldElementBox fieldBox);
}
