package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {

    @Test
    public void forOptionsParser_callWhereMove_thenCheckIsCorrect(){
        // Given
        String[] text = new String[1];
        text[0] = "fbrl";
        MoveDirection[] correctAnswer = {MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.RIGHT,MoveDirection.LEFT};
        boolean isCorrect = true;
        MoveDirection[] funcAnswer;
        // When
        funcAnswer = OptionsParser.whereMove(text);
        for (int i = 0; i < funcAnswer.length; i++) {
            if(isCorrect){
                isCorrect = correctAnswer[i] == funcAnswer[i];
            }
        }
        // Then
        Assertions.assertTrue(isCorrect);
    }
}
