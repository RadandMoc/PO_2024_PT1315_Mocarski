package agh.ics.oop.presenter;

import agh.ics.oop.model.MutationStrategy;

import java.io.*;

public record PresenterConfigurationSave(int heightValue,
                                         int widthValue,
                                         int xValue,
                                         int yValue,
                                         int energyFromPlantValue,
                                         int genomeLengthValue,
                                         int energyForBeingFullStaffedValue,
                                         int startingEnergyValue,
                                         int numOfNewPlantsPerTurnValue,
                                         int breadingEnergyLossValue,
                                         int energyLossValue,
                                         int energyLossPerMoveToPoleValue,
                                         int startsNumOfPlantsValue,
                                         int startingAnimalsValue,
                                         String mapChoice,
                                         MutationStrategy mutationStrategy,
                                         int minMutation,
                                         int maxMutation,
                                         boolean saveStatistics
) implements Serializable {

    public void save(String path) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(path);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        } catch (IOException e) {
            throw new IOException("Failed to save PresenterConfigurationSave to " + path + ": " + e.getMessage(), e);
        }
    }

    public static PresenterConfigurationSave load(String path) throws IOException, ClassNotFoundException { // założymy się, że ClassNotFoundException nie rzuca?
        try (FileInputStream fileIn = new FileInputStream(path);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (PresenterConfigurationSave) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Failed to load PresenterConfigurationSave from " + path + ": " + e.getMessage(), e);
        }
    }
}
