package org.michaelb.lab3.story;

import java.util.Objects;

public class AirBalloon implements Flyable {

    String name;
    public boolean blownByTheWind;

    public AirBalloon() {
        this.name = "Воздушный шар с именем по умолчанию";
        blownByTheWind = false;
    }

    public AirBalloon(String name) {
        this.name = name;
    }

    @Override
    public void fly() {
        System.out.print("Воздушный шар поднялся еще выше");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirBalloon that = (AirBalloon) o;
        return blownByTheWind == that.blownByTheWind && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, blownByTheWind);
    }

    @Override
    public String toString() {
        return "AirBalloon{" +
                "name='" + name + '\'' +
                ", blownByTheWind=" + blownByTheWind +
                '}';
    }
}
