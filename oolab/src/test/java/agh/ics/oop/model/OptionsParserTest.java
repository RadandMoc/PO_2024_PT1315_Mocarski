package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {

    @Test
    public void forOptionsParser_callWhereMove_thenCheckLengthOfAnswersArrayIsCorrect(){
        // Given
        String[] text = new String[1];
        text[0] = "fabcrely";
        int correctLength = 4;
        MoveDirection[] funcAnswer;
        // When
        funcAnswer = OptionsParser.whereMove(text);
        // Then
        assertEquals(correctLength,funcAnswer.length);
    }

    @Test
    public void forOptionsParser_callWhereMove_thenCheckValuesOfArrayAreCorrect(){
        // Given
        String[] text = new String[1];
        text[0] = "fabcrely";
        MoveDirection[] correctAnswer = {MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.RIGHT,MoveDirection.LEFT};
        boolean isCorrect = true;
        MoveDirection[] funcAnswer;
        // When
        funcAnswer = OptionsParser.whereMove(text);
        for (int i = 0; i < correctAnswer.length; i++) {
            if(isCorrect){
                isCorrect = correctAnswer[i] == funcAnswer[i];
            }
        }
        // Then
        assertTrue(isCorrect);
    }
}
