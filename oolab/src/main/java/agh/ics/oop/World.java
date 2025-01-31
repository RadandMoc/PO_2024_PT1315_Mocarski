package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        ConsoleMapDisplay listener = new ConsoleMapDisplay();

        /* // nie wykomentowujemy kodu - usuwamy
        try{
            //List<MoveDirection> directions = OptionsParser.whereMove(args);
            List<MoveDirection> directions = List.of(
                    MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                    MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                    MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD);
            List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
            GrassField map = new GrassField(10);
            map.addObserver(listener);
            Simulation simulation = new Simulation(positions, directions, map);

            directions = List.of(
                    MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                    MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                    MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD);
            positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
            RectangularMap map2 = new RectangularMap(8,8);
            map2.addObserver(listener);
            Simulation simulation2 = new Simulation(positions, directions, map2);
            //simulation.run();
            SimulationEngine eng = new SimulationEngine(List.of(simulation,simulation2));
            eng.runAsync();
            eng.awaitSimulationsEnd();
        }
        catch (IllegalArgumentException e){
            System.out.println(e);
        }
        */

        try {
            //List<MoveDirection> directions = OptionsParser.whereMove(args);
            List<MoveDirection> directions = List.of(
                    MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                    MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                    MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD);
            List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
            GrassField map = new GrassField(10);
            map.addObserver(listener);
            Simulation simulation = new Simulation(positions, directions, map);

            List<Simulation> sims = new ArrayList<>();
            sims.add(simulation);
            for (int i = 0; i < 1000; i++) {
                directions = List.of(
                        MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                        MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD,
                        MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD);
                positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
                RectangularMap map2 = new RectangularMap(8, 8);
                map2.addObserver(listener);
                sims.add(new Simulation(positions, directions, map2));
            }

            //simulation.run();
            SimulationEngine eng = new SimulationEngine(sims);
            //eng.runSync();
            //eng.runAsync();
            eng.runAsyncInThreadPool();
            try {
                eng.awaitSimulationsEnd();
            } catch (InterruptedException ignored) {

            }
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        System.out.println("Program zakończył działanie");
    }

    private static void run(MoveDirection[] directions) {
        String moves = "";
        for (MoveDirection direct : directions) {
            moves += World.whereAnimalMove(direct);
        }
        System.out.print(moves);
    }

    private static String whereAnimalMove(MoveDirection directions) {
        String answer = switch (directions) {
            case MoveDirection.FORWARD -> "zwierzak idzie do przodu\n";
            case MoveDirection.BACKWARD -> "zwierzak idzie do tyłu\n";
            case MoveDirection.RIGHT -> "zwierzak skręca w prawo\n";
            case MoveDirection.LEFT -> "zwierzak skręca w lewo\n";
            default -> "";
        };
        return answer;
    }
}
