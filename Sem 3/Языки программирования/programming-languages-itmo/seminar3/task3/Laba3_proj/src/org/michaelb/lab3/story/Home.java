package org.michaelb.lab3.story;

import java.util.Objects;

public class Home {

    public int number;
    public String address;

    public Home(int number, String address) {
        this.number = number;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Home home = (Home) o;
        return number == home.number && Objects.equals(address, home.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, address);
    }

    @Override
    public String toString() {
        return "Home{" +
                "number=" + number +
                ", address='" + address + '\'' +
                '}';
    }
}
