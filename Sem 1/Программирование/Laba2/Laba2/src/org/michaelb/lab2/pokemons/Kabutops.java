package org.michaelb.lab2.pokemons;

import org.michaelb.lab2.pokemons.moves.physical.NightSlash;

public class Kabutops extends Kabuto {

    public Kabutops() {
        this("Kabutopsушка", 1);
    }

    public Kabutops(String name) {
        this(name, 1);
    }

    public Kabutops(String name, int level) {
        super(name, level);
        // Задание базовых атак
        // (hp, att, def, spAtt, spDef, speed)
        setStats(60, 115, 105, 65, 70, 80);
        addMove(new NightSlash());
    }
}
