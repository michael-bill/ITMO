package org.michaelb.lab3.story.objects;

import org.michaelb.lab3.story.exceptions.NoHatOnException;

import java.util.ArrayList;
import java.util.Objects;

public class They {

    ArrayList<Hat> hats;

    public They(int quantity) {
        hats = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Hat hat = new Hat((int)(15 + Math.random() * (15 - 10)), "Материал " + (i + 1));
            hats.add(hat);
        }
    }

    public void wavingHats() throws NoHatOnException {
        if (hats != null && hats.size() > 0)
            System.out.print("Они тоже стали махать шляпами");
        else throw new NoHatOnException("у вас ни у одного шляп нет, лол");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        They they = (They) o;
        return Objects.equals(hats, they.hats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hats);
    }

    @Override
    public String toString() {
        return "They{" +
                "hats=" + hats +
                '}';
    }
}
