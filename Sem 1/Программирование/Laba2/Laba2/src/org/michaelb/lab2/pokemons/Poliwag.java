package org.michaelb.lab2.pokemons;

import org.michaelb.lab2.pokemons.moves.status.Rest;
import org.michaelb.lab2.pokemons.moves.status.Swagger;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Poliwag extends Pokemon {

    public Poliwag() {
        this("Poliwagушка", 1);
    }

    public Poliwag(String name) {
        this(name, 1);
    }

    public Poliwag(String name, int level) {
        super(name, level);
        // Задание базовых атак
        // (hp, att, def, spAtt, spDef, speed)
        setStats(40, 50, 40, 40, 40, 90);
        setType(Type.WATER);
        addMove(new Rest());
        addMove(new Swagger());
    }

}
