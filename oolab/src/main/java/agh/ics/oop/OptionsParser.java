package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static MoveDirection[] whereMove(String[] args)
    {
        int tableLength = 0;
        for (String st: args)
            tableLength += st.length();
        MoveDirection[] moves = new MoveDirection[tableLength];
        char c;
        int iterator = 0;
        for(String text : args)
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
                betterTable[indexOfVal] = i;
                indexOfVal++;
            }
        }
        return betterTable;
    }

    private static MoveDirection whereAnimalMove(char c)
    {
        MoveDirection returner = switch (c) {
            case 'f' -> MoveDirection.Forward;
            case 'b' -> MoveDirection.Backward;
            case 'r' -> MoveDirection.Right;
            case 'l' -> MoveDirection.Left;
            default ->  null;
        };
        return returner;
    }
}
