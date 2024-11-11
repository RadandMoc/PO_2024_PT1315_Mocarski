package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {
    public static void main(String[] args)
    {
        List<MoveDirection> directions = OptionsParser.whereMove(args);
        List<Animal> animals = List.of(new Animal(new Vector2d(2,2)), new Animal(new Vector2d(3,4)));
        RectangularMap rect = new RectangularMap(5,5);
        Simulation<Animal,Vector2d> anSimulation = new Simulation<Animal,Vector2d>(animals, new ArrayList<>(directions),rect);
        anSimulation.run();

        List<String> texts = List.of("Ala","ma","psa","a","Tomek","ma","kota");
        TextMap txtMap = new TextMap();
        Simulation<String,Integer> tSim = new Simulation<String,Integer>(texts,directions,txtMap);
        tSim.run();
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
