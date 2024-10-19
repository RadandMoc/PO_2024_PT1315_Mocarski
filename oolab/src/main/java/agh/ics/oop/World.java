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

    public static void run(MoveDirection[] directions)
    {
        String moves = "";
        for(MoveDirection direct : directions)
        {
            moves += World.whereAnimalMove(direct);
        }
        System.out.print(moves);
    }

    /*public static void run(String[] args)
    {
        System.out.println("zwierzak idzie do przodu");
        boolean isNotFirst = false;
        String moves = "";
        char c;
        for(String text : args)
        {
            for (int i = 0; i < text.length(); i++) {
                if(isNotFirst) {
                    System.out.print(",");
                } else {
                    isNotFirst = true;
                }

                c = text.charAt(i);
                System.out.print(c);
                moves += World.whereAnimalMove(c);
            }
        }
        if(args.length > 0)
        {
            System.out.println();
        }
        System.out.print(moves);
    }*/

    private static String whereAnimalMove(MoveDirection en)
    {
        String returner = switch (en) {
            case MoveDirection.Forward -> "zwierzak idzie do przodu\n";
            case MoveDirection.Backward -> "zwierzak idzie do tyłu\n";
            case MoveDirection.Right -> "zwierzak skręca w prawo\n";
            case MoveDirection.Left -> "zwierzak skręca w lewo\n";
            default ->  "";
        };
        return returner;
    }
}
