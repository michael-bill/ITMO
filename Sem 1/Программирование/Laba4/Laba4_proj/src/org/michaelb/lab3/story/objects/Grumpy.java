package org.michaelb.lab3.story.objects;

import org.michaelb.lab3.story.abstractions.Human;
import org.michaelb.lab3.story.interfaces.Laughable;

import java.util.Objects;

public class Grumpy extends Human implements Laughable {

    public Grumpy() {
       super("Ворчун");
    }

    public Grumpy(String name) {
        super(name);
    }

    public void goTell() {
        System.out.print(name + " сказал");
    }

    @Override
    public void laugh() {
        System.out.print(name + " засмеялся");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grumpy grumpy = (Grumpy) o;
        return Objects.equals(name, grumpy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Grumpy{" +
                "name='" + name + '\'' +
                '}';
    }
}
