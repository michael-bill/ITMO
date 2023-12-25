package org.michaelb.lab2.pokemons;

import org.michaelb.lab2.pokemons.moves.physical.Bite;
import org.michaelb.lab2.pokemons.moves.physical.Tackle;
import org.michaelb.lab2.pokemons.moves.special.DarkPulse;
import org.michaelb.lab2.pokemons.moves.status.BabyDollEyes;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Furfrou extends Pokemon {

    public Furfrou() {
        this("Furfrouшка", 1);
    }

    public Furfrou(String name) {
        this(name, 1);
    }

    public Furfrou(String name, int level) {
        super(name, level);
        // Задание базовых атак
        // (hp, att, def, spAtt, spDef, speed)
        setStats(75, 80, 60, 65, 90, 102);
        addType(Type.NORMAL);
        addMove(new DarkPulse());
        addMove(new BabyDollEyes());
        addMove(new Tackle());
        addMove(new Bite());
    }
}
