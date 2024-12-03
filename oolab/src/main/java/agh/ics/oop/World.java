package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public static void main(String[] args)
    {
        ConsoleMapDisplay listener = new ConsoleMapDisplay();
        try{
            List<MoveDirection> directions = OptionsParser.whereMove(args);
            List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
            GrassField map = new GrassField(10);
            map.addObserver(listener);
            Simulation simulation = new Simulation(positions, directions, map);
            simulation.run();
        }
        catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    private static void run(MoveDirection[] directions)
    {
        String moves = "";
        for(MoveDirection direct : directions)
        {
            moves += World.whereAnimalMove(direct);
        }
        System.out.print(moves);
    }

    private static String whereAnimalMove(MoveDirection directions)
    {
        String answer = switch (directions) {
            case MoveDirection.FORWARD -> "zwierzak idzie do przodu\n";
            case MoveDirection.BACKWARD -> "zwierzak idzie do tyłu\n";
            case MoveDirection.RIGHT -> "zwierzak skręca w prawo\n";
            case MoveDirection.LEFT -> "zwierzak skręca w lewo\n";
            default ->  "";
        };
        return answer;
    }
}
