package agh.ics.oop;

public class World {
    public static void main(String[] args)
    {
        System.out.println("system wystartował");
        World.run(args);
        System.out.println("system zakończył działanie");
    }
    public static void run(String[] args)
    {
        System.out.println("zwierzak idzie do przodu");
        boolean isNotFirst = false;
        for(String text : args)
        {
            for (int i = 0; i < text.length(); i++) {
                if(isNotFirst) {
                    System.out.print(",");
                } else {
                    isNotFirst = true;
                }

                System.out.print(text.charAt(i));
            }
        }
        if(args.length > 0)
        {
            System.out.println();
        }
    } // ========================== ============= Zadanie 13 zrobione  ========================================
}
