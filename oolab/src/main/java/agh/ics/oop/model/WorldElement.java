package agh.ics.oop.model;

public interface WorldElement {
    Vector2d getPosition();
    String getResourceName();
    String getTag(); // skoro i tak musiałem zmienić interfejs, to przy okazji dodanie nowej metody nie zaszkodzi, zwłaszcza gdy alternatywą jest sprawdzanie czy obiekt jest instancją czegoś, a jeśli nie jest żadną z poszukiwanych to pluć exception.
}
