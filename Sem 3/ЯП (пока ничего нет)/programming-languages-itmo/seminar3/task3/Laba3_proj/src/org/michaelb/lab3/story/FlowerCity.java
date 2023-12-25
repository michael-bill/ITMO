package org.michaelb.lab3.story;

import java.util.ArrayList;
import java.util.Objects;

public class FlowerCity extends City implements Seeable {

    public Residents residents;

    public FlowerCity(String name, Homes homes) {
        super(name, homes);
        residents = new Residents();
    }

    public FlowerCity(String name, Homes homes, Residents residents) {
        super(name, homes);
        this.residents = residents;
    }

    @Override
    public void seeing(AirBalloon balloon) {
        if (!balloon.blownByTheWind)
            System.out.print("весь Цветочный город был " + SeeingType.VISIBLE_AT_A_GLANCE);
        else
            System.out.print("скоро весь город " + SeeingType.SEEN_FAR_BEHIND);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowerCity that = (FlowerCity) o;
        return  Objects.equals(residents, that.residents) &&
                Objects.equals(homes, that.homes) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return (Objects.hash(residents) + Objects.hash(homes) + Objects.hash(name));
    }

    @Override
    public String toString() {
        return "FlowerCity{" +
                "name='" + name + '\'' +
                ", homes=" + homes +
                ", residents=" + residents +
                '}';
    }
}
