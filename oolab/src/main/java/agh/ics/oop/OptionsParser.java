package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> whereMove(String[] directions){
        List<MoveDirection> result = new ArrayList<>();
        MoveDirection add;
        for (String text: directions){
            if(text.length()==1){
                add = whereAnimalMove(text.charAt(0));
                result.add(add);
            }
        }
        return result;
    }

    private static MoveDirection whereAnimalMove(char c)
    {
        return switch (c) {
            case 'f' -> MoveDirection.FORWARD;
            case 'b' -> MoveDirection.BACKWARD;
            case 'r' -> MoveDirection.RIGHT;
            case 'l' -> MoveDirection.LEFT;
            default ->  throw new IllegalArgumentException(c + " is not legal move specification");
        };
    }
}
