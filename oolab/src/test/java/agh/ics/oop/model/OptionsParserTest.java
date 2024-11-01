package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class OptionsParserTest {

    @Test
    public void forOptionsParser_callWhereMove_thenCheckLengthOfAnswersArrayIsCorrect(){
        // Given
        String[] text = new String[]{
            "f","aa","b","c","r","ll","l","y"
        };
        int correctLength = 4;
        List<MoveDirection> funcAnswer;
        // When
        funcAnswer = OptionsParser.whereMove(text);
        // Then
        assertEquals(correctLength,funcAnswer.size());
    }

    @Test
    public void forOptionsParser_callWhereMove_thenCheckValuesOfArrayAreCorrect(){
        // Given
        String[] text = new String[]{
                "f","aa","b","c","r","ll","l","y"
        };
        List<MoveDirection> correctAnswer = new ArrayList<>();
        correctAnswer.add(MoveDirection.FORWARD);
        correctAnswer.add(MoveDirection.BACKWARD);
        correctAnswer.add(MoveDirection.RIGHT);
        correctAnswer.add(MoveDirection.LEFT);
        List<MoveDirection> funcAnswer;
        // When
        funcAnswer = OptionsParser.whereMove(text);
        // Then
        assertEquals(correctAnswer,funcAnswer);
    }
}
