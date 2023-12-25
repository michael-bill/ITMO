package org.michaelb.lab3.story.objects;

import org.michaelb.lab3.story.exceptions.NoMoreFuelException;
import org.michaelb.lab3.story.interfaces.Flyable;

import java.util.Objects;

public class AirBalloon implements Flyable {

    String name;
    public boolean blownByTheWind;
    private boolean hasFuel;

    public AirBalloon() {
        this.name = "Воздушный шар с именем по умолчанию";
        blownByTheWind = false;
        hasFuel = true;
    }

    public AirBalloon(String name) {
        this.name = name;
        blownByTheWind = false;
        hasFuel = true;
    }

    public void gasUp() {
        hasFuel = true;
    }

    public void emptyTank() {
        hasFuel = false;
    }

    @Override
    public void fly() {
        if (hasFuel)
            System.out.print("Воздушный шар поднялся еще выше");

        else throw new NoMoreFuelException();
        int[] a = new int[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirBalloon that = (AirBalloon) o;
        return blownByTheWind == that.blownByTheWind && Objects.equals(name, that.name) && hasFuel == that.hasFuel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, blownByTheWind, hasFuel);
    }

    @Override
    public String toString() {
        return "AirBalloon{" +
                "name='" + name + '\'' +
                ", blownByTheWind=" + blownByTheWind +
                ", hasFuel=" + hasFuel +
                '}';
    }
}
