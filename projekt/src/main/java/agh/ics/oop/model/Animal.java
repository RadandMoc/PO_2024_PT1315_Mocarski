package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement{
    private MapDirection orientation = MapDirection.N; // Nie wiemy, czy ma być zawsze domyślnie na północ
    private Vector2d position;
    private int energy; // Być może statyczna ilość energii byłaby lepsza. Chwilowo dajemy możliwość symulacji wyboru ile energii powinny mieć zwierzęta domyślnie (zakładamy że tam będzie stała)
    private final List<Byte> genome; // Może enum zamiast Byte. Trochę średnio pasuje MapDirection, bo brak obrotu ma opis północ, co nie jest zgodne. Jeśli enum to raczej jakiś nowy (chyba).
    private int turnOfAnimal = 0; // Num of turns of animal when was/is alive
    private final int turnOfBirth;
    private boolean isDead = false;
    private final List<Animal> childs = new ArrayList<>();
    private int eatenGrass = 0;

    public Animal(Vector2d position,int energy,int turnOfBirth,MutateGenome mutateMethod,List<Byte> parentsGenome){
        this.turnOfBirth = turnOfBirth;
        this.energy = energy;
        this.position = position;
        this.genome = mutateMethod.Mutate(parentsGenome);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
}
