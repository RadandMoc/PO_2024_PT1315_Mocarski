package agh.ics.oop.model;

public interface EnergyLoss {
    int howManyEnergyToWalk(Animal animal); // zwierzę zamiast vectora zwiększa elastyczność klasom implementującym. Z tego obiektu można odczytać vector, ale inne klasy mogłyby np. odczytać wagę zwierzęcia gdyby chciało itp. Z punktu wydajnościowego różnica pomiędzy vectorem a animalem nie istnieje, bo w obu przypadkach przechowywana jest tylko referencja
}
