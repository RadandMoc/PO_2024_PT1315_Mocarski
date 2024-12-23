package agh.ics.oop.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Vector2dTest {

    @Test
    public void equalsForSameVectors(){
        // Given
        Vector2d vec = new Vector2d(243,-271);
        Vector2d copy;
        // When
        copy = vec;
        // Then
        assertEquals(vec,copy);
    }

    @Test
    public void equalsForEqualsVectors(){
        // Given
        Vector2d vec1 = new Vector2d(243,-271);
        Vector2d vec2 = new Vector2d(243,-271);
        // When

        // Then
        assertNotEquals(vec1,vec2);
    }

    @Test
    public void equalsForNotequalsVectors(){
        // Given
        Vector2d vec1 = new Vector2d(243,-271);
        Vector2d vec2 = new Vector2d(-243,271);
        Vector2d vec3 = new Vector2d(2,1);
        Vector2d vec4 = new Vector2d(1,38);
        // When

        // Then
        assertNotEquals(vec1,vec2);
        assertNotEquals(vec1,vec3);
        assertNotEquals(vec1,vec4);
        assertNotEquals(vec2,vec3);
        assertNotEquals(vec2,vec4);
        assertNotEquals(vec3,vec4);
    }

    @Test
    public void checkToString(){
        // Given
        Vector2d vec = new Vector2d(12,21);
        String correct_text = "(12,21)";
        String funcAnswer;
        // When
        funcAnswer = vec.toString();
        // Then
        assertEquals(correct_text, funcAnswer);
    }

    @Test
    public void precedesForSameValues(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(12,21);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.precedes(vec2);
        // Then
        assertTrue(funcAnswer);
    }

    @Test
    public void precedesForCorrectData(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(13,22);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.precedes(vec2);
        // Then
        assertTrue(funcAnswer);
    }

    @Test
    public void precedesForNotCorrectData(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(11,20);
        Vector2d vec3 = new Vector2d(11,22);
        Vector2d vec4 = new Vector2d(13,20);
        boolean bothWrong;
        boolean firstWrong;
        boolean secWrong;
        // When
        bothWrong = vec1.precedes(vec2);
        firstWrong = vec1.precedes(vec3);
        secWrong = vec1.precedes(vec4);
        // Then
        assertFalse(bothWrong);
        assertFalse(firstWrong);
        assertFalse(secWrong);
    }

    @Test
    public void correctFollows(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(12,21);
        Vector2d vec3 = new Vector2d(11,20);
        boolean vecEquals;
        boolean vecSmaller;
        // When
        vecEquals = vec1.follows(vec2);
        vecSmaller = vec1.follows(vec3);
        // Then
        assertTrue(vecEquals);
        assertTrue(vecSmaller);
    }

    @Test
    public void incorrectFollows(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(13,22);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.follows(vec2);
        // Then
        assertFalse(funcAnswer);
    }

    @Test
    public void upperRightTests(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d vec2 = new Vector2d(3,1);
        Vector2d vec3 = new Vector2d(0,0);
        Vector2d vec4 = new Vector2d(-1,-2);
        Vector2d vec5 = new Vector2d(-2,-1);
        Vector2d vecs_1_2;
        // When
        vecs_1_2 = vec1.upperRight(vec2);
        vecs_1_3 = vec1.upperRight(vec2);
        vecs_1_4 = vec1.upperRight(vec2);
        vecs_1_5 = vec1.upperRight(vec2);
        // Then
        assertEquals(new Vector2d(3,3),vecs_1_2);
    }

    @Test
    public void forVectorsWithDifferentValues_callLowerLeft_thenCompareToProperlyVector(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d vec2 = new Vector2d(3,1);
        Vector2d funcAnswer;
        // When
        funcAnswer = vec1.lowerLeft(vec2);
        // Then
        assertEquals(new Vector2d(1,1),funcAnswer);
    }

    @Test
    public void forVectorsWithDifferentValues_callAdd_thenCompareToProperlyVector(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d vec2 = new Vector2d(3,1);
        Vector2d funcAnswer;
        // When
        funcAnswer = vec1.add(vec2);
        // Then
        assertEquals(new Vector2d(4,4),funcAnswer);
    }

    @Test
    public void forVectorsWithDifferentValues_callSubtract_thenCompareToProperlyVector(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d vec2 = new Vector2d(3,1);
        Vector2d funcAnswer;
        // When
        funcAnswer = vec1.subtract(vec2);
        // Then
        assertEquals(new Vector2d(-2,2),funcAnswer);
    }

    @Test
    public void forVectorsWithDifferentValues_callOpposite_thenCompareToProperlyVector(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d funcAnswer;
        // When
        funcAnswer = vec1.opposite();
        // Then
        assertEquals(new Vector2d(-1,-3),funcAnswer);
    }
}
