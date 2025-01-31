package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionsParserTest {

    @Test
    void convertAgrumentsTableToDirection_AllValidDirections_ReturnsCorrectMoveDirections() {
        // given
        String[] moves = {"f", "b", "r", "l"};
        List<MoveDirection> expected = Arrays.asList(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT
        );

        // when
        List<MoveDirection> result = OptionsParser.whereMove(moves);

        // then
        assertEquals(expected, result);
    }

    @Test
    void convertAgrumentsTableToDirection_EmptyArray_ReturnsEmptyMoveDirections() {
        // given
        String[] moves = {};
        List<MoveDirection> expected = Arrays.asList();

        // when
        List<MoveDirection> result = OptionsParser.whereMove(moves);

        // then
        assertEquals(expected, result);
    }

    @Test
    void convertAgrumentsTableToDirection_InvalidDirections_IgnoresInvalidEntries() {
        // given
        String[] moves = {"f", "x", "r", "y"};

        // when

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.whereMove(moves);
        });

        // then

        assertEquals("x is not legal move", exception.getMessage());
    }

    @Test
    void convertArgumentsTableToDirection_AllInvalidDirections_ThrowsIllegalArgumentException() {
        // given
        String[] moves = {"x", "y", "z"};

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.whereMove(moves);
        });

        assertEquals("x is not legal move", exception.getMessage());
    }
}
