package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationIntegrationTest {
    private final Vector2d northVec = new Vector2d(2,4);
    private final Vector2d eastVec = new Vector2d(4,2);
    private final Vector2d southVec = new Vector2d(2,0);
    private final Vector2d westVec = new Vector2d(0,2);

    @Test
    public void tryGoOutsideMap(){
        // Given
        String[] moves = new String[]{"f","r","b","l","f","f","b","f","f","l","b","r"};
        List<MoveDirection> directions = OptionsParser.whereMove(moves);
        List<Vector2d> positions = List.of(northVec,eastVec,southVec,westVec);
        Simulation sim = new Simulation(positions,directions);
        Simulation expectedSim = new Simulation(positions,new ArrayList<>());
        // When
        sim.run();
        // Then
        assertEquals(expectedSim,sim);
    }

    @Test
    public void tryGoOutsideMapAndCheckOrientations(){
        // Given
        String[] moves = new String[]{"f","r","b","l","f","f","b","f"};
        List<MoveDirection> directions = OptionsParser.whereMove(moves);
        List<Vector2d> positions = List.of(northVec,eastVec,southVec,westVec);
        Simulation sim = new Simulation(positions,directions);
        Simulation expectedSim = new Simulation(positions,new ArrayList<>());
        List<Animal> expectedAnimals = new ArrayList<>();
        expectedAnimals.add(new Animal(northVec));
        expectedAnimals.add(new Animal(eastVec));
        expectedAnimals.getLast().setOrientation(MapDirection.EAST);
        expectedAnimals.add(new Animal(southVec));
        expectedAnimals.add(new Animal(westVec));
        expectedAnimals.getLast().setOrientation(MapDirection.WEST);
        expectedSim.setObjects(expectedAnimals);
        // When
        sim.run();
        // Then
        assertEquals(expectedSim,sim);
    }

    @Test
    public void checkProperlyMove(){
        // Given
        String[] moves = new String[]{"b","r","f","l","b","b","f","b","b","l","f","r"};
        List<MoveDirection> directions = OptionsParser.whereMove(moves);
        List<Vector2d> positions = List.of(northVec,eastVec,southVec,westVec);
        Simulation sim = new Simulation(positions,directions);
        List<Vector2d> expectedPositions = List.of(new Vector2d(2,2),new Vector2d(3,2),new Vector2d(2,1),new Vector2d(1,2));
        Simulation expectedSim = new Simulation(expectedPositions,new ArrayList<>());
        // When
        sim.run();
        // Then
        assertEquals(expectedSim,sim);
    }

    @Test
    public void checkFullRotationWithMove(){
        // Given
        String[] moves = new String[]{"r","f","r","r","r"};
        List<MoveDirection> directions = OptionsParser.whereMove(moves);
        List<Vector2d> positions = List.of(new Vector2d(2,2));
        Simulation sim = new Simulation(positions,directions);
        List<Vector2d> expectedPositions = List.of(new Vector2d(3,2));
        Simulation expectedSim = new Simulation(expectedPositions,new ArrayList<>());
        // When
        sim.run();
        // Then
        assertEquals(expectedSim,sim);
    }

    @Test
    public void checkAnimalInterpretation(){
        // Given
        RectangularMap map = new RectangularMap(5,5);
        Animal toRight = new Animal();
        Animal forward = new Animal();
        Animal toLeft = new Animal();
        Animal back = new Animal();
        Animal correctRight = new Animal();
        correctRight.setOrientation(MapDirection.EAST);
        Animal correctForw = new Animal(new Vector2d(2,3));
        Animal correctLeft = new Animal();
        correctLeft.setOrientation(MapDirection.WEST);
        Animal correctBack = new Animal(new Vector2d(2,1));
        // When
        toRight.move(MoveDirection.RIGHT,map);
        forward.move(MoveDirection.FORWARD,map);
        toLeft.move(MoveDirection.LEFT,map);
        back.move(MoveDirection.BACKWARD,map);
        // Then
        assertEquals(correctRight,toRight);
        assertEquals(correctForw,forward);
        assertEquals(correctLeft,toLeft);
        assertEquals(correctBack,back);
    }
}
