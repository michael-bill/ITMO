package org.michaelb.lab3.story.abstractions;

import org.michaelb.lab3.story.objects.Homes;

public abstract class City {

    public String name;
    public Homes homes;

    public City(String name, Homes homes) {
        this.name = name;
        this.homes = homes;
    }
}
