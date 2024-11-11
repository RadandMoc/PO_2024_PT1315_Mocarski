package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

// Kazano zaimplementować, ale nie określono, że P ma być jakimś konkretnym (lub uniwersalnym) typem, więz założyłem że będzie intem
public class TextMap implements WorldNumberPositionMap<String, Integer> {
    private final List<String> texts = new ArrayList<>();

    @Override
    public boolean place(String text) {
        if (text == null)
            return false;
        texts.add(text);
        return true;
    }

    @Override
    public void move(String text, MoveDirection direction) {
        int index = -1;

        for (int i = 0; i < texts.size(); i++) {
            if (texts.get(i) == text) {
                index = i;
                break;
            }
        }
        if (index == -1){
            index = texts.indexOf(text);
            if (index == -1)
                return;
        }
        if (direction == MoveDirection.RIGHT || direction == MoveDirection.FORWARD) {
            if (index < texts.size() - 1)
                swap(index, index + 1);
        } else if (direction == MoveDirection.LEFT || direction == MoveDirection.BACKWARD) {
            if (index > 0)
                swap(index, index - 1);
        }
    }

    private void swap(int i, int j) {
        String assist = texts.get(i);
        texts.set(i, texts.get(j));
        texts.set(j, assist);
    }

    @Override
    public boolean isOccupied(Integer position) {
        return position >= 0 && position < texts.size();
    }

    @Override
    public String objectAt(Integer position) {
        return isOccupied(position) ? texts.get(position) : null;
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return isOccupied(position);
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        for(String text : texts)
            res.append(text).append("  ");
        return res.toString();
    }
}
