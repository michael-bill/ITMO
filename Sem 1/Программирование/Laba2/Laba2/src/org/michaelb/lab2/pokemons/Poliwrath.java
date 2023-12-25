package org.michaelb.lab2.pokemons;

import org.michaelb.lab2.pokemons.moves.status.Confide;
import ru.ifmo.se.pokemon.Type;

public class Poliwrath extends Poliwhirl {

    public Poliwrath() {
        this("Poliwrathушка", 1);
    }

    public Poliwrath(String name) {
        this(name, 1);
    }

    public Poliwrath(String name, int level) {
        super(name, level);
        // Задание базовых атак
        // (hp, att, def, spAtt, spDef, speed)
        setStats(90, 95, 95, 70, 90, 70);
        addType(Type.FIGHTING);
        addMove(new Confide());
    }
}
