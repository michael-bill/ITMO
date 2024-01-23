package org.michaelb.lab3.story;

public class Wind implements Flyable {

    public void blownAway(AirBalloon balloon) {
        balloon.blownByTheWind = true;
        System.out.print("Воздушный шар относило ветром");
    }

    @Override
    public void fly() {
        System.out.print(this + " летит");
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Ветер";
    }
}
