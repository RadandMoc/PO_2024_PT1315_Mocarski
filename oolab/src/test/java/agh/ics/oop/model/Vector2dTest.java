package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {

    @Test
    public void forTheSameVector_callEquals_thenCheckIsReturnedTrue(){
        // Given
        Vector2d vec = new Vector2d(243,-271);
        Vector2d copy;
        // When
        copy = vec;
        // Then
        Assertions.assertEquals(vec,copy);
    }

    @Test
    public void forOtherVectorsWithTheSameValue_callEquals_thenCheckIsReturnedTrue(){
        // Given
        Vector2d vec1 = new Vector2d(243,-271);
        Vector2d vec2 = new Vector2d(243,-271);
        boolean are_references_equal;
        // When
        are_references_equal = vec1 == vec2;
        // Then
        Assertions.assertTrue(vec1.equals(vec2) && (!are_references_equal));
    }

    @Test
    public void forOtherVectorsWithOtherValues_callEquals_thenCheckIsReturnedFalse(){
        // Given
        Vector2d vec1 = new Vector2d(243,-271);
        Vector2d vec2 = new Vector2d(-243,271);
        Vector2d vec3 = new Vector2d(2,1);
        Vector2d vec4 = new Vector2d(1,38);
        boolean are_references_equal;
        // When
        are_references_equal = vec1 == vec2 || vec2 == vec3 || vec3 == vec4;
        // Then
        Assertions.assertFalse(vec1.equals(vec2) || vec3.equals(vec4) || are_references_equal || vec2.equals(vec3));
    }

    @Test
    public void forVector_callToString_thenCheckResultAndCompareToProperlyText(){
        // Given
        Vector2d vec = new Vector2d(12,21);
        String correct_text = "(12,21)";
        String funcAnswer;
        // When
        funcAnswer = vec.toString();
        // Then
        Assertions.assertTrue(correct_text.equals(funcAnswer));
    }

    @Test
    public void forVectorsWithTheSameValues_callPrecedes_thenCheckIsReturnedTrue(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(12,21);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.precedes(vec2);
        // Then
        Assertions.assertTrue(funcAnswer);
    }

    @Test
    public void forVectorsWithDifferentValues_callPrecedes_thenCheckIsReturnedTrue(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(13,22);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.precedes(vec2);
        // Then
        Assertions.assertTrue(funcAnswer);
    }

    @Test
    public void forVectorsWithDifferentValues_callPrecedes_thenCheckIsReturnedFalse(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(11,20);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.precedes(vec2);
        // Then
        Assertions.assertFalse(funcAnswer);
    }

    @Test
    public void forVectorsWithTheSameValues_callFollows_thenCheckIsReturnedTrue(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(12,21);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.follows(vec2);
        // Then
        Assertions.assertTrue(funcAnswer);
    }

    @Test
    public void forVectorsWithDifferentValues_callFollows_thenCheckIsReturnedTrue(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(11,20);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.follows(vec2);
        // Then
        Assertions.assertTrue(funcAnswer);
    }

    @Test
    public void forVectorsWithDifferentValues_callFollows_thenCheckIsReturnedFalse(){
        // Given
        Vector2d vec1 = new Vector2d(12,21);
        Vector2d vec2 = new Vector2d(13,22);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.follows(vec2);
        // Then
        Assertions.assertFalse(funcAnswer);
    }

    @Test
    public void forVectorsWithDifferentValues_callUpperRight_thenCompareToWithProperlyVector(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d vec2 = new Vector2d(3,1);
        Vector2d funcAnswer;
        // When
        funcAnswer = vec1.upperRight(vec2);
        // Then
        Assertions.assertEquals(funcAnswer, new Vector2d(3,3));
    }

    @Test
    public void forVectorsWithDifferentValues_callLowerLeft_thenCompareToWithProperlyVector(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d vec2 = new Vector2d(3,1);
        Vector2d funcAnswer;
        // When
        funcAnswer = vec1.lowerLeft(vec2);
        // Then
        Assertions.assertEquals(funcAnswer, new Vector2d(1,1));
    }

    @Test
    public void forVectorsWithDifferentValues_callAdd_thenCompareToWithProperlyVector(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d vec2 = new Vector2d(3,1);
        Vector2d funcAnswer;
        // When
        funcAnswer = vec1.add(vec2);
        // Then
        Assertions.assertEquals(funcAnswer, new Vector2d(4,4));
    }

    @Test
    public void forVectorsWithDifferentValues_callSubtract_thenCompareToWithProperlyVector(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d vec2 = new Vector2d(3,1);
        Vector2d funcAnswer;
        // When
        funcAnswer = vec1.subtract(vec2);
        // Then
        Assertions.assertEquals(funcAnswer, new Vector2d(-2,2));
    }

    @Test
    public void forVectorsWithDifferentValues_callOpposite_thenCompareToWithProperlyVector(){
        // Given
        Vector2d vec1 = new Vector2d(1,3);
        Vector2d funcAnswer;
        // When
        funcAnswer = vec1.opposite();
        // Then
        Assertions.assertEquals(funcAnswer, new Vector2d(-1,-3));
    }
}
