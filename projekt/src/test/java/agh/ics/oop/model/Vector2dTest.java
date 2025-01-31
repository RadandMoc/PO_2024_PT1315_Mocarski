package agh.ics.oop.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Vector2dTest {

    @Test
    public void equalsForSameVectors() {
        // Given
        Vector2d vec = new Vector2d(243, -271);
        Vector2d copy;
        // When
        copy = vec;
        // Then
        assertEquals(vec, copy);
    }

    @Test
    public void equalsForEqualsVectors() {
        // Given
        Vector2d vec1 = new Vector2d(243, -271);
        Vector2d vec2 = new Vector2d(243, -271);
        // When

        // Then
        assertEquals(vec1, vec2);
    }

    @Test
    public void equalsForNotEqualsVectors() {
        // Given
        Vector2d vec1 = new Vector2d(243, -271);
        Vector2d vec2 = new Vector2d(-243, 271);
        Vector2d vec3 = new Vector2d(2, 1);
        Vector2d vec4 = new Vector2d(1, 38);
        // When

        // Then
        assertNotEquals(vec1, vec2);
        assertNotEquals(vec1, vec3);
        assertNotEquals(vec1, vec4);
        assertNotEquals(vec2, vec3);
        assertNotEquals(vec2, vec4);
        assertNotEquals(vec3, vec4);
    }

    @Test
    public void checkToString() {
        // Given
        Vector2d vec = new Vector2d(12, 21);
        String correct_text = "(12,21)";
        String funcAnswer;
        // When
        funcAnswer = vec.toString();
        // Then
        assertEquals(correct_text, funcAnswer);
    }

    @Test
    public void precedesForSameValues() {
        // Given
        Vector2d vec1 = new Vector2d(12, 21);
        Vector2d vec2 = new Vector2d(12, 21);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.precedes(vec2);
        // Then
        assertTrue(funcAnswer);
    }

    @Test
    public void precedesForCorrectData() {
        // Given
        Vector2d vec1 = new Vector2d(12, 21);
        Vector2d vec2 = new Vector2d(13, 22);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.precedes(vec2);
        // Then
        assertTrue(funcAnswer);
    }

    @Test
    public void precedesForNotCorrectData() {
        // Given
        Vector2d vec1 = new Vector2d(12, 21);
        Vector2d vec2 = new Vector2d(11, 20);
        Vector2d vec3 = new Vector2d(11, 22);
        Vector2d vec4 = new Vector2d(13, 20);
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
    public void correctFollows() {
        // Given
        Vector2d vec1 = new Vector2d(12, 21);
        Vector2d vec2 = new Vector2d(12, 21);
        Vector2d vec3 = new Vector2d(11, 20);
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
    public void incorrectFollows() {
        // Given
        Vector2d vec1 = new Vector2d(12, 21);
        Vector2d vec2 = new Vector2d(13, 22);
        boolean funcAnswer;
        // When
        funcAnswer = vec1.follows(vec2);
        // Then
        assertFalse(funcAnswer);
    }

    @Test
    public void upperRightTests() {
        // Given
        Vector2d vec1 = new Vector2d(1, 3);
        Vector2d vec2 = new Vector2d(3, 1);
        Vector2d vec3 = new Vector2d(0, 0);
        Vector2d vec4 = new Vector2d(-1, -2);
        Vector2d vec5 = new Vector2d(-2, -1);
        Vector2d ans_vecs_1_2;
        Vector2d ans_vecs_1_3;
        Vector2d ans_vecs_1_4;
        Vector2d ans_vecs_3_1;
        Vector2d ans_vecs_3_4;
        Vector2d ans_vecs_3_5;
        Vector2d ans_vecs_4_5;
        // When
        ans_vecs_1_2 = vec1.upperRight(vec2);
        ans_vecs_1_3 = vec1.upperRight(vec3);
        ans_vecs_1_4 = vec1.upperRight(vec4);
        ans_vecs_3_1 = vec3.upperRight(vec1);
        ans_vecs_3_4 = vec3.upperRight(vec4);
        ans_vecs_3_5 = vec3.upperRight(vec5);
        ans_vecs_4_5 = vec4.upperRight(vec5);
        // Then
        assertEquals(new Vector2d(3, 3), ans_vecs_1_2);
        assertEquals(new Vector2d(1, 3), ans_vecs_1_3);
        assertEquals(new Vector2d(1, 3), ans_vecs_1_4);
        assertEquals(new Vector2d(1, 3), ans_vecs_3_1);
        assertEquals(new Vector2d(0, 0), ans_vecs_3_4);
        assertEquals(new Vector2d(0, 0), ans_vecs_3_5);
        assertEquals(new Vector2d(-1, -1), ans_vecs_4_5);
    }

    @Test
    public void lowerLeftTests() {
        // Given
        Vector2d vec1 = new Vector2d(1, 3);
        Vector2d vec2 = new Vector2d(3, 1);
        Vector2d vec3 = new Vector2d(0, 0);
        Vector2d vec4 = new Vector2d(-1, -2);
        Vector2d vec5 = new Vector2d(-2, -1);
        Vector2d ans_vecs_1_2;
        Vector2d ans_vecs_1_3;
        Vector2d ans_vecs_1_4;
        Vector2d ans_vecs_3_1;
        Vector2d ans_vecs_3_4;
        Vector2d ans_vecs_3_5;
        Vector2d ans_vecs_4_5;
        // When
        ans_vecs_1_2 = vec1.lowerLeft(vec2);
        ans_vecs_1_3 = vec1.lowerLeft(vec3);
        ans_vecs_1_4 = vec1.lowerLeft(vec4);
        ans_vecs_3_1 = vec3.lowerLeft(vec1);
        ans_vecs_3_4 = vec3.lowerLeft(vec4);
        ans_vecs_3_5 = vec3.lowerLeft(vec5);
        ans_vecs_4_5 = vec4.lowerLeft(vec5);
        // Then
        assertEquals(new Vector2d(1, 1), ans_vecs_1_2);
        assertEquals(new Vector2d(0, 0), ans_vecs_1_3);
        assertEquals(new Vector2d(-1, -2), ans_vecs_1_4);
        assertEquals(new Vector2d(0, 0), ans_vecs_3_1);
        assertEquals(new Vector2d(-1, -2), ans_vecs_3_4);
        assertEquals(new Vector2d(-2, -1), ans_vecs_3_5);
        assertEquals(new Vector2d(-2, -2), ans_vecs_4_5);
    }

    @Test
    public void addVectorsTests() {
        // Given
        Vector2d vec1 = new Vector2d(1, 3);
        Vector2d vec2 = new Vector2d(3, 1);
        Vector2d vec3 = new Vector2d(-1, -1);
        Vector2d ans_vecs_1_2;
        Vector2d ans_vecs_2_1;
        Vector2d ans_vecs_1_3;
        // When
        ans_vecs_1_2 = vec1.add(vec2);
        ans_vecs_2_1 = vec2.add(vec1);
        ans_vecs_1_3 = vec1.add(vec3);
        // Then
        assertEquals(new Vector2d(4, 4), ans_vecs_1_2);
        assertEquals(new Vector2d(4, 4), ans_vecs_2_1);
        assertEquals(new Vector2d(0, 2), ans_vecs_1_3);
    }

    @Test
    public void subtractVectorsTests() {
        // Given
        Vector2d vec1 = new Vector2d(1, 3);
        Vector2d vec2 = new Vector2d(3, 1);
        Vector2d vec3 = new Vector2d(-1, -1);
        Vector2d ans_vecs_1_2;
        Vector2d ans_vecs_2_1;
        Vector2d ans_vecs_1_3;
        // When
        ans_vecs_1_2 = vec1.subtract(vec2);
        ans_vecs_2_1 = vec2.subtract(vec1);
        ans_vecs_1_3 = vec1.subtract(vec3);
        // Then
        assertEquals(new Vector2d(-2, 2), ans_vecs_1_2);
        assertEquals(new Vector2d(2, -2), ans_vecs_2_1);
        assertEquals(new Vector2d(2, 4), ans_vecs_1_3);
    }

    @Test
    public void oppositVectorsTests() {
        // Given
        Vector2d vec1 = new Vector2d(1, 3);
        Vector2d vec2 = new Vector2d(0, 0);
        Vector2d vec3 = new Vector2d(-1, -3);
        Vector2d vec4 = new Vector2d(-1, 3);
        Vector2d ans_vec_1;
        Vector2d ans_vec_2;
        Vector2d ans_vec_3;
        Vector2d ans_vec_4;
        // When
        ans_vec_1 = vec1.opposite();
        ans_vec_2 = vec2.opposite();
        ans_vec_3 = vec3.opposite();
        ans_vec_4 = vec4.opposite();
        // Then
        assertEquals(new Vector2d(-1, -3), ans_vec_1);
        assertEquals(new Vector2d(0, 0), ans_vec_2);
        assertEquals(new Vector2d(1, 3), ans_vec_3);
        assertEquals(new Vector2d(1, -3), ans_vec_4);
    }

    @Test
    public void isVectorInBoundaryTest() {
        // Given
        Vector2d vectorIn1 = new Vector2d(1, 1);
        Vector2d vectorIn2 = new Vector2d(0, 0);
        Vector2d vectorIn3 = new Vector2d(0, 2);
        Vector2d vectorIn4 = new Vector2d(2, 0);
        Vector2d vectorIn5 = new Vector2d(2, 2);
        Vector2d vectorOut1 = new Vector2d(-1, 1);
        Vector2d vectorOut2 = new Vector2d(1, -1);
        Vector2d vectorOut3 = new Vector2d(3, 1);
        Vector2d vectorOut4 = new Vector2d(1, 3);
        Vector2d vectorOut5 = new Vector2d(3, 3);
        Boundary boundary = new Boundary(new Vector2d(0, 0), new Vector2d(2, 2));

        // When
        boolean ans1 = boundary.isVectorIn(vectorIn1);
        boolean ans2 = boundary.isVectorIn(vectorIn2);
        boolean ans3 = boundary.isVectorIn(vectorIn3);
        boolean ans4 = boundary.isVectorIn(vectorIn4);
        boolean ans5 = boundary.isVectorIn(vectorIn5);
        boolean ans6 = boundary.isVectorIn(vectorOut1);
        boolean ans7 = boundary.isVectorIn(vectorOut2);
        boolean ans8 = boundary.isVectorIn(vectorOut3);
        boolean ans9 = boundary.isVectorIn(vectorOut4);
        boolean ans10 = boundary.isVectorIn(vectorOut5);

        // Then
        assertTrue(ans1);
        assertTrue(ans2);
        assertTrue(ans3);
        assertTrue(ans4);
        assertTrue(ans5);
        assertFalse(ans6);
        assertFalse(ans7);
        assertFalse(ans8);
        assertFalse(ans9);
        assertFalse(ans10);
    }
}
