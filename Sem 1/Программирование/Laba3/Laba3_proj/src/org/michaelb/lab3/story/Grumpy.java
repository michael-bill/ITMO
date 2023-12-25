package org.michaelb.lab3.story;

import java.util.Objects;

public class Grumpy implements Laughable {

    final public String name;

    public Grumpy() {
       this.name = "Ворчун";
    }

    public void goTell() {
        System.out.print(name + " сказал");
    }

    @Override
    public void laugh() {
        System.out.print("Ворчун засмеялся");
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
