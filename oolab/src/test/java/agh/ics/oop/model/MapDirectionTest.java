package agh.ics.oop.model;

import static org.junit.jupiter.api.Assertions.*;
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
        assertTrue(MapDirection.EAST == north && MapDirection.SOUTH == east && MapDirection.WEST == south && MapDirection.NORTH == west);
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
        assertTrue(MapDirection.WEST == north && MapDirection.NORTH == east && MapDirection.EAST == south && MapDirection.SOUTH == west);
    }

    /*@Test // Czy akceptujecie również wzorzec AAA do testów? Identyczne do GWT a chyba ładniejsze
    public void test(){
        // Arrange

        // Act

        // Assert
    }*/
}
