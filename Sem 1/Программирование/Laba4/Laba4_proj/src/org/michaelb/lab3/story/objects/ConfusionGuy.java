package org.michaelb.lab3.story.objects;

import org.michaelb.lab3.story.abstractions.Human;
import org.michaelb.lab3.story.exceptions.NoHatOnException;
import org.michaelb.lab3.story.interfaces.Laughable;

import java.util.Objects;

public class ConfusionGuy extends Human implements Laughable {

    private Head head;
    public final Hand HAND = new Hand();

    public ConfusionGuy() {
        super("Растеряйка");
        this.head = new Head(5, null);
    }

    public ConfusionGuy(String name) {
        super(name);
        this.head = new Head(5, null);
    }

    public ConfusionGuy(String name, int hatSize, String hatMaterial) {
        super(name);
        this.head = new Head(5, new Hat(hatSize, hatMaterial));
    }

    static class Head {
        int brainSize = 10;
        public Hat hat;
        public Head(int brainSize, Hat hat) {
            this.brainSize = brainSize;
            this.hat = hat;
        }
    }

    public class Hand {
        public void holdOutToTakeOffHat() throws NoHatOnException {
            System.out.print(name + " протянул к голове руку, чтобы снять шапку");
            if (head.hat != null)
                System.out.print("снял шапку, смотри не заболей");
            else
                throw new NoHatOnException("только тут обнаружил, что шапки-то на нем нет.");
        }
    }

    @Override
    public void laugh() {
        System.out.print(name + " засмеялся");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfusionGuy that = (ConfusionGuy) o;
        return Objects.equals(head, that.head) && Objects.equals(HAND, that.HAND);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, HAND);
    }
}
