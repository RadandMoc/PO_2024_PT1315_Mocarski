package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World {
    public static void main(String[] args)
    {
        System.out.println("system wystartował");
        //World.run(args);
        World.run(OptionsParser.whereMove(args));
        System.out.println("system zakończył działanie");
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
