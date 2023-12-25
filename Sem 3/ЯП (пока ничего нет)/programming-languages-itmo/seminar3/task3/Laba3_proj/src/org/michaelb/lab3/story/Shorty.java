package org.michaelb.lab3.story;

import java.util.Objects;

public class Shorty {

    public final String name;
    public final int height;
    public final String sex;

    public Shorty(String name, int height, String sex) {
        this.name = name;
        this.height = height;
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shorty shorty = (Shorty) o;
        return height == shorty.height && Objects.equals(name, shorty.name) && Objects.equals(sex, shorty.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, sex);
    }

    @Override
    public String toString() {
        return "Shorty{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", sex='" + sex + '\'' +
                '}';
    }
}
