package org.michaelb.lab3.story;

import java.util.ArrayList;

public abstract class City {

    public String name;
    public Homes homes;

    public City(String name, Homes homes) {
        this.name = name;
        this.homes = homes;
    }
}
