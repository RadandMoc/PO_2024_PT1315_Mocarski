package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

public class World {
    public static void main(String[] args)
    {
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        /*System.out.println("system wystartował");
        //World.run(args);
        World.run(OptionsParser.whereMove(args));
        System.out.println("system zakończył działanie");*/
        /*Vector2d a = new Vector2d(1,2);
        Vector2d b = new Vector2d(1,2);
        System.out.println(a.equals(b));*/
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
