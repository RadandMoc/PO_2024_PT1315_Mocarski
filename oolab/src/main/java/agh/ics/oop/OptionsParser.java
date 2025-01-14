package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionsParser {
    public static List<MoveDirection> whereMove(String[] directions){
        return Stream.of(directions)
                .map(OptionsParser::parseDirection)
                .collect(Collectors.toList());
    }

    private static MoveDirection parseDirection(String direction) {
        return switch (direction) {
            case "f" -> MoveDirection.FORWARD;
            case "b" -> MoveDirection.BACKWARD;
            case "r" -> MoveDirection.RIGHT;
            case "l" -> MoveDirection.LEFT;
            default -> throw new IllegalArgumentException("Unknown direction: " + direction);
        };
    }
}
