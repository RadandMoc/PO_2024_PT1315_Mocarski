package agh.ics.oop.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileMapDisplay implements MapChangeListener {

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        String fileName = "map_" + worldMap.getId() + ".log";
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
            out.println(worldMap);
        } catch (IOException ignored) { }
    }
}
