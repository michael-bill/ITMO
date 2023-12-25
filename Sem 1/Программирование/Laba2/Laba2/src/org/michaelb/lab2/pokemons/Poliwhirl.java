package org.michaelb.lab2.pokemons;

import org.michaelb.lab2.pokemons.moves.physical.WakeUpSlap;

public class Poliwhirl extends Poliwag {

    public Poliwhirl() {
        this("Poliwhirlишка", 1);
    }

    public Poliwhirl(String name) {
        this(name, 1);
    }

    public Poliwhirl(String name, int level) {
        super(name, level);
        // Задание базовых атак
        // (hp, att, def, spAtt, spDef, speed)
        setStats(65, 65, 65, 50, 50, 90);
        addMove(new WakeUpSlap());
    }
}
