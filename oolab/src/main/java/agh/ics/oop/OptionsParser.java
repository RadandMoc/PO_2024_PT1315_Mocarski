package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static MoveDirection[] whereMove(String[] directions)
    {
        int tableLength = 0;
        for (String text: directions)
            tableLength += text.length();
        MoveDirection[] moves = new MoveDirection[tableLength];
        char c;
        int iterator = 0;
        for(String text : directions)
        {
            for (int i = 0; i < text.length(); i++) {
                c = text.charAt(i);
                moves[iterator]=OptionsParser.whereAnimalMove(c);
                iterator++;
            }
        }
        return OptionsParser.optimizeTable(moves);
    }

    private static MoveDirection[] optimizeTable(MoveDirection[] table)
    {
        int len = table.length;
        for (MoveDirection i : table)
        {
            if(i == null)
                len--;
        }
        MoveDirection[] betterTable = new MoveDirection[len];
        int indexOfVal = 0;
        for (MoveDirection i : table)
        {
            if(i != null)
            {
                betterTable[indexOfVal++] = i;
            }
        }
        return betterTable;
    }

    private static MoveDirection whereAnimalMove(char c)
    {
        MoveDirection returner = switch (c) {
            case 'f' -> MoveDirection.FORWARD;
            case 'b' -> MoveDirection.BACKWARD;
            case 'r' -> MoveDirection.RIGHT;
            case 'l' -> MoveDirection.LEFT;
            default ->  null;
        };
        return returner;
    }
}
