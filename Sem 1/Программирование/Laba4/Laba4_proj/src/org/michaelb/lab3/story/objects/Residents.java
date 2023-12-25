package org.michaelb.lab3.story.objects;

import org.michaelb.lab3.story.enums.SeeingType;
import org.michaelb.lab3.story.interfaces.Seeable;

import java.util.ArrayList;
import java.util.Objects;

public class Residents extends ArrayList<Shorty> implements Seeable {

    @Override
    public void seeing(AirBalloon balloon) {
        System.out.print("коротышек уж и " + SeeingType.COULD_NOT_SEE_AT_ALL);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Residents other) {
            if (other.size() == this.size()) {
                for (int i = 0; i < this.size(); i++) {
                    if (!this.get(i).equals(other.get(i)))
                        return  false;
                }
            } else return false;
        } else return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.size(); i++)
            result += this.get(i).toString() + "\n";
        return result;
    }
}
