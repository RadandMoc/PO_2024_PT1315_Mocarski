package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapDirectionTest {

    @Test
    public void forAllMapDirections_callNext_thenCheckIsProperlyDirection(){
        // Given
        MapDirection north = MapDirection.NORTH;
        MapDirection east = MapDirection.EAST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection west = MapDirection.WEST;
        // When
        north = north.next();
        east = east.next();
        south = south.next();
        west = west.next();
        // Then
        Assertions.assertTrue(north == MapDirection.EAST && east == MapDirection.SOUTH && south == MapDirection.WEST && west == MapDirection.NORTH);
    }

    @Test
    public void forAllMapDirections_callPrevious_thenCheckIsProperlyDirection(){
        // Given
        MapDirection north = MapDirection.NORTH;
        MapDirection east = MapDirection.EAST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection west = MapDirection.WEST;
        // When
        north = north.previous();
        east = east.previous();
        south = south.previous();
        west = west.previous();
        // Then
        Assertions.assertTrue(north == MapDirection.WEST && east == MapDirection.NORTH && south == MapDirection.EAST && west == MapDirection.SOUTH);
    }

    /*@Test // Czy akceptujecie również wzorzec AAA do testów? Identyczne do GWT a chyba ładniejsze
    public void test(){
        // Arrange

        // Act

        // Assert
    }*/
}
