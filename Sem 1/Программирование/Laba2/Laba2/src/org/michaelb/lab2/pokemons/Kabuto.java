package org.michaelb.lab2.pokemons;

import org.michaelb.lab2.pokemons.moves.special.AncientPower;
import org.michaelb.lab2.pokemons.moves.status.RockPolish;
import org.michaelb.lab2.pokemons.moves.status.SandAttack;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Kabuto extends Pokemon {

    public Kabuto() {
        this("Kabutoшка", 1);
    }

    public Kabuto(String name) {
        this(name, 1);
    }

    public Kabuto(String name, int level) {
        super(name, level);
        // Задание базовых атак
        // (hp, att, def, spAtt, spDef, speed)
        setStats(30, 80, 90, 55, 45, 55);
        setType(Type.ROCK, Type.WATER);
        addMove(new SandAttack());
        addMove(new RockPolish());
        addMove(new AncientPower());
    }
}
